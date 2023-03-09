package com.example.onboardingapplicationcompositeservice.domain.entity.HouseService;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class House {
    private Integer id;
    private Integer maxOccupant;
    private Landlord landlord;
    private String address;
    private Set<Facility> facilities;
}
