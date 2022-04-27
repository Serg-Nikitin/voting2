package ru.javaops.topjava2.model;

import lombok.*;
import org.hibernate.validator.constraints.Range;

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
public class Dish extends NamedEntity implements Serializable {

    @NotNull
    @FutureOrPresent
    @Column(name = "serving", nullable = false)
    private LocalDate dateOfServing;

    @Column(name = "price")
    @NotNull
    @Range(min = 10)
    private Integer price;

    @ManyToOne(fetch = FetchType.LAZY)
    private Restaurant restaurant;

    public Dish(Integer id, String name, LocalDate dateOfServing, Integer price, Restaurant restaurant) {
        super(id, name);
        this.dateOfServing = dateOfServing;
        this.price = price;
        this.restaurant = restaurant;
    }
}
