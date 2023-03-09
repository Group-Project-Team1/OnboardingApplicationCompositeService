package com.example.onboardingapplicationcompositeservice.domain.response;

import com.example.onboardingapplicationcompositeservice.domain.entity.HouseService.House;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class HouseDetailResponse {
    House house;
    List<EmployeeSummary> employeeSummaries;
}
