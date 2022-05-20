package ru.nikitin.voting.to;


import lombok.Data;
import lombok.ToString;
import ru.nikitin.voting.model.Restaurant;

import java.time.LocalDate;
import java.util.List;

@Data
@ToString
public class Menu {
    LocalDate date;
    Restaurant restaurant;
    List<DishTo> menu;

    public Menu(LocalDate date, Restaurant restaurant, List<DishTo> menu) {
        this.date = date;
        this.restaurant = restaurant;
        this.menu = menu;
    }
}
