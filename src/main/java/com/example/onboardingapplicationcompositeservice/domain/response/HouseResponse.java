package com.example.onboardingapplicationcompositeservice.domain.response;

import com.example.onboardingapplicationcompositeservice.domain.entity.HouseService.House;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class HouseResponse {
    House house;
}
