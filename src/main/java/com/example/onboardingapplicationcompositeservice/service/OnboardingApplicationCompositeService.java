package com.example.onboardingapplicationcompositeservice.service;

import com.example.onboardingapplicationcompositeservice.domain.entity.ApplicationService.ApplicationWorkFlow;
import com.example.onboardingapplicationcompositeservice.domain.entity.ApplicationService.DigitalDocument;
import com.example.onboardingapplicationcompositeservice.domain.entity.EmployeeService.Address;
import com.example.onboardingapplicationcompositeservice.domain.entity.EmployeeService.PersonalDocument;
import com.example.onboardingapplicationcompositeservice.domain.entity.EmployeeService.Employee;
import com.example.onboardingapplicationcompositeservice.domain.entity.EmployeeService.VisaStatus;
import com.example.onboardingapplicationcompositeservice.domain.request.ApplicationFormRequest;
import com.example.onboardingapplicationcompositeservice.domain.response.AllApplicationResponse;
import com.example.onboardingapplicationcompositeservice.domain.response.ApplicationInfoResponse;
import com.example.onboardingapplicationcompositeservice.domain.response.ApplicationResponse;
import com.example.onboardingapplicationcompositeservice.domain.response.VisaStatusManagementResponse;
import com.example.onboardingapplicationcompositeservice.security.AuthUserDetail;
import com.example.onboardingapplicationcompositeservice.service.remote.RemoteApplicationService;
import com.example.onboardingapplicationcompositeservice.service.remote.RemoteEmployeeService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OnboardingApplicationCompositeService {
    private RemoteEmployeeService employeeService;
    private RemoteApplicationService applicationService;
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Autowired
    public void setRemoteEmployeeService(RemoteEmployeeService employeeService){
        this.employeeService = employeeService;
    }

    @Autowired
    public void setRemoteApplicationService(RemoteApplicationService applicationService){
        this.applicationService = applicationService;
    }

    public void submitApplicationForm(String token,
                                      Integer employeeId,
                                      ApplicationFormRequest applicationFormRequest,
                                      MultipartFile driverLicense,
                                      MultipartFile OPTReceipt){

        // update employee info in mongoDB
        Employee employee = employeeService.findEmployeeById(token,employeeId);
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
        employeeService.updateEmployee(token, employee);
//
        if(driverLicense != null){
            // upload driverLicense and update personalDocuments in mongoDB
            String driverLicenseFileName = applicationService.uploadFile(token, driverLicense);
            PersonalDocument personalDocument = new PersonalDocument();
            String driverLicenseURL = applicationService.getFileUrl(token, driverLicenseFileName);
            personalDocument.setTitle("Driver License");
            personalDocument.setPath(driverLicenseURL);
            personalDocument.setCreateDate(Timestamp.valueOf(LocalDateTime.now()));
            employeeService.addPersonalDocument(token, employeeId, personalDocument);
        }

        if(OPTReceipt != null){
            // upload OPT Receipt and update personalDocuments in mongoDB
            String OPTFileName = applicationService.uploadFile(token, OPTReceipt);
            PersonalDocument personalDocument = new PersonalDocument();
            String OPTReceiptURL = applicationService.getFileUrl(token, OPTFileName);
            personalDocument.setTitle("OPT Receipt");
            personalDocument.setPath(OPTReceiptURL);
            personalDocument.setCreateDate(Timestamp.valueOf(LocalDateTime.now()));
            employeeService.addPersonalDocument(token, employeeId, personalDocument);
            applicationService.submitApplicationForm(token, employeeId, OPTReceiptURL);
        }

    }


    public void submitApplication(String token, Integer employeeId, List<MultipartFile> files){
        List<DigitalDocument> digitalDocumentList = applicationService.getDocuments(token);
        for(int i = 0; i < digitalDocumentList.size(); i++){
            String fileName = applicationService.uploadFile(token, files.get(i));
            String fileTile = digitalDocumentList.get(i).getTitle();
            PersonalDocument personalDocument = new PersonalDocument();
            String url = applicationService.getFileUrl(token, fileName);
            personalDocument.setTitle(fileTile);
            personalDocument.setPath(url);
            personalDocument.setCreateDate(Timestamp.valueOf(LocalDateTime.now()));
            employeeService.addPersonalDocument(token, employeeId, personalDocument);
        }
        applicationService.submitApplication(token, employeeId);

    }

    public Employee findEmployeeById(String token, Integer employeeId){
        return employeeService.findEmployeeById(token, employeeId);
    }

    public ApplicationWorkFlow getApplicationByEmployeeId(String token, Integer employeeId){
        return applicationService.getApplicationByEmployeeId(token, employeeId);
    }

    public AllApplicationResponse getApplicationsByStatus(String token, String status){
        List<ApplicationWorkFlow> applications = applicationService.getApplicationsByStatus(token, status);
        List<ApplicationInfoResponse> applicationInfoResponses = new ArrayList<>();
        for(ApplicationWorkFlow applicationWorkFlow : applications){
            Employee employee = employeeService.findEmployeeById(token, applicationWorkFlow.getEmployeeId());
            applicationInfoResponses.add(ApplicationInfoResponse.builder()
                    .fullName(employee.getFirstName() + " " + employee.getLastName())
                    .email(employee.getEmail())
                    .status(applicationWorkFlow.getStatus())
                    .build());
        }
        return AllApplicationResponse.builder().applicationInfos(applicationInfoResponses).build();
    }

    public void submitVisaDocuments(String token, Integer employeeId, MultipartFile file, Integer fileId, String fileType){
        String fileName = applicationService.uploadFile(token, file);
        PersonalDocument personalDocument = new PersonalDocument();
        String url = applicationService.getFileUrl(token, fileName);
        personalDocument.setTitle(fileType);
        personalDocument.setPath(url);
        personalDocument.setCreateDate(Timestamp.valueOf(LocalDateTime.now()));
        employeeService.addPersonalDocument(token, employeeId, personalDocument);
        applicationService.submitVisaDocuments(token, employeeId, fileId, url);
    }

    public VisaStatusManagementResponse getVisaStatus(String token, Integer employeeId){
        return applicationService.getVisaStatus(token, employeeId);
    }

    public ResponseEntity<Object> reviewApplication(String token, Integer employeeId, String action, String feedback){
        applicationService.reviewApplication(token, employeeId, action, feedback);
        if(action.equals("approve")){
            return new ResponseEntity<>("This application has been successfully approved.", HttpStatus.OK);
        }else{
            Employee employee = findEmployeeById(token, employeeId);
            String messageBody = feedback;
            Message message = new Message(messageBody.getBytes(StandardCharsets.UTF_8));
            message.getMessageProperties().getHeaders().put("subject", "Feedback of your application");
            message.getMessageProperties().getHeaders().put("recipient", employee.getEmail());
            System.out.println(employee.getEmail());
            rabbitTemplate.convertAndSend("email.direct", "emailKey", message);
            return new ResponseEntity<>("This application has been successfully rejected. Feedback has been sent to the employee.", HttpStatus.OK);
        }
    }

}
