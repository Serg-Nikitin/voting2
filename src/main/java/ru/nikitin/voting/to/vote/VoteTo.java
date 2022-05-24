package ru.nikitin.voting.to.vote;

import ru.nikitin.voting.model.Restaurant;
import ru.nikitin.voting.model.Vote;
import ru.nikitin.voting.util.validation.present.Present;

import java.time.LocalDate;

public record VoteTo(int id, @Present LocalDate date, Restaurant restaurant) {

    public VoteTo(Vote vote) {
        this(vote.id(), vote.getVoteDate(), vote.getRestaurant());
    }

    public int getId() {
        return id;
    }
}