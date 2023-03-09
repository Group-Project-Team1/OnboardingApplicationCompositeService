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
public class FacilityReport {
    private Integer id;

    @ToString.Exclude
    @JsonIgnore
    private Facility facility;

    private Integer employeeId;

    private String description;

    private String title;

    private String status;

    private Timestamp createDate;

    @ToString.Exclude
    private Set<FacilityReportDetail> facilityReportDetails;


    public FacilityReport(Facility facility, Integer employeeId, String title, String description, String status, Timestamp createDate) {
        this.facility = facility;
        this.employeeId = employeeId;
        this.description = description;
        this.title = title;
        this.status = status;
        this.createDate = createDate;
    }
}
