package com.example.onboardingapplicationcompositeservice.service.remote;

import com.example.onboardingapplicationcompositeservice.domain.request.ApplicationFormRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient("application-service")
public interface RemoteApplicationService {

    @PostMapping("/application-service/{employeeId}/applicationForm")
    ResponseEntity<Object> submitApplicationForm(@PathVariable Integer employeeId,
                                                 @RequestParam("OPTReceiptURL") String OPTReceiptURL);

    @PostMapping("application-service/digitalDocuments")
    ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile multipartFile);

    @PostMapping("application-service/presignedURL/{fileName}")
    ResponseEntity<Object> getFileUrl(@PathVariable String fileName);
}
