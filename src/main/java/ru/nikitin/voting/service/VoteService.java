package ru.nikitin.voting.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.nikitin.voting.error.IllegalRequestDataException;
import ru.nikitin.voting.model.Restaurant;
import ru.nikitin.voting.model.User;
import ru.nikitin.voting.model.Vote;
import ru.nikitin.voting.repository.UserRepository;
import ru.nikitin.voting.repository.VoteRepository;
import ru.nikitin.voting.to.vote.VotingTo;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.nikitin.voting.util.ServiceUtil.checkNotFound;

@Service
@Slf4j
@AllArgsConstructor
public class VoteService {

    private final RestaurantService restaurantService;
    private final VoteRepository repository;
    private final UserRepository userRepository;


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
        if (!now.isAfter(to)) {
            return repository.save(current);
        } else {
            throw new IllegalRequestDataException("You can't change your vote after 11:00");
        }
    }

    public Vote findById(int voteId) {
        log.info("findById votId = {}", voteId);
        return checkNotFound(repository.findById(voteId), voteId);
    }

    public List<Vote> getAll(int userId) {
        log.info("getAll userId = {}", userId);
        List<Vote> list = repository.getAllByUserId(userId);
        list.forEach(v -> v.setUser(userRepository.getById(userId)));
        return list;
    }

    public Vote getVoteByDate(LocalDate date, int userId) {
        log.info("getVoteThisDay date = {}, userId = {}", date, userId);

        Vote vote = repository.getByUserIdAndDate(date, userId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("You didn't vote that day = %s", date.toString())));
        vote.setUser(userRepository.getById(userId));
        return vote;
    }

    public List<VotingTo> getVotingByDate(LocalDate date) {
        log.info("getVoting date = {}", date);
        return repository.getVotingByDate(date).getRating(date);
    }
}
