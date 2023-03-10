package com.example.onboardingapplicationcompositeservice.domain.entity.HouseService;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FacilityReportDetailInfo {
    private String name;
    private Timestamp lastModificationDate;
    private String comment;
}
