package com.example.onboardingapplicationcompositeservice.service;

import com.example.onboardingapplicationcompositeservice.domain.entity.EmployeeService.Address;
import com.example.onboardingapplicationcompositeservice.domain.entity.EmployeeService.PersonalDocument;
import com.example.onboardingapplicationcompositeservice.domain.entity.EmployeeService.Employee;
import com.example.onboardingapplicationcompositeservice.domain.entity.EmployeeService.VisaStatus;
import com.example.onboardingapplicationcompositeservice.domain.request.ApplicationFormRequest;
import com.example.onboardingapplicationcompositeservice.service.remote.RemoteApplicationService;
import com.example.onboardingapplicationcompositeservice.service.remote.RemoteEmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OnboardingApplicationCompositeService {
    private RemoteEmployeeService employeeService;
    private RemoteApplicationService applicationService;
    @Autowired
    public void setRemoteEmployeeService(RemoteEmployeeService employeeService){
        this.employeeService = employeeService;
    }

    @Autowired
    public void setRemoteApplicationService(RemoteApplicationService applicationService){
        this.applicationService = applicationService;
    }

    public void submitApplicationForm(Integer employeeId,
                                      ApplicationFormRequest applicationFormRequest
//                                      MultipartFile driverLicense,
                                     /* MultipartFile OPTReceipt */){

        // update employee info in mongoDB
        Employee employee = employeeService.findEmployeeById(employeeId);
        employee.setFirstName(applicationFormRequest.getFirstName());
        employee.setLastName(applicationFormRequest.getLastName());
        employee.setMiddleName(applicationFormRequest.getMiddleName());
        employee.setPreferredName(applicationFormRequest.getPreferredName());
        List<Address> addressList = new ArrayList<>();
        addressList.add(applicationFormRequest.getAddress());
        employee.setAddresses(addressList);
        employee.setCellPhone(applicationFormRequest.getCellPhoneNum());
        employee.setAlternatePhone(applicationFormRequest.getWorkPhoneNum());
        employee.setGender(applicationFormRequest.getGender());
        employee.setDob(applicationFormRequest.getDateOfBirth());
        employee.setSsn(applicationFormRequest.getSSN());
        List<VisaStatus> visaStatusList = new ArrayList<>();
        visaStatusList.add(applicationFormRequest.getVisaStatus());
        employee.setVisaStatuses(visaStatusList);
        employee.setDriverLicense(applicationFormRequest.getDriverLicenseNumber());
        employee.setDriverLicenseExpiration(applicationFormRequest.getExpirationDate());
        employee.setContacts(applicationFormRequest.getContacts());
        employeeService.updateEmployee(employee);
//
//        if(driverLicense != null){
//            // upload driverLicense and update personalDocuments in mongoDB
//            String driverLicenseFileName = applicationService.uploadFile(driverLicense).getBody().toString();
//            PersonalDocument personalDocument = new PersonalDocument();
//            String driverLicenseURL = applicationService.getFileUrl(driverLicenseFileName).getBody().toString();
//            personalDocument.setTitle("Driver License");
//            personalDocument.setPath(driverLicenseURL);
//            personalDocument.setCreateDate(Timestamp.valueOf(LocalDateTime.now()));
//            employeeService.addPersonalDocument(employeeId, personalDocument);
//        }

//        if(OPTReceipt != null){
//            // upload OPT Receipt and update personalDocuments in mongoDB
//            String OPTFileName = applicationService.uploadFile(OPTReceipt).getBody().toString();
//            PersonalDocument personalDocument = new PersonalDocument();
//            String OPTReceiptURL = applicationService.getFileUrl(OPTFileName).getBody().toString();
//            personalDocument.setTitle("OPT Receipt");
//            personalDocument.setPath(OPTReceiptURL);
//            personalDocument.setCreateDate(Timestamp.valueOf(LocalDateTime.now()));
//            employeeService.addPersonalDocument(employeeId, personalDocument);
//            applicationService.submitApplicationForm(employeeId, OPTReceiptURL);
//        }

    }
}
