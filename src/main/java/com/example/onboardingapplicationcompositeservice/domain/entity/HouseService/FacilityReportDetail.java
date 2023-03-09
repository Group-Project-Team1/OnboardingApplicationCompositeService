package com.example.onboardingapplicationcompositeservice.domain.entity.HouseService;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class FacilityReportDetail {

    private Integer id;

    @ToString.Exclude
    @JsonIgnore
    private FacilityReport facilityReport;

    private Integer employeeId;

    private Timestamp createDate;

    private Timestamp lastModificationDate;

    private String comment;

    public FacilityReportDetail(FacilityReport facilityReport, Integer employeeId, Timestamp createDate, Timestamp lastModificationDate, String comment) {
        this.facilityReport = facilityReport;
        this.employeeId = employeeId;
        this.createDate = createDate;
        this.lastModificationDate = lastModificationDate;
        this.comment = comment;
    }
}
