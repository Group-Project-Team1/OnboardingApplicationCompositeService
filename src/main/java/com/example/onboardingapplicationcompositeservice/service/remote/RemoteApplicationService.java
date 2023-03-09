package com.example.onboardingapplicationcompositeservice.service.remote;

import com.example.onboardingapplicationcompositeservice.domain.entity.ApplicationService.ApplicationWorkFlow;
import com.example.onboardingapplicationcompositeservice.domain.entity.ApplicationService.DigitalDocument;
import com.example.onboardingapplicationcompositeservice.domain.response.VisaStatusManagementResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient("application-service")
public interface RemoteApplicationService {

    @PostMapping("application-service/employee/{employeeId}/applicationForm")
    void submitApplicationForm(@PathVariable Integer employeeId,
                               @RequestParam("OPTReceiptURL") String OPTReceiptURL);

    @PostMapping(value = "application-service/employee/digitalDocuments", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String uploadFile(@RequestPart("file") MultipartFile multipartFile);

    @GetMapping("application-service/all/presignedURL/{fileName}")
    String getFileUrl(@PathVariable String fileName);

    @GetMapping("application-service/all/digitalDocuments")
    List<DigitalDocument> getDocuments();

    @PostMapping("application-service/employee/{employeeId}/application")
    void submitApplication(@PathVariable Integer employeeId);

    @GetMapping("application-service/all/{employeeId}/application")
    ApplicationWorkFlow getApplicationByEmployeeId(@PathVariable Integer employeeId);

    @GetMapping("application-service/hr/{status}/applications")
    List<ApplicationWorkFlow> getApplicationsByStatus(@PathVariable String status);

    @PostMapping("application-service/employee/{employeeId}/visaStatusManagement")
    void submitVisaDocuments(@PathVariable Integer employeeId, @RequestParam("fileId") Integer fileId);

    @GetMapping("application-service/employee/{employeeId}/visaStatusManagement")
    VisaStatusManagementResponse getVisaStatus(@PathVariable Integer employeeId);

}
