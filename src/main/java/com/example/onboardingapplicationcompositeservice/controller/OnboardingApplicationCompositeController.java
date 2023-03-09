package com.example.onboardingapplicationcompositeservice.controller;

import com.example.onboardingapplicationcompositeservice.domain.entity.ApplicationService.ApplicationForm;
import com.example.onboardingapplicationcompositeservice.domain.entity.ApplicationService.ApplicationWorkFlow;
import com.example.onboardingapplicationcompositeservice.domain.entity.EmployeeService.Employee;
import com.example.onboardingapplicationcompositeservice.domain.request.ApplicationFormRequest;
import com.example.onboardingapplicationcompositeservice.domain.response.AllApplicationResponse;
import com.example.onboardingapplicationcompositeservice.domain.response.ApplicationResponse;
import com.example.onboardingapplicationcompositeservice.domain.response.VisaStatusManagementResponse;
import com.example.onboardingapplicationcompositeservice.security.AuthUserDetail;
import com.example.onboardingapplicationcompositeservice.security.JwtProvider;
import com.example.onboardingapplicationcompositeservice.service.OnboardingApplicationCompositeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("composite-application")
public class OnboardingApplicationCompositeController {
    private OnboardingApplicationCompositeService onboardingApplicationCompositeService;

    @Autowired
    private JwtProvider jwtProvider;
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
    @PostMapping(value = "/employee/{employeeId}/applicationForm")
    @PreAuthorize("hasAuthority('employee')")
    public ResponseEntity<Object> submitApplicationForm(@PathVariable("employeeId") Integer employeeId,
                                                        @RequestPart("applicationFormRequest") ApplicationFormRequest applicationFormRequest,
                                                        @RequestPart("driverLicense") MultipartFile driverLicense,
                                                        @RequestPart("OPTReceipt") MultipartFile OPTReceipt){

        onboardingApplicationCompositeService.submitApplicationForm("Bearer:"+jwtProvider.createToken((AuthUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()), employeeId, applicationFormRequest, driverLicense, OPTReceipt);
        return new ResponseEntity<>("Application form submitted successfully", HttpStatus.OK);
    }


    /**
     * documents and submit the entire application
     * @param employeeId
     * @param files
     * @return
     */
    @PostMapping("/employee/{employeeId}/application")
    @PreAuthorize("hasAuthority('employee')")
    public ResponseEntity<Object> submitApplication(@PathVariable Integer employeeId, @RequestParam("files") List<MultipartFile> files){
        onboardingApplicationCompositeService.submitApplication("Bearer:"+jwtProvider.createToken((AuthUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()), employeeId, files);
        return new ResponseEntity<>("Pleas wait for HR to review your application.", HttpStatus.OK);
    }


    /**
     * employee checks his/her application
     * @param employeeId
     * @return
     */
    @GetMapping("/employee/{employeeId}/application")
    @PreAuthorize("hasAuthority('employee')")
    public ApplicationResponse getApplication(@PathVariable Integer employeeId){
        Employee employee = onboardingApplicationCompositeService.findEmployeeById("Bearer:"+jwtProvider.createToken((AuthUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()), employeeId);
        ApplicationWorkFlow applicationWorkFlow = onboardingApplicationCompositeService.getApplicationByEmployeeId("Bearer:"+jwtProvider.createToken((AuthUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()), employeeId);

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
    @GetMapping("/hr/{status}/applications")
    @PreAuthorize("hasAuthority('hr')")
    public AllApplicationResponse getApplicationsByStatus(@PathVariable String status){
        return onboardingApplicationCompositeService.getApplicationsByStatus("Bearer:"+jwtProvider.createToken((AuthUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()), status);
    }


    /**
     * hr views application
     * @param employeeId
     * @return
     */
    @GetMapping("/hr/viewApplication/{employeeId}")
    @PreAuthorize("hasAuthority('hr')")
    public ApplicationResponse viewApplication(@PathVariable Integer employeeId){
        Employee employee = onboardingApplicationCompositeService.findEmployeeById("Bearer:"+jwtProvider.createToken((AuthUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()), employeeId);
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
    @PostMapping("/employee/{employeeId}/visaStatusManagement")
    @PreAuthorize("hasAuthority('employee')")
    public ResponseEntity<Object> submitVisaDocuments(@PathVariable Integer employeeId,
                                                      @RequestPart("fileId") Integer fileId,
                                                      @RequestPart("file") MultipartFile file,
                                                      @RequestPart("fileType") String fileType){
        onboardingApplicationCompositeService.submitVisaDocuments("Bearer:"+jwtProvider.createToken((AuthUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()), employeeId, file, fileId, fileType);
        return new ResponseEntity<>("Waiting for HR to approve your " + fileType, HttpStatus.OK);
    }


    /**
     * employee gets his/her visaStatusManagement page
     * @param employeeId
     * @return
     */
    @GetMapping("/employee/{employeeId}/visaStatusManagement")
    @PreAuthorize("hasAuthority('employee')")
    public VisaStatusManagementResponse getVisaStatus(@PathVariable Integer employeeId){
        return onboardingApplicationCompositeService.getVisaStatus("Bearer:"+jwtProvider.createToken((AuthUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()), employeeId);
    }


    /**
     * hr reviews an application
     * @param employeeId
     * @param action
     * @param feedback
     */
    @PostMapping("/hr/viewApplication/{employeeId}")
    @PreAuthorize("hasAuthority('hr')")
    public ResponseEntity<Object> reviewApplication(@PathVariable Integer employeeId, @RequestParam String action, @RequestParam String feedback){
        return onboardingApplicationCompositeService.reviewApplication("Bearer:"+jwtProvider.createToken((AuthUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()), employeeId, action, feedback);
    }

}
