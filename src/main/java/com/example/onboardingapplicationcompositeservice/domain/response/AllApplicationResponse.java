package com.example.onboardingapplicationcompositeservice.domain.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class AllApplicationResponse {
    List<ApplicationInfoResponse> applicationInfos;
}
