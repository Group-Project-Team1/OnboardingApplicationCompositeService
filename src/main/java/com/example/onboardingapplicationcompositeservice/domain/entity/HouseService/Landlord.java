package com.example.onboardingapplicationcompositeservice.domain.entity.HouseService;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Landlord {
    private Integer id;

    private String firstName;

    private String lastName;

    private String email;

    private String cellphone;

    @JsonIgnore
    @ToString.Exclude
    private Set<House> houses;
}
