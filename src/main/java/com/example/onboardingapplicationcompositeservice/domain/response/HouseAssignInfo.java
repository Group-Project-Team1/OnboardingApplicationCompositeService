package com.example.onboardingapplicationcompositeservice.domain.response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HouseAssignInfo {
    int houseId;
    int maxOccupant;
}
