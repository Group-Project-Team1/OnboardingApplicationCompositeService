package com.example.onboardingapplicationcompositeservice.domain.entity.ApplicationService;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ApplicationWorkFlow {

    private int id;

    private int employeeId;

    private Timestamp createDate;

    private Timestamp lastModificationDate;

    private String status;

    private String comment;
}

