package com.example.onboardingapplicationcompositeservice.domain.entity.ApplicationService;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class VisaDocumentStatus {

    private Integer id;

    private Integer employeeId;

    private Integer fileId;

    private String path;

    private String status;
}