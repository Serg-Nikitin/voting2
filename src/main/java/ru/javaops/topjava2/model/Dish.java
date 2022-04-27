package ru.javaops.topjava2.model;

import lombok.*;
import org.hibernate.validator.constraints.Range;
import ru.javaops.topjava2.util.validation.NoHtml;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(indexes = {@Index(name = "serving_idx", columnList = "serving, restaurant_id")})
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Dish extends NamedEntity {

    @NotNull
    @NoHtml
    @FutureOrPresent
    @Column(name = "serving", nullable = false)
    private Date dateOfServing;

    @Column(name = "price")
    @NoHtml
    @NotNull
    @Range(min = 10)
    private Integer price;

    @ManyToOne(fetch= FetchType.LAZY)
    private Restaurant restaurant;


}
