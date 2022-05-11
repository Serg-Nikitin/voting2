package ru.javaops.topjava2.to;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;
import ru.javaops.topjava2.model.Restaurant;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
@EqualsAndHashCode
public class VotingTo {

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;

    private Map<Restaurant, Long> rating;
    private Map<Restaurant, List<DishTo>> menu;

    public VotingTo(LocalDate date, Map<Restaurant, Long> rating, Map<Restaurant, List<DishTo>> menu) {
        this.date = date;
        this.rating = rating;
        this.menu = menu;
    }

    @Override
    public String toString() {
        return "VotingTo{" +
                "date=" + date +
                ", rating=" + rating +
                ", menu=" + menu +
                '}';
    }
}
