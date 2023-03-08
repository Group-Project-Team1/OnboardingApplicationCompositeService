package com.example.onboardingapplicationcompositeservice.service.remote;

import com.example.onboardingapplicationcompositeservice.domain.request.ApplicationFormRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@FeignClient("application-service")
public interface RemoteApplicationService {

    @PostMapping("/application-service/{employeeId}/applicationForm")
    void submitApplicationForm(@PathVariable Integer employeeId,
                               @RequestParam("OPTReceiptURL") String OPTReceiptURL);

    @PostMapping(value = "application-service/digitalDocuments", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String uploadFile(@RequestPart("file") MultipartFile multipartFile);

    @GetMapping("application-service/presignedURL/{fileName}")
    String getFileUrl(@PathVariable String fileName);
}
