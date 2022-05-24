package ru.nikitin.voting.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nikitin.voting.error.IllegalRequestDataException;
import ru.nikitin.voting.model.Restaurant;
import ru.nikitin.voting.model.Vote;
import ru.nikitin.voting.repository.UserRepository;
import ru.nikitin.voting.repository.VoteRepository;
import ru.nikitin.voting.to.vote.VoteTo;
import ru.nikitin.voting.to.vote.VotingTo;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static ru.nikitin.voting.util.ServiceUtil.*;

@Service
@Slf4j
@AllArgsConstructor
@Transactional(readOnly = true)
public class VoteService {

    private final RestaurantService restaurantService;
    private final VoteRepository repository;
    private final UserRepository userRepository;

    @Transactional
    public VoteTo voting(int userId, int restaurantId) {
        log.info("voting userId = {}, restaurantId = {}", userId, restaurantId);
        Optional<Vote> voteById = repository.findFirstByUserIdOrderByIdDesc(userId);
        if (voteById.isPresent() && checkDate(voteById)) {
            if (checkTime()) {
                return save(userId, restaurantId, voteById.get());
            } else {
                throw new IllegalRequestDataException("You can't change your vote after 11:00");
            }
        } else {
            return save(userId, restaurantId, new Vote(LocalDate.now()));
        }
    }

    public VoteTo save(int userId, int restaurantId, Vote vote) {
        Restaurant restaurant = restaurantService.findById(restaurantId);
        vote.set(restaurant, userRepository.getById(userId));
        Vote newV = repository.save(vote);
        return new VoteTo(newV.getId(), newV.getVoteDate(), restaurant);
    }

    public Vote findById(int voteId) {
        log.info("findById votId = {}", voteId);
        return checkNotFound(repository.findById(voteId), voteId);
    }

    public List<VoteTo> getAll(int userId) {
        log.info("getAll userId = {}", userId);
        return repository.getAllByUserId(userId).getTo();
    }

    public VoteTo getByDate(int userId, LocalDate date) {
        log.info("getVoteThisDay date = {}, userId = {}", date, userId);
        return new VoteTo(repository.getByUserIdAndDate(userId, date)
                .orElseThrow(() -> new EntityNotFoundException(String.format("You did not vote on this date = %s", date.toString()))));
    }

    public List<VotingTo> getVotingByDate(LocalDate date) {
        log.info("getVoting date = {}", date);
        return repository.getVotingByDate(date).getRating(date);
    }
}
