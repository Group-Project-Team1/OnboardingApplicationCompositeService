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
public class Facility {

    private Integer id;

    @ToString.Exclude
    @JsonIgnore
    private House house;

    private String type;

    private String description;

    private String quantity;

    @ToString.Exclude
    private Set<FacilityReport> facilityReports;
}
