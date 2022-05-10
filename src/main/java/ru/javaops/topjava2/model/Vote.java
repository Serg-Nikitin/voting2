package ru.javaops.topjava2.model;


import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;
import ru.javaops.topjava2.util.validation.present.Present;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity()
@Getter
@Setter
@Table(indexes = {@Index(name = "vote_idx", columnList = "date_vote")},
        uniqueConstraints = @UniqueConstraint(columnNames = {"date_vote", "restaurant_id", "user_id"}, name = "uk_vote"))
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class Vote extends BaseEntity {

    @Column(name = "date_vote")
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Present
    private LocalDate dateVote;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;


    public Vote(LocalDate dateVote, Restaurant restaurant, User user) {
        this(null, dateVote, restaurant, user);
    }

    public Vote(Integer id, LocalDate dateVote, Restaurant restaurant) {
        this(id, dateVote, restaurant, null);
    }

    public Vote(LocalDate dateVote, Restaurant restaurant) {
        this(null, dateVote, restaurant, null);
    }

    public Vote(Integer id, LocalDate dateVote, Restaurant restaurant, User user) {
        super(id);
        this.dateVote = dateVote;
        this.restaurant = restaurant;
        this.user = user;
    }
}
