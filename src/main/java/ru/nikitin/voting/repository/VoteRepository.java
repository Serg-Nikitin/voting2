package ru.nikitin.voting.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.nikitin.voting.model.Vote;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface VoteRepository extends BaseRepository<Vote> {

    @Transactional
    @Query("SELECT v FROM Vote v WHERE v.voteDate=:date AND v.user.id=:userId")
    Optional<Vote> getByUserIdAndDate(LocalDate date, int userId);

    @Transactional(readOnly = true)
    @Query("SELECT v FROM Vote v WHERE v.user.id=:userId ORDER BY v.voteDate desc ")
    List<Vote> getAllByUserId(int userId);


    @Transactional
    @Query("SELECT v FROM  Vote v WHERE v.voteDate=:date")
    List<Vote> findAllOnDateVoting(LocalDate date);
}
