package ru.nikitin.voting.to.vote;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import ru.nikitin.voting.model.Restaurant;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class VotingTo {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate voteDate;
    Restaurant restaurant;
    Long rating;

    public VotingTo(LocalDate voteDate, Restaurant restaurant, Long rating) {
        this.voteDate = voteDate;
        this.restaurant = restaurant;
        this.rating = rating;
    }


}
