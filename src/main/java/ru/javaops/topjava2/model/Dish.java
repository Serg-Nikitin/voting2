package ru.javaops.topjava2.model;

import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Table(indexes = {@Index(name = "serving_idx", columnList = "serving, restaurant_id")})
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(callSuper = true)
public class Dish extends NamedEntity implements Serializable {

    @NotNull
    @FutureOrPresent
    @Column(name = "serving", nullable = false)
    private LocalDate dateOfServing;

    @Column(name = "price")
    @NotNull
    private Double price;

    @ManyToOne(fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    public Dish(String name, LocalDate dateOfServing, Double price) {
        this(null, name, dateOfServing, price);
    }

    public Dish(Integer id, String name, LocalDate dateOfServing, Double price) {
        this(id, name, dateOfServing, price, null);
    }

    public Dish(Integer id, String name, LocalDate dateOfServing, Double price, Restaurant restaurant) {
        super(id, name);
        this.dateOfServing = dateOfServing;
        this.price = price;
        this.restaurant = restaurant;
    }

}
