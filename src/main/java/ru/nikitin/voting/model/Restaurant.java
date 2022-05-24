package ru.nikitin.voting.model;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@Table(name = "restaurant", uniqueConstraints = @UniqueConstraint(name = "restaurant_uk", columnNames = {"name", "country", "district", "locality", "street", "number"}))
@ToString(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Restaurant extends NamedEntity {

    @NotNull
    private Address address;

    public Restaurant(Integer id, String name, Address address) {
        super(id, name);
        this.address = address;
    }
}
