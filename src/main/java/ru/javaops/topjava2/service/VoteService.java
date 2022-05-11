package ru.javaops.topjava2.service;

import org.springframework.stereotype.Service;
import ru.javaops.topjava2.error.IllegalRequestDataException;
import ru.javaops.topjava2.model.Dish;
import ru.javaops.topjava2.model.Restaurant;
import ru.javaops.topjava2.model.Vote;
import ru.javaops.topjava2.repository.UserRepository;
import ru.javaops.topjava2.repository.VoteRepository;
import ru.javaops.topjava2.to.DishTo;
import ru.javaops.topjava2.to.voting.VotingTo;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
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
        vote.setUser(userRepository.getById(userId));
        return repository.save(vote);
    }

    public Vote changeVote(int userId, Vote vote, int voteId) {
        LocalTime now = LocalTime.now();
        LocalTime to = LocalTime.of(11, 0);
        vote.setUser(userRepository.getById(userId));
        if (now.compareTo(to) <= 0) {
            return repository.save(vote);
        } else {
            throw new IllegalRequestDataException("You can't change your vote after 11:00");
        }
    }

    public Vote getById(Integer voteId) {
        return repository.getById(voteId);
    }

    public List<Vote> getAll(int userId) {
        return repository.getAllByUserId(userId);
    }

    public Optional<Vote> getVoteThisDay(LocalDate date, int userId) {
        return repository.getUserVoteThisDay(date, userId);
    }

    public VotingTo getVoting(LocalDate date) {
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
        return new VotingTo(date, rating, menu);
    }
}
