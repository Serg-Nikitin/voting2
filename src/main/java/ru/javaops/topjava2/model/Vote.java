package ru.javaops.topjava2.model;


import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity()
@Getter
@Setter
@Table(indexes = {@Index(name = "vote_idx", columnList = "date_vote,restaurant_id")})
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Vote extends BaseEntity{

    @Column(name = "date_vote", columnDefinition = "timestamp default now()")
    private Date dateVote;

    @ManyToOne()
    private Restaurant restaurant;

    @ManyToOne()
    private User user;

    private Boolean vote;

}
