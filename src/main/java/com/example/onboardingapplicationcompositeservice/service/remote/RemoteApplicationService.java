package com.example.onboardingapplicationcompositeservice.service.remote;

import com.example.onboardingapplicationcompositeservice.domain.entity.ApplicationService.ApplicationWorkFlow;
import com.example.onboardingapplicationcompositeservice.domain.entity.ApplicationService.DigitalDocument;
import com.example.onboardingapplicationcompositeservice.domain.response.VisaStatusManagementResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FeignClient("application-service")
public interface RemoteApplicationService {

    @PostMapping("application-service/employee/{employeeId}/applicationForm")
    void submitApplicationForm(@RequestHeader(value = "Authorization", required = true) String authorizationHeader,
                               @PathVariable Integer employeeId,
                               @RequestParam("OPTReceiptURL") String OPTReceiptURL);

    @PostMapping(value = "application-service/employee/digitalDocuments", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String uploadFile(@RequestHeader(value = "Authorization", required = true) String authorizationHeader,
                      @RequestPart("file") MultipartFile multipartFile);

    @GetMapping("application-service/all/presignedURL/{fileName}")
    String getFileUrl(@RequestHeader(value = "Authorization", required = true) String authorizationHeader,
                      @PathVariable String fileName);

    @GetMapping("application-service/all/digitalDocuments")
    List<DigitalDocument> getDocuments(@RequestHeader(value = "Authorization", required = true) String authorizationHeader);

    @PostMapping("application-service/employee/{employeeId}/application")
    void submitApplication(@RequestHeader(value = "Authorization", required = true) String authorizationHeader,
                           @PathVariable Integer employeeId);

    @GetMapping("application-service/all/{employeeId}/application")

    ApplicationWorkFlow getApplicationByEmployeeId(@RequestHeader(value = "Authorization", required = true) String authorizationHeader,
                                                   @PathVariable Integer employeeId);

    @GetMapping("application-service/hr/{status}/applications")
    List<ApplicationWorkFlow> getApplicationsByStatus(@RequestHeader(value = "Authorization", required = true) String authorizationHeader,
                                                      @PathVariable String status);

    @PostMapping("application-service/employee/{employeeId}/visaStatusManagement")
    void submitVisaDocuments(@RequestHeader(value = "Authorization", required = true) String authorizationHeader,
                             @PathVariable Integer employeeId,
                             @RequestParam("fileId") Integer fileId,
                             @RequestParam("url") String url);

    @GetMapping("application-service/employee/{employeeId}/visaStatusManagement")
    VisaStatusManagementResponse getVisaStatus(@RequestHeader(value = "Authorization", required = true) String authorizationHeader,
                                               @PathVariable Integer employeeId);

    @PostMapping("application-service/hr/viewApplication/{employeeId}")
    void reviewApplication(@RequestHeader(value = "Authorization", required = true) String authorizationHeader,
                           @PathVariable Integer employeeId, @RequestParam String action, @RequestParam String feedback);

    @PostMapping("application-service/createApplication/{employeeId}")
    void createNewApplication(
//            @RequestHeader(value = "Authorization", required = true) String authorizationHeader,
            @PathVariable Integer employeeId);

    @PostMapping(value = "application-service/digitalDocuments", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String uploadFile(@RequestPart("file") MultipartFile multipartFile);

}
