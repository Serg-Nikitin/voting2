package ru.javaops.topjava2.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.javaops.topjava2.error.IllegalRequestDataException;
import ru.javaops.topjava2.model.Dish;
import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.model.User;
import ru.javaops.topjava2.model.Vote;
import ru.javaops.topjava2.repository.UserRepository;
import ru.javaops.topjava2.repository.VoteRepository;
import ru.javaops.topjava2.to.DishTo;
import ru.javaops.topjava2.to.VotingTo;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class VoteService {

    private final VoteRepository repository;
    private final UserRepository userRepository;
    private final DishService dishService;

    public VoteService(VoteRepository repository, UserRepository userRepository, DishService dishService) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.dishService = dishService;
    }

    public Vote voting(int userId, Vote vote) {
        log.info("voting userId = {}, restaurantId = {}", userId, vote.getRestaurant().id());
        vote.setUser(userRepository.getById(userId));
        return repository.save(vote);
    }

    public Vote changeVote(int userId, Vote vote) {
        log.info("changeVote votId = {}", vote.id());
        LocalTime now = LocalTime.now();
        LocalTime to = LocalTime.of(11, 0);
        vote.setUser(userRepository.getById(userId));
        if (now.compareTo(to) <= 0) {
            return repository.save(vote);
        } else {
            throw new IllegalRequestDataException("You can't change your vote after 11:00");
        }
    }

    public Vote findById(Integer voteId) {
        log.info("findById votId = {}", voteId);
        return repository.findById(voteId).orElseThrow(() -> new EntityNotFoundException(String.format("vote with id = %d not found", voteId)));
    }

    public List<Vote> getAll(User user) {
        log.info("getAll userId = {}", user.id());
        List<Vote> list = repository.getAllByUserId(user.id());
        list.forEach(v -> v.setUser(user));
        return list;
    }

    public Vote getVoteThisDay(LocalDate date, User user) {
        log.info("getVoteThisDay date = {}, userId = {}", date, user.id());

        Vote vote = repository.getUserVoteThisDay(date, user.id())
                .orElseThrow(() -> new EntityNotFoundException(String.format("You didn't vote that day = %s", date.toString())));
        vote.setUser(user);
        return vote;
    }

    public VotingTo getVoting(LocalDate date) {
        log.info("getVoting date = {}", date);

        List<Vote> votes = repository.findAllOnDateVoting(date);

        Map<Restaurant, List<DishTo>> menu = dishService.findAll().stream()
                .filter(dish -> dish.getDateOfServing().compareTo(date) == 0)
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
