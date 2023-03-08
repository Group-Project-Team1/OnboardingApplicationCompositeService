package com.example.onboardingapplicationcompositeservice.domain.entity.EmployeeService;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Contact {

    @ToString.Exclude
    private Integer id;
    private String firstName;
    private String lastName;
    private String cellPhone;
    private String AlternatePhone;
    private String email;
    private String Relationship;
    @ToString.Exclude
    private String type;
}

