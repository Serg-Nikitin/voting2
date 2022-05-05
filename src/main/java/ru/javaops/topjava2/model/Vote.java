package ru.javaops.topjava2.model;


import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity()
@Getter
@Setter
@Table(indexes = {@Index(name = "vote_idx", columnList = "date_vote,restaurant_id")})
@ToString(callSuper = true)
@EqualsAndHashCode
@NoArgsConstructor
public class Vote extends BaseEntity {

    @Column(name = "date_vote", columnDefinition = "timestamp default now()")
    @NotNull
    private LocalDate dateVote;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @NotNull
    private Boolean vote;

    public Vote(LocalDate dateVote, Restaurant restaurant, User user, Boolean vote) {
        this(null, dateVote, restaurant, user, vote);
    }

    public Vote(Integer id, LocalDate dateVote, Restaurant restaurant, User user, Boolean vote) {
        super(id);
        this.dateVote = dateVote;
        this.restaurant = restaurant;
        this.user = user;
        this.vote = vote;
    }
}
