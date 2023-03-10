package com.example.onboardingapplicationcompositeservice.domain.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AllHouseResponse {
    List<HouseAssignInfo> houseAssignInfo;
}
