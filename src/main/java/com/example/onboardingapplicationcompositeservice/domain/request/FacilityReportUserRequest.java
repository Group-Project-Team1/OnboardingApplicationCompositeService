package com.example.onboardingapplicationcompositeservice.domain.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FacilityReportUserRequest {
    private Integer facilityId;
    private String description;
    private String title;
}
