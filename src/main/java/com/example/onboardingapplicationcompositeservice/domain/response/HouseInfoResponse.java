package com.example.onboardingapplicationcompositeservice.domain.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class HouseInfoResponse {

    String address;
    List<Roommate> roommates;
}
