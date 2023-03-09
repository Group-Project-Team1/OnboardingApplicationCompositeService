package com.example.onboardingapplicationcompositeservice.domain.entity.ApplicationService;

import com.example.onboardingapplicationcompositeservice.domain.entity.EmployeeService.Address;
import com.example.onboardingapplicationcompositeservice.domain.entity.EmployeeService.Contact;
import com.example.onboardingapplicationcompositeservice.domain.entity.EmployeeService.Employee;
import com.example.onboardingapplicationcompositeservice.domain.entity.EmployeeService.VisaStatus;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ApplicationForm {

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

    public ApplicationForm(Employee employee) {
        this.firstName = employee.getFirstName();
        this.middleName = employee.getMiddleName();
        this.lastName = employee.getLastName();
        this.preferredName = employee.getPreferredName();
        this.email = employee.getEmail();
        this.cellPhone = employee.getCellPhone();
        this.alternatePhone = employee.getAlternatePhone();
        this.gender = employee.getGender();
        this.ssn = employee.getSsn();
        this.dob = employee.getDob();
        this.startDate = employee.getStartDate();
        this.endDate = employee.getEndDate();
        this.driverLicense = employee.getDriverLicense();
        this.driverLicenseExpiration = employee.getDriverLicenseExpiration();
        this.contacts = employee.getContacts();
        this.addresses = employee.getAddresses();
        this.visaStatuses = employee.getVisaStatuses();
    }
}

