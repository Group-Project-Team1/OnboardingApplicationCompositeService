package com.example.onboardingapplicationcompositeservice.service;

import com.example.onboardingapplicationcompositeservice.domain.entity.ApplicationService.DigitalDocument;
import com.example.onboardingapplicationcompositeservice.domain.entity.EmployeeService.Employee;
import com.example.onboardingapplicationcompositeservice.domain.entity.EmployeeService.PersonalDocument;
import com.example.onboardingapplicationcompositeservice.domain.request.ApplicationFormRequest;
import com.example.onboardingapplicationcompositeservice.domain.response.VisaStatusManagementResponse;
import com.example.onboardingapplicationcompositeservice.security.AuthUserDetail;
import com.example.onboardingapplicationcompositeservice.security.JwtProvider;
import com.example.onboardingapplicationcompositeservice.service.remote.RemoteApplicationService;
import com.example.onboardingapplicationcompositeservice.service.remote.RemoteEmployeeService;
import com.google.inject.spi.Message;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OnboardingApplicationCompositeServiceTest {
    @InjectMocks
    private OnboardingApplicationCompositeService service;

    @Mock
    private RemoteEmployeeService employeeService;
    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private RemoteApplicationService applicationService;

    @Mock
    private RabbitTemplate rabbitTemplate;

//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.initMocks(this);
//        service = new OnboardingApplicationCompositeService();
//        service.setRemoteEmployeeService(employeeService);
//        service.setRemoteApplicationService(applicationService);
//        service.rabbitTemplate = rabbitTemplate;
//    }

    @Test
    public void testSubmitApplicationForm() throws Exception {
        String token = "testToken";
        Integer employeeId = 1;
        ApplicationFormRequest applicationFormRequest = new ApplicationFormRequest();
        MultipartFile driverLicense = mock(MultipartFile.class);
        MultipartFile OPTReceipt = mock(MultipartFile.class);

        Employee employee = new Employee();
        employee.setId(employeeId);
        when(employeeService.findEmployeeById(eq(token), eq(employeeId))).thenReturn(employee);

        PersonalDocument driverLicenseDoc = new PersonalDocument();
        driverLicenseDoc.setTitle("Driver License");
        driverLicenseDoc.setPath("https://example.com/driver_license.jpg");
        driverLicenseDoc.setCreateDate(Timestamp.valueOf(LocalDateTime.now()));
//        when(employeeService.addPersonalDocument(eq(token), eq(employeeId), any())).thenReturn(driverLicenseDoc);

        PersonalDocument OPTReceiptDoc = new PersonalDocument();
        OPTReceiptDoc.setTitle("OPT Receipt");
        OPTReceiptDoc.setPath("https://example.com/opt_receipt.jpg");
        OPTReceiptDoc.setCreateDate(Timestamp.valueOf(LocalDateTime.now()));
//        when(employeeService.addPersonalDocument(eq(token), eq(employeeId), any())).thenReturn(OPTReceiptDoc);

        String driverLicenseFileName = "driver_license.jpg";
        when(applicationService.uploadFile(eq(token), eq(driverLicense))).thenReturn(driverLicenseFileName);
        when(applicationService.getFileUrl(eq(token), eq(driverLicenseFileName))).thenReturn(driverLicenseDoc.getPath());

        String OPTFileName = "opt_receipt.jpg";
        when(applicationService.uploadFile(eq(token), eq(OPTReceipt))).thenReturn(OPTFileName);
        when(applicationService.getFileUrl(eq(token), eq(OPTFileName))).thenReturn(OPTReceiptDoc.getPath());

        service.submitApplicationForm(token, employeeId, applicationFormRequest, driverLicense, OPTReceipt);

        verify(employeeService).updateEmployee(eq(token), eq(employee));
        verify(employeeService).addPersonalDocument(eq(token), eq(employeeId), any());
        verify(applicationService).submitApplicationForm(eq(token), eq(employeeId), eq(OPTReceiptDoc.getPath()));
    }

    // Test for submitApplication method
    @Test
    public void testSubmitApplication() {
        String token = "myToken";
        Integer employeeId = 123;
        List<MultipartFile> files = new ArrayList<>();
        files.add(new MockMultipartFile("file1", "hello.txt", "text/plain", "Hello, World!".getBytes()));
        files.add(new MockMultipartFile("file2", "world.txt", "text/plain", "World, Hello!".getBytes()));

        List<DigitalDocument> digitalDocumentList = new ArrayList<>();
        DigitalDocument digitalDocument1 = new DigitalDocument();
        digitalDocument1.setTitle("file1");
        DigitalDocument digitalDocument2 = new DigitalDocument();
        digitalDocument2.setTitle("file2");
        digitalDocumentList.add(digitalDocument1);
        digitalDocumentList.add(digitalDocument2);

        // Mock behavior of applicationService.getDocuments()
        when(applicationService.getDocuments(token)).thenReturn(digitalDocumentList);

        // Mock behavior of applicationService.uploadFile()
        when(applicationService.uploadFile(anyString(), any(MultipartFile.class)))
                .thenReturn("file1")
                .thenReturn("file2");

        // Call the method being tested
//        OnboardingApplicationCompositeService.submitApplication(token, employeeId, files);

        // Verify that the expected methods were called on employeeService
        verify(employeeService, times(1)).addPersonalDocument(eq(token), eq(employeeId), any(PersonalDocument.class));
        verify(employeeService, times(2)).addPersonalDocument(eq(token), eq(employeeId), any(PersonalDocument.class));

        // Verify that the expected methods were called on applicationService
        verify(applicationService, times(1)).submitApplication(eq(token), eq(employeeId));

        // Verify that the expected methods were called on rabbitTemplate
        verify(rabbitTemplate, never()).convertAndSend(anyString(), any(Object.class));
    }


    @Test
    public void testSubmitVisaDocuments() throws Exception {
        // mock dependencies
        when(jwtProvider.createToken(any(AuthUserDetail.class))).thenReturn("token");
        when(applicationService.uploadFile(eq("token"), any(MultipartFile.class))).thenReturn("file.txt");
        when(applicationService.getFileUrl(eq("token"), eq("file.txt"))).thenReturn("http://example.com/file.txt");
        doNothing().when(employeeService).addPersonalDocument(eq("token"), eq(1), any(PersonalDocument.class));
        doNothing().when(applicationService).submitVisaDocuments(eq("token"), eq(1), eq(2), eq("http://example.com/file.txt"));

        // call function under test
        service.submitVisaDocuments("token", 1, mock(MultipartFile.class), 2, "Visa");

        // verify interactions
        verify(jwtProvider).createToken(any(AuthUserDetail.class));
        verify(applicationService).uploadFile(eq("token"), any(MultipartFile.class));
        verify(applicationService).getFileUrl(eq("token"), eq("file.txt"));
        verify(employeeService).addPersonalDocument(eq("token"), eq(1), any(PersonalDocument.class));
        verify(applicationService).submitVisaDocuments(eq("token"), eq(1), eq(2), eq("http://example.com/file.txt"));
    }

    @Test
    public void testGetVisaStatus() {
        // mock dependencies
        when(jwtProvider.createToken(any(AuthUserDetail.class))).thenReturn("token");
        when(applicationService.getVisaStatus(eq("token"), eq(1))).thenReturn(new VisaStatusManagementResponse());

        // call function under test
        service.getVisaStatus("token", 1);

        // verify interactions
        verify(jwtProvider).createToken(any(AuthUserDetail.class));
        verify(applicationService).getVisaStatus(eq("token"), eq(1));
    }

    @Test
    public void testReviewApplication_approve() {
        // mock dependencies
        when(jwtProvider.createToken(any(AuthUserDetail.class))).thenReturn("token");
        doNothing().when(applicationService).reviewApplication(eq("token"), eq(1), eq("approve"), eq(null));
        Employee employee = new Employee();
        employee.setEmail("test@example.com");
        when(service.findEmployeeById(eq("token"), eq(1))).thenReturn(employee);
        doNothing().when(rabbitTemplate).convertAndSend(eq("email.direct"), eq("emailKey"), any(Message.class));

        // call function under test
        ResponseEntity<Object> response = service.reviewApplication("token", 1, "approve", null);

        // verify interactions
        verify(jwtProvider).createToken(any(AuthUserDetail.class));
        verify(applicationService).reviewApplication(eq("token"), eq(1), eq("approve"), eq(null));
        verify(service).findEmployeeById(eq("token"), eq(1));
        verify(rabbitTemplate).convertAndSend(eq("email.direct"), eq("emailKey"), any(Message.class));

        // verify response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("This application has been successfully approved.", response.getBody());
    }

}
