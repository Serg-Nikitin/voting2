package ru.javaops.topjava2.model;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.javaops.topjava2.to.DishTo;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

import static ru.javaops.topjava2.util.DishUtil.convert;

@Entity
@Getter
@Setter
@Table(indexes = {@Index(name = "serving_idx", columnList = "serving, restaurant_id")})
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true)
public class Dish extends NamedEntity implements Serializable {

    @NotNull
    @Column(name = "serving", nullable = false)
    @FutureOrPresent
    private LocalDate dateOfServing;

    @Column(name = "price")
    @NotNull
    private Integer price;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    public Dish(Integer id, String name, LocalDate dateOfServing, Integer price) {
        this(id, name, dateOfServing, price, null);
    }

    public Dish(DishTo dishTo) {
        this(dishTo.getId(), dishTo.getName(), dishTo.getDateOfServing(), convert(dishTo.getPrice()));
    }

    public Dish(Integer id, String name, LocalDate dateOfServing, Integer price, Restaurant restaurant) {
        super(id, name);
        this.dateOfServing = dateOfServing;
        this.price = price;
        this.restaurant = restaurant;
    }

}
