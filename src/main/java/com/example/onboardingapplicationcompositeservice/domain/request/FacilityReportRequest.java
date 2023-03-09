package com.example.onboardingapplicationcompositeservice.domain.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class FacilityReportRequest {
    private Integer facilityId;
    private Integer employeeId;
    private String description;
    private String title;
}
