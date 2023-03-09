package com.example.onboardingapplicationcompositeservice.domain.request;


import com.example.onboardingapplicationcompositeservice.domain.entity.EmployeeService.Address;
import com.example.onboardingapplicationcompositeservice.domain.entity.EmployeeService.Contact;
import com.example.onboardingapplicationcompositeservice.domain.entity.EmployeeService.Employee;
import com.example.onboardingapplicationcompositeservice.domain.entity.EmployeeService.VisaStatus;
import lombok.*;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ApplicationFormRequest {
    private String firstName;
    private String lastName;
    private String middleName;
    private String preferredName;
    //    private String profilePicturePath;
    private Address address;
    private String email;
    private String cellPhoneNum;
    private String workPhoneNum;

    private String gender;
    private LocalDate dateOfBirth;
    private String SSN;
    //    private Car car;

    private VisaStatus visaStatus;
    private String driverLicenseNumber;
    private LocalDate expirationDate;
    private Employee reference;
    private List<Contact> contacts;

}
