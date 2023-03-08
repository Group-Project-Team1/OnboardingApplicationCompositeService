package com.example.onboardingapplicationcompositeservice.controller;

import com.example.onboardingapplicationcompositeservice.domain.request.ApplicationFormRequest;
import com.example.onboardingapplicationcompositeservice.service.OnboardingApplicationCompositeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("composite")
public class OnboardingApplicationCompositeController {
    private OnboardingApplicationCompositeService onboardingApplicationCompositeService;

    @Autowired
    public void setOnboardingApplicationCompositeService(OnboardingApplicationCompositeService onboardingApplicationCompositeService){
        this.onboardingApplicationCompositeService = onboardingApplicationCompositeService;
    }

    @PostMapping(value = "{employeeId}/applicationForm")
    public ResponseEntity<Object> submitApplicationForm(@PathVariable("employeeId") Integer employeeId,
                                                        @RequestPart("applicationFormRequest") ApplicationFormRequest applicationFormRequest,
                                                        @RequestPart("driverLicense") MultipartFile driverLicense,
                                                        @RequestPart("OPTReceipt") MultipartFile OPTReceipt){
        onboardingApplicationCompositeService.submitApplicationForm(employeeId, applicationFormRequest, driverLicense, OPTReceipt);
        return new ResponseEntity<>("Application form submitted successfully", HttpStatus.OK);
    }
}
