package ru.nikitin.voting.model;


import lombok.*;
import ru.nikitin.voting.util.validation.nohtml.NoHtml;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Embeddable
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

    @NoHtml
    @Size(min = 2, max = 100)
    @NotBlank
    private String country;

    @NoHtml
    @Size(min = 2, max = 100)
    private String district;

    @NoHtml
    @Size(min = 2, max = 100)
    private String locality;

    @NoHtml
    @Size(min = 2, max = 100)
    private String street;

    @NoHtml
    private String number;

    public Address(String country, String district, String locality, String street, String number) {
        this.country = country;
        this.district = district;
        this.locality = locality;
        this.street = street;
        this.number = number;
    }
}
