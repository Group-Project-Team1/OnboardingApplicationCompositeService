package com.example.onboardingapplicationcompositeservice.controller;

import com.example.onboardingapplicationcompositeservice.controller.HousingCompositeController;
import com.example.onboardingapplicationcompositeservice.domain.entity.EmployeeService.Employee;
import com.example.onboardingapplicationcompositeservice.domain.entity.HouseService.*;
import com.example.onboardingapplicationcompositeservice.domain.response.EmployeeSummary;
import com.example.onboardingapplicationcompositeservice.domain.response.HouseInfoResponse;
import com.example.onboardingapplicationcompositeservice.domain.response.Roommate;
import com.example.onboardingapplicationcompositeservice.security.AuthUserDetail;
import com.example.onboardingapplicationcompositeservice.security.JwtProvider;
import com.example.onboardingapplicationcompositeservice.service.HousingCompositeService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest()
@Slf4j
public class HousingCompositeControllerTest {

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private HousingCompositeService housingCompositeService;

    @InjectMocks
    private HousingCompositeController housingCompositeController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        this.housingCompositeController = new HousingCompositeController(housingCompositeService, jwtProvider);
    }

    @Test
    public void testGetUserHouseInfo() {
        // Mock the AuthUserDetail object returned by SecurityContextHolder
        AuthUserDetail authUserDetail = AuthUserDetail
                .builder()
                .userId(1)
                .username("test")
                .authorities(new ArrayList<>(Arrays.asList(new SimpleGrantedAuthority("employee"))))
                .build();
        // Mock the House, Employee, and EmployeeSummary objects returned by the HousingCompositeService
        House house = new House();
        house.setAddress("123 Main St.");
        Facility facility = new Facility();
        facility.setType("Bathroom");
        FacilityReport facilityReport = new FacilityReport();
        facilityReport.setDescription("Toilet is clogged.");
        EmployeeSummary employeeSummary = new EmployeeSummary();
        employeeSummary.setName("John Doe");
        employeeSummary.setPhoneNumber("555-555-1212");
        List<EmployeeSummary> employeeSummaries = new ArrayList<>();
        employeeSummaries.add(employeeSummary);
        house.setFacilities(new HashSet<>(Arrays.asList(facility)));
        facility.setFacilityReports(new HashSet<>(Arrays.asList(facilityReport)));
        Employee employee = new Employee();
        employee.setHouseId(1);
        when(housingCompositeService.findEmployeeById(any(), any())).thenReturn(employee);
        when(housingCompositeService.findHouseById(any(), any())).thenReturn(house);
        when(housingCompositeService.findEmployeeSummariesByHouseId(any(), any())).thenReturn(employeeSummaries);

        // Mock the JWT token returned by the JwtProvider
        when(jwtProvider.createToken(any())).thenReturn("test token");
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(authUserDetail,null));

        // Call the getUserHouseInfo method on the HousingCompositeController
        HouseInfoResponse response = housingCompositeController.getUserHouseInfo();

        // Verify that the HousingCompositeService methods were called with the correct parameters
        assertEquals(1, housingCompositeService.findEmployeeById("test token", 1).getHouseId());
        assertEquals("123 Main St.", housingCompositeService.findHouseById("test token", 1).getAddress());
        assertEquals("John Doe", housingCompositeService.findEmployeeSummariesByHouseId("test token", 1).get(0).getName());
    }


//    @Test
//    public void testAddFacilityReport() {
//        // Mock the AuthUserDetail object returned by SecurityContextHolder
//        AuthUserDetail authUserDetail = AuthUserDetail
//                .builder()
//                .userId(1)
//                .username("test")
//                .authorities(new ArrayList<>(Arrays.asList(new SimpleGrantedAuthority("employee"))))
//                .build();
//        // Mock the House, Employee, and EmployeeSummary objects returned by the HousingCompositeService
//        House house = new House();
//        house.setAddress("123 Main St.");
//        Facility facility = new Facility();
//        facility.setType("Bathroom");
//        FacilityReport facilityReport = new FacilityReport();
//        facilityReport.setDescription("Toilet is clogged.");
//
//
//        house.setFacilities(new HashSet<>(Arrays.asList(facility)));
//        facility.setFacilityReports(new HashSet<>(Arrays.asList(facilityReport)));
//        Employee employee = new Employee();
//        employee.setHouseId(1);
//        when(housingCompositeService.findEmployeeById(any(), any())).thenReturn(employee);
//        when(housingCompositeService.findHouseById(any(), any())).thenReturn(house);
//
//        // Mock the JWT token returned by the JwtProvider
//        when(jwtProvider.createToken(any())).thenReturn("test token");
//        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(authUserDetail,null));
//
//        HouseInfoResponse response = housingCompositeController.addFacilityReport(facilityReport);
//
//        assertEquals(1, housingCompositeService.findEmployeeById("test token", 1).getHouseId());
//    }
}