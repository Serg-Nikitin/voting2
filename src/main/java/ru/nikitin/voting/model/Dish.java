package ru.nikitin.voting.model;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.nikitin.voting.to.DishTo;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(indexes = {@Index(name = "serving_idx", columnList = "serving, restaurant_id")})
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Dish extends NamedEntity {

    @NotNull
    @Column(name = "serving", nullable = false)
    private LocalDate servingDate;

    @Column(name = "price")
    @NotNull
    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    public Dish(Integer id, String name, LocalDate servingDate, Integer price) {
        this(id, name, servingDate, price, null);
    }

    public Dish(DishTo dishTo) {
        this(dishTo.getId(), dishTo.getName(), dishTo.getServingDate(), dishTo.getPrice());
    }

    public Dish(Integer id, String name, LocalDate servingDate, Integer price, Restaurant restaurant) {
        super(id, name);
        this.servingDate = servingDate;
        this.price = price;
        this.restaurant = restaurant;
    }
}
