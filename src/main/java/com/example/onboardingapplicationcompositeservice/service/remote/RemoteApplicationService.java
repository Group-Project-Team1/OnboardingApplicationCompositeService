package com.example.onboardingapplicationcompositeservice.service.remote;

import com.example.onboardingapplicationcompositeservice.domain.entity.ApplicationService.ApplicationWorkFlow;
import com.example.onboardingapplicationcompositeservice.domain.entity.ApplicationService.DigitalDocument;
import com.example.onboardingapplicationcompositeservice.domain.request.ApplicationFormRequest;
import com.example.onboardingapplicationcompositeservice.domain.response.VisaStatusManagementResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.ws.rs.Path;
import java.util.List;

@FeignClient("application-service")
public interface RemoteApplicationService {

    @PostMapping("/application-service/{employeeId}/applicationForm")
    void submitApplicationForm(@PathVariable Integer employeeId,
                               @RequestParam("OPTReceiptURL") String OPTReceiptURL);

    @PostMapping(value = "application-service/digitalDocuments", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String uploadFile(@RequestPart("file") MultipartFile multipartFile);

    @GetMapping("application-service/presignedURL/{fileName}")
    String getFileUrl(@PathVariable String fileName);

    @GetMapping("application-service/digitalDocuments")
    List<DigitalDocument> getDocuments();

    @PostMapping("application-service/{employeeId}/application")
    void submitApplication(@PathVariable Integer employeeId);

    @GetMapping("application-service/{employeeId}/application")
    ApplicationWorkFlow getApplicationByEmployeeId(@PathVariable Integer employeeId);

    @GetMapping("application-service/{status}/applications")
    List<ApplicationWorkFlow> getApplicationsByStatus(@PathVariable String status);

    @PostMapping("application-service/{employeeId}/visaStatusManagement")
    void submitVisaDocuments(@PathVariable Integer employeeId, @RequestParam("fileId") Integer fileId);

    @GetMapping("application-service/{employeeId}/visaStatusManagement")
    VisaStatusManagementResponse getVisaStatus(@PathVariable Integer employeeId);

}
