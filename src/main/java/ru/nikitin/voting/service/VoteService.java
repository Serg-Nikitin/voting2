package ru.nikitin.voting.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.nikitin.voting.error.IllegalRequestDataException;
import ru.nikitin.voting.model.Dish;
import ru.nikitin.voting.model.Restaurant;
import ru.nikitin.voting.model.User;
import ru.nikitin.voting.model.Vote;
import ru.nikitin.voting.repository.UserRepository;
import ru.nikitin.voting.repository.VoteRepository;
import ru.nikitin.voting.to.DishTo;
import ru.nikitin.voting.to.VotingTo;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class VoteService {

    private final RestaurantService restaurantService;
    private final VoteRepository repository;
    private final UserRepository userRepository;
    private final DishService dishService;

    public VoteService(RestaurantService restaurantService, VoteRepository repository, UserRepository userRepository, DishService dishService) {
        this.restaurantService = restaurantService;
        this.repository = repository;
        this.userRepository = userRepository;
        this.dishService = dishService;
    }

    public Vote voting(int userId, int restaurantId) {
        log.info("voting userId = {}, restaurantId = {}", userId, restaurantId);
        Restaurant restaurant = restaurantService.getById(restaurantId);
        User user = userRepository.getById(userId);
        return repository.save(new Vote(LocalDate.now(), restaurant, user));
    }

    public Vote changeVote(int userId, int restaurantId, int id) {
        log.info("changeVote votId = {}", id);
        LocalTime now = LocalTime.now();
        LocalTime to = LocalTime.of(11, 0);
        Vote current = repository.getById(id);
        current.setRestaurant(restaurantService.getById(restaurantId));
        current.setUser(userRepository.getById(userId));
        if (now.compareTo(to) <= 0) {
            return repository.save(current);
        } else {
            throw new IllegalRequestDataException("You can't change your vote after 11:00");
        }
    }

    public Vote findById(int voteId) {
        log.info("findById votId = {}", voteId);
        return repository.findById(voteId).orElseThrow(() -> new EntityNotFoundException(String.format("vote with id = %d not found", voteId)));
    }

    public List<Vote> getAll(int userId) {
        log.info("getAll userId = {}", userId);
        List<Vote> list = repository.getAllByUserId(userId);
        list.forEach(v -> v.setUser(userRepository.getById(userId)));
        return list;
    }

    public Vote getVoteThisDay(LocalDate date, int userId) {
        log.info("getVoteThisDay date = {}, userId = {}", date, userId);

        Vote vote = repository.getByUserIdAndDate(date, userId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("You didn't vote that day = %s", date.toString())));
        vote.setUser(userRepository.getById(userId));
        return vote;
    }

    public VotingTo getVoting(LocalDate date) {
        log.info("getVoting date = {}", date);

        List<Vote> votes = repository.findAllOnDateVoting(date);

        Map<Restaurant, List<DishTo>> menu = dishService.findAll().stream()
                .filter(dish -> dish.getServingDate().compareTo(date) == 0)
                .collect(Collectors.groupingBy(Dish::getRestaurant, Collectors.mapping(DishTo::new, Collectors.toList())));

        Map<Restaurant, Long> rating = votes
                .stream()
                .collect(Collectors.groupingBy(Vote::getRestaurant, Collectors.counting()));
        if (menu.keySet().size() != rating.keySet().size()) {
            menu.keySet()
                    .forEach(key -> rating.computeIfAbsent(key, k -> 0L));
        }

        if (rating.keySet().size() == 0) {
            throw new EntityNotFoundException(String.format("Voting result for date = %s not found", date.toString()));
        }
        return new VotingTo(date, rating, menu);
    }
}
