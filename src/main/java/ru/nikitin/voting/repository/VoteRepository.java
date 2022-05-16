package ru.nikitin.voting.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.nikitin.voting.model.Vote;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface VoteRepository extends BaseRepository<Vote> {

    @Transactional
    @Query("SELECT v FROM Vote v JOIN FETCH v.restaurant WHERE v.dateVote=:date AND v.user.id=:userId")
    Optional<Vote> getUserVoteThisDay(LocalDate date, int userId);

    @Query("SELECT v FROM Vote v JOIN FETCH v.restaurant WHERE v.user.id=:userId")
    List<Vote> getAllByUserId(int userId);


    @Transactional
    @Query("SELECT v FROM  Vote v JOIN FETCH v.restaurant WHERE v.dateVote=:date")
    List<Vote> findAllOnDateVoting(LocalDate date);
}
