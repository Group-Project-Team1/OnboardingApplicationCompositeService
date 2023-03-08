package com.example.onboardingapplicationcompositeservice.domain.entity.EmployeeService;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class VisaStatus {
    private Integer id;
    private String visaType;
    private Boolean activeFlag;
    private LocalDate startDate;
    private LocalDate endDate;
    private LocalDate lastModificationDate;
//    @ToString.Exclude
//    private Employee employee;
}

