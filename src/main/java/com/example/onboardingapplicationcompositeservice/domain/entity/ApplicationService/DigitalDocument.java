package com.example.onboardingapplicationcompositeservice.domain.entity.ApplicationService;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DigitalDocument {
    private int id;

    private boolean isRequired;

    private String type;

    private String title;

    private String path;

    private String description;
}

