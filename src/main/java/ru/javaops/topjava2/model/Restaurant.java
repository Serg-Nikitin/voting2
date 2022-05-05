package ru.javaops.topjava2.model;


import lombok.*;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Restaurant extends NamedEntity implements Serializable {

    @NotNull
    private Address address;

    public Restaurant(Integer id, String name, Address address) {
        super(id, name);
        this.address = address;
    }
}
