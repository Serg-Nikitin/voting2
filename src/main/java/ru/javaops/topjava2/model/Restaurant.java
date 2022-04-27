package ru.javaops.topjava2.model;


import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Restaurant extends NamedEntity implements Serializable {

    private Address address;

    @OneToMany(mappedBy = "restaurant")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Vote> votes;

    @OneToMany(mappedBy = "restaurant")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Set<Dish> dishes;

    public Restaurant(Integer id, String name, Address address) {
        this(id, name, address, null);
    }

    public Restaurant(Integer id, String name, Address address, Set<Vote> votes) {
        super(id, name);
        this.address = address;
        this.votes = votes;
    }
}
