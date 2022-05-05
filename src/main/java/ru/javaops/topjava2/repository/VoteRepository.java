package ru.javaops.topjava2.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javaops.topjava2.model.Vote;

import java.time.LocalDate;
import java.util.List;

public interface VoteRepository extends BaseRepository<Vote> {

    @Transactional
    @Modifying
    @Query("SELECT v FROM Vote v JOIN FETCH v.restaurant WHERE v.user.id=:userId ORDER BY v.dateVote DESC")
    List<Vote> getAll(Integer userId);


    @Transactional
    @Modifying
    @Query("UPDATE Vote v SET v.vote=:vote WHERE v.id=:id")
    Vote update(Integer id, Boolean vote);

    @Transactional
    @Modifying
    @Query("SELECT v.restaurant.id, v.user.id, v.id FROM Vote v WHERE v.user.id=:userId AND v.dateVote=:date AND v.vote=true")
    List<Integer> getLastVoteToday(int userId, LocalDate date);
}
