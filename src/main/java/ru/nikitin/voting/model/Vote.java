package ru.nikitin.voting.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;
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
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @Present
    private LocalDate voteDate;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    public Vote(LocalDate voteDate, Restaurant restaurant) {
        this(null, voteDate, restaurant, null);
    }

    public Vote(LocalDate voteDate, Restaurant restaurant, User user) {
        this(null, voteDate, restaurant, user);
    }

    public Vote(Integer id, LocalDate voteDate, Restaurant restaurant, User user) {
        super(id);
        this.voteDate = voteDate;
        this.restaurant = restaurant;
        this.user = user;
    }
}
