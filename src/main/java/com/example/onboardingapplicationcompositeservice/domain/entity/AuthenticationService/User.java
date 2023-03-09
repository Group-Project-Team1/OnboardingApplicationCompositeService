package com.example.onboardingapplicationcompositeservice.domain.entity.AuthenticationService;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.sql.Timestamp;
import java.util.Set;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {

    private Integer id;

    private String username;

    private String email;

    private String password;

    private Timestamp createDate;

    private Timestamp lastModificationDate;

    private Boolean activeFlag;

    @JsonIgnore
    @ToString.Exclude
    private Set<RegistrationToken> registrationTokens;

    @JsonIgnore
    @ToString.Exclude
    private Set<Role> roles;
}
