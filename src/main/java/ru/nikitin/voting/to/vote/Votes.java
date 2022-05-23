package ru.nikitin.voting.to.vote;

import lombok.RequiredArgsConstructor;
import org.springframework.data.util.Streamable;
import ru.nikitin.voting.model.Vote;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor(staticName = "of")
public class Votes implements Streamable<Vote> {
    private final Streamable<Vote> streamable;

    public List<VotingTo> getRating(LocalDate date) {
        return streamable.stream()
                .collect(Collectors.groupingBy(Vote::getRestaurant, Collectors.counting()))
                .entrySet().stream().map(m -> new VotingTo(date, m.getKey(), m.getValue()))
                .collect(Collectors.toList());

    }

    @Override
    public Iterator<Vote> iterator() {
        return streamable.iterator();
    }
}
