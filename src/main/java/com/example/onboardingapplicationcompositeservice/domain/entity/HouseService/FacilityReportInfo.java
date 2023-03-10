package com.example.onboardingapplicationcompositeservice.domain.entity.HouseService;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.sql.Timestamp;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FacilityReportInfo {
    private Integer id;

    private String facilityType;

    private String name;

    private String description;

    private String title;

    private String status;

    private Timestamp createDate;

    @ToString.Exclude
    private Set<FacilityReportDetail> facilityReportDetails;

}
