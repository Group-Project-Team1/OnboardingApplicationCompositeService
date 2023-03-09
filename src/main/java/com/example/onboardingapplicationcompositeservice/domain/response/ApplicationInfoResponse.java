package com.example.onboardingapplicationcompositeservice.domain.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ApplicationInfoResponse {
    String fullName;
    String email;
    String status;
}
