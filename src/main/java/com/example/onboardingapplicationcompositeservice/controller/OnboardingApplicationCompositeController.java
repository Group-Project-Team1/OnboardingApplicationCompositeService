package com.example.onboardingapplicationcompositeservice.controller;

import com.example.onboardingapplicationcompositeservice.domain.entity.ApplicationService.ApplicationForm;
import com.example.onboardingapplicationcompositeservice.domain.entity.ApplicationService.ApplicationWorkFlow;
import com.example.onboardingapplicationcompositeservice.domain.entity.EmployeeService.Employee;
import com.example.onboardingapplicationcompositeservice.domain.request.ApplicationFormRequest;
import com.example.onboardingapplicationcompositeservice.domain.response.AllApplicationResponse;
import com.example.onboardingapplicationcompositeservice.domain.response.ApplicationResponse;
import com.example.onboardingapplicationcompositeservice.domain.response.VisaStatusManagementResponse;
import com.example.onboardingapplicationcompositeservice.service.OnboardingApplicationCompositeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("composite")
public class OnboardingApplicationCompositeController {
    private OnboardingApplicationCompositeService onboardingApplicationCompositeService;

    @Autowired
    public void setOnboardingApplicationCompositeService(OnboardingApplicationCompositeService onboardingApplicationCompositeService){
        this.onboardingApplicationCompositeService = onboardingApplicationCompositeService;
    }

    /**
     * submit application form
     * @param employeeId
     * @param applicationFormRequest
     * @param driverLicense
     * @param OPTReceipt
     * @return
     */
    @PostMapping(value = "{employeeId}/applicationForm")
    public ResponseEntity<Object> submitApplicationForm(@PathVariable("employeeId") Integer employeeId,
                                                        @RequestPart("applicationFormRequest") ApplicationFormRequest applicationFormRequest,
                                                        @RequestPart("driverLicense") MultipartFile driverLicense,
                                                        @RequestPart("OPTReceipt") MultipartFile OPTReceipt){

        onboardingApplicationCompositeService.submitApplicationForm(employeeId, applicationFormRequest, driverLicense, OPTReceipt);
        return new ResponseEntity<>("Application form submitted successfully", HttpStatus.OK);
    }


    /**
     * documents and submit the entire application
     * @param employeeId
     * @param files
     * @return
     */
    @PostMapping("/{employeeId}/application")
    public ResponseEntity<Object> submitApplication(@PathVariable Integer employeeId, @RequestParam("files") List<MultipartFile> files){
        onboardingApplicationCompositeService.submitApplication(employeeId, files);
        return new ResponseEntity<>("Pleas wait for HR to review your application.", HttpStatus.OK);
    }


    /**
     * employee checks his/her application
     * @param employeeId
     * @return
     */
    @GetMapping("/{employeeId}/application")
    public ApplicationResponse getApplication(@PathVariable Integer employeeId){
        Employee employee = onboardingApplicationCompositeService.findEmployeeById(employeeId);
        ApplicationWorkFlow applicationWorkFlow = onboardingApplicationCompositeService.getApplicationByEmployeeId(employeeId);

        String status = applicationWorkFlow.getStatus();
        if(status.equals("approved")){
            return ApplicationResponse.builder()
                    .message("Your application has been approved.")
                    .applicationForm(new ApplicationForm(employee))
                    .personalDocuments(employee.getPersonalDocuments())
                    .build();
        }else if(status.equals("pending")){
            return ApplicationResponse.builder()
                    .message("Please wait for HR to review your application.")
                    .applicationForm(new ApplicationForm(employee))
                    .personalDocuments(employee.getPersonalDocuments())
                    .build();
        }else{
            return ApplicationResponse.builder()
                    .message(applicationWorkFlow.getComment())
                    .applicationForm(new ApplicationForm(employee))
                    .personalDocuments(employee.getPersonalDocuments())
                    .build();
        }
    }


    /**
     * hr checks applications by status
     * @param status
     * @return
     */
    @GetMapping("/{status}/applications")
    public AllApplicationResponse getApplicationsByStatus(@PathVariable String status){
        return onboardingApplicationCompositeService.getApplicationsByStatus(status);
    }


    /**
     * hr views application
     * @param employeeId
     * @return
     */
    @GetMapping("/viewApplication/{employeeId}")
    public ApplicationResponse viewApplication(@PathVariable Integer employeeId){
        Employee employee = onboardingApplicationCompositeService.findEmployeeById(employeeId);
        return ApplicationResponse.builder()
                .message("")
                .applicationForm(new ApplicationForm(employee))
                .personalDocuments(employee.getPersonalDocuments())
                .build();
    }


    /**
     * employee uploads visa document
     * @param employeeId
     * @param file
     * @param fileId
     * @param fileType
     * @return
     */
    @PostMapping("/{employeeId}/visaStatusManagement")
    public ResponseEntity<Object> submitVisaDocuments(@PathVariable Integer employeeId,
                                    @RequestPart("fileId") Integer fileId,
                                    @RequestPart("file") MultipartFile file,
                                    @RequestPart("fileType") String fileType){
        onboardingApplicationCompositeService.submitVisaDocuments(employeeId, file, fileId, fileType);
        return new ResponseEntity<>("Waiting for HR to approve your " + fileType, HttpStatus.OK);
    }


    /**
     * employee gets his/her visaStatusManagement page
     * @param employeeId
     * @return
     */
    @GetMapping("{employeeId}/visaStatusManagement")
    public VisaStatusManagementResponse getVisaStatus(@PathVariable Integer employeeId){
        return onboardingApplicationCompositeService.getVisaStatus(employeeId);
    }

}
