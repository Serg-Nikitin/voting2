package ru.javaops.topjava2.to;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import org.hibernate.validator.constraints.Range;
import ru.javaops.topjava2.HasId;
import ru.javaops.topjava2.model.Dish;
import ru.javaops.topjava2.util.DishUtil;

import javax.validation.constraints.NotNull;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DishTo extends NamedTo implements HasId {

    @NotNull
    @Range(min = 10)
    Double price;

    public DishTo(Integer id, String name, Double price) {
        super(id, name);
        this.price = price;
    }

    public DishTo(Dish dish) {
        this(dish.getId(), dish.getName(), DishUtil.convert(dish.getPrice()));
    }
}
