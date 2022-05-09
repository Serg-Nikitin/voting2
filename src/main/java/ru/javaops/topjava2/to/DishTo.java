package ru.javaops.topjava2.to;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.DateTimeFormat;
import ru.javaops.topjava2.HasId;
import ru.javaops.topjava2.model.Dish;
import ru.javaops.topjava2.util.DishUtil;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DishTo extends NamedTo implements HasId {

    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @FutureOrPresent
    LocalDate dateOfServing;

    @NotNull
    @Range(min = 10)
    Double price;

    public DishTo(Integer id, String name, LocalDate dateOfServing, Double price) {
        super(id, name);
        this.dateOfServing = dateOfServing;
        this.price = price;
    }

    public DishTo(Dish dish) {
        this(dish.getId(), dish.getName(), dish.getDateOfServing(), DishUtil.convert(dish.getPrice()));
    }
}
