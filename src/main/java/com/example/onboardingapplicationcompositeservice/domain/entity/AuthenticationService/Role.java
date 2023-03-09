package com.example.onboardingapplicationcompositeservice.domain.entity.AuthenticationService;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.sql.Timestamp;
import java.util.Set;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Role {

    private Integer id;


    private String roleName;


    private String roleDescription;


    private Timestamp createDate;


    private Timestamp lastModificationDate;

    @ToString.Exclude
    @JsonIgnore
    private Set<User> users;
}
