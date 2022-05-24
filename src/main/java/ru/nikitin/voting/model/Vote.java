package ru.nikitin.voting.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.nikitin.voting.util.validation.present.Present;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity()
@Getter
@Setter
@Table(indexes = {@Index(name = "vote_idx", columnList = "vote_date")},
        uniqueConstraints = @UniqueConstraint(columnNames = {"vote_date", "user_id"}, name = "uk_vote"))
@ToString(callSuper = true, exclude = {"restaurant", "user"})
@NoArgsConstructor
public class Vote extends BaseEntity {

    @Column(name = "vote_date")
    @NotNull
    @Present
    private LocalDate voteDate;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    public Vote(LocalDate voteDate) {
        this(null, voteDate, null, null);
    }

    public Vote(Integer id, LocalDate voteDate, Restaurant restaurant, User user) {
        super(id);
        this.voteDate = voteDate;
        this.restaurant = restaurant;
        this.user = user;
    }

    public Vote set(Restaurant restaurant, User user) {
        this.setRestaurant(restaurant);
        this.setUser(user);
        return this;
    }
}
