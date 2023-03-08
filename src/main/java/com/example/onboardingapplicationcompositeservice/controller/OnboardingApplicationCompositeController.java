package com.example.onboardingapplicationcompositeservice.controller;

import com.example.onboardingapplicationcompositeservice.domain.request.ApplicationFormRequest;
import com.example.onboardingapplicationcompositeservice.service.OnboardingApplicationCompositeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @PostMapping("{employeeId}/applicationForm")
    public ResponseEntity<Object> submitApplicationForm(@PathVariable("employeeId") Integer employeeId,
                                                        @RequestBody ApplicationFormRequest applicationFormRequest
//                                                        @RequestPart("driverLicense") MultipartFile driverLicense,
                                                       /* @RequestPart("OPTReceiptURL") MultipartFile OPTReceiptURL */){
        onboardingApplicationCompositeService.submitApplicationForm(employeeId, applicationFormRequest);
        return new ResponseEntity<>("Application form submitted successfully", HttpStatus.OK);
    }
}
