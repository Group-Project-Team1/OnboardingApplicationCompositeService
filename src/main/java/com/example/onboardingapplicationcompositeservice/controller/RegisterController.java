package com.example.onboardingapplicationcompositeservice.controller;

import com.example.onboardingapplicationcompositeservice.domain.entity.EmployeeService.Employee;
import com.example.onboardingapplicationcompositeservice.domain.entity.HouseService.House;
import com.example.onboardingapplicationcompositeservice.domain.response.EmployeeSummary;
import com.example.onboardingapplicationcompositeservice.domain.response.HouseDetailResponse;
import com.example.onboardingapplicationcompositeservice.domain.response.HouseInfoResponse;
import com.example.onboardingapplicationcompositeservice.domain.response.Roommate;
import com.example.onboardingapplicationcompositeservice.security.AuthUserDetail;
import com.example.onboardingapplicationcompositeservice.security.JwtProvider;
import com.example.onboardingapplicationcompositeservice.service.HousingCompositeService;
import com.example.onboardingapplicationcompositeservice.service.RegisterCompositeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("auth")
public class RegisterController {

    JwtProvider jwtProvider;

    RegisterCompositeService registerCompositeService;
    @Autowired
    public RegisterController(RegisterCompositeService registerCompositeService, JwtProvider jwtProvider) {
        this.registerCompositeService = registerCompositeService;
        this.jwtProvider = jwtProvider;
    }

    @PostMapping("user-register/{userId}/{email}")
//    @PreAuthorize("permitAll()")
    public ResponseEntity<Object> createProfileApplicationAndHouse(@PathVariable("userId") String userId, @PathVariable("email") String email){
        // Create an employee profile
        Integer id = Integer.parseInt(userId);
        System.out.println("ZERO");
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
//        Integer employeeId = registerCompositeService.generateEmployeeId();
//        System.out.println(employeeId);
        Integer employeeId = id; // set the employeeId = userId
        Employee employee = Employee.builder()
                .id(employeeId)
                .userId(id)
                .email(email)
                .firstName("firstName")
                .lastName("lastName")
                .preferredName("preferredName")
                .cellPhone("XXX-XXX-XXXX")
                .alternatePhone("XXX-XXX-XXXX")
                .gender("male")
                .ssn("XXX-XX-XXXX")
                .driverLicense("XXXXXXXXX")
                .contacts(new ArrayList<>())
                .addresses(new ArrayList<>())
                .visaStatuses(new ArrayList<>())
                .personalDocuments(new ArrayList<>())
                .build();
        System.out.println(employee.getUserId());
        System.out.println("ONE");
        registerCompositeService.createNewEmployee(
//                "Bearer "+jwtProvider.createToken((AuthUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()),
                employee
        );

        System.out.println("TWO");
        // Create an application
        registerCompositeService.createNewApplication(
//                "Bearer:"+jwtProvider.createToken((AuthUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()),
                employeeId
        );

        System.out.println("THREE");
        // Assign a random house
        registerCompositeService.assignNewHouse(
//        "Bearer:"+jwtProvider.createToken((AuthUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()),
               employee
        );

        return new ResponseEntity<>("User created successfully", HttpStatus.OK);
    }

}
