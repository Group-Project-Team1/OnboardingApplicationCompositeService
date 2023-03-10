package com.example.onboardingapplicationcompositeservice.controller;

import com.example.onboardingapplicationcompositeservice.domain.request.ApplicationFormRequest;
import com.example.onboardingapplicationcompositeservice.domain.response.AllApplicationResponse;
import com.example.onboardingapplicationcompositeservice.security.JwtProvider;
import com.example.onboardingapplicationcompositeservice.service.OnboardingApplicationCompositeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class OnboardingApplicationCompositeControllerTest {

    @Mock
    private OnboardingApplicationCompositeService onboardingApplicationCompositeService;

    @Mock
    private JwtProvider jwtProvider;

    @InjectMocks
    private OnboardingApplicationCompositeController onboardingApplicationCompositeController;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(onboardingApplicationCompositeController).build();
    }

    @Test
    public void testSubmitApplicationForm() throws Exception {
        // Given
        Integer employeeId = 1;
        ApplicationFormRequest applicationFormRequest = new ApplicationFormRequest();
        MultipartFile driverLicense = new MockMultipartFile("driverLicense", "test.txt", "text/plain", "test file".getBytes());
        MultipartFile OPTReceipt = new MockMultipartFile("OPTReceipt", "test.txt", "text/plain", "test file".getBytes());

        // When
        doNothing().when(onboardingApplicationCompositeService).submitApplicationForm(anyString(), eq(employeeId), eq(applicationFormRequest), eq(driverLicense), eq(OPTReceipt));

        // Then
        mockMvc.perform(MockMvcRequestBuilders.multipart("/employee/" + employeeId + "/applicationForm")
                        .file("applicationFormRequest", new ObjectMapper().writeValueAsString(applicationFormRequest).getBytes())
                        .file("driverLicense", driverLicense.getBytes())
                        .file("OPTReceipt", OPTReceipt.getBytes()))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) content().string("Application form submitted successfully"));
    }

    @Test
    public void testSubmitApplication() throws Exception {
        // Given
        Integer employeeId = 1;
        MultipartFile file1 = new MockMultipartFile("file1", "test.txt", "text/plain", "test file".getBytes());
        MultipartFile file2 = new MockMultipartFile("file2", "test.txt", "text/plain", "test file".getBytes());
        List<MultipartFile> files = Arrays.asList(file1, file2);

        // When
        doNothing().when(onboardingApplicationCompositeService).submitApplication(anyString(), eq(employeeId), eq(files));

        // Then
        mockMvc.perform(MockMvcRequestBuilders.multipart("/employee-service/employee/" + employeeId + "/application")
                        .file("files", file1.getBytes())
                        .file("files", file2.getBytes()))
                .andExpect(status().isOk())
                .andExpect((ResultMatcher) content().string("Pleas wait for HR to review your application."));
    }



}

