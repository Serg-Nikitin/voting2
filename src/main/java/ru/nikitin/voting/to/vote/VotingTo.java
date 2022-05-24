package ru.nikitin.voting.to.vote;

import ru.nikitin.voting.model.Restaurant;

import java.time.LocalDate;


public record VotingTo(LocalDate voteDate,
                       Restaurant restaurant,
                       Long rating) {
}
