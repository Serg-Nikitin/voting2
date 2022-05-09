package ru.javaops.topjava2.model;


import lombok.*;
import ru.javaops.topjava2.util.validation.nohtml.NoHtml;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address {

    @NoHtml
    @Size(min = 2, max = 100)
    @NotBlank
    private String country;

    @NoHtml
    @NotBlank
    @Size(min = 2, max = 100)
    private String district;

    @NoHtml
    @NotBlank
    @Size(min = 2, max = 100)
    private String locality;

    @NoHtml
    @NotBlank
    @Size(min = 2, max = 100)
    private String street;

    @NoHtml
    @NotNull
    private Integer number;

    public Address(String country, String district, String locality, String street, Integer number) {
        this.country = country;
        this.district = district;
        this.locality = locality;
        this.street = street;
        this.number = number;
    }
}
