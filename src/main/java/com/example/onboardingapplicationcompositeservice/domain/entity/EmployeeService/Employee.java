package com.example.onboardingapplicationcompositeservice.domain.entity.EmployeeService;

import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Employee {

    private Integer id;
    private Integer userId;
    private String firstName;
    private String lastName;
    private String middleName;
    private String preferredName;
    private String email;
    private String cellPhone;
    private String alternatePhone;
    private String gender;
    private String ssn;
    private LocalDate dob;
    private LocalDate startDate;
    private LocalDate endDate;
    private String driverLicense;
    private LocalDate driverLicenseExpiration;
    private Integer houseId;
    private List<Contact> contacts;
    private List<Address> addresses;
    private List<VisaStatus> visaStatuses;
    private List<PersonalDocument> personalDocuments = new ArrayList<>();


}

