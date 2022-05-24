package ru.nikitin.voting.to;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import ru.nikitin.voting.HasId;
import ru.nikitin.voting.model.Dish;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DishTo extends NamedTo implements HasId {

    @NotNull
    int price;

    @NotNull
    @FutureOrPresent
    LocalDate servingDate;

    int restaurantId;

    public DishTo(Integer id, String name, Integer price, LocalDate servingDate, int restaurantId) {
        super(id, name);
        this.price = price;
        this.servingDate = servingDate;
        this.restaurantId = restaurantId;
    }

    public static DishTo getDishTo(Dish dish) {
        return new DishTo(dish.getId(), dish.getName(), dish.getPrice(), dish.getServingDate(), dish.getRestaurant().id());
    }
}
