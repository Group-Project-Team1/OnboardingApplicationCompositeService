package com.example.onboardingapplicationcompositeservice.domain.entity.AuthenticationService;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.sql.Timestamp;


@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RegistrationToken {

    private Integer id;


    private String token;


    private String email;


    private Timestamp expirationDate;

    @JsonIgnore
    @ToString.Exclude
    private User user;
}
