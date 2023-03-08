package com.example.onboardingapplicationcompositeservice.domain.entity.EmployeeService;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Address {

    @ToString.Exclude
    private Integer id;
    private String addressLine1;
    private String addressLine2;
    private String City;
    private String State;
    private String zipCode;

}
