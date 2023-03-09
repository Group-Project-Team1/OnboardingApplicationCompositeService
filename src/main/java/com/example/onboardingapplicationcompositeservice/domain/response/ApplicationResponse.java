package com.example.onboardingapplicationcompositeservice.domain.response;

import com.example.onboardingapplicationcompositeservice.domain.entity.ApplicationService.ApplicationForm;
import com.example.onboardingapplicationcompositeservice.domain.entity.EmployeeService.Employee;
import com.example.onboardingapplicationcompositeservice.domain.entity.EmployeeService.PersonalDocument;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationResponse {

    String message;
    ApplicationForm applicationForm;
    List<PersonalDocument> personalDocuments;

}
