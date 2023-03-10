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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("user-register/{userId}/{email}/")
    public void createProfileApplicationAndHouse(@PathVariable("userId") String userId, @PathVariable("email") String email){
        System.out.println(userId);
        System.out.println("Email = " + email);
        // Create an employee profile
        Integer id = Integer.parseInt(userId);
        Employee employee = Employee.builder()
                .id(id)
                .userId(id)
                .email(email)
                .build();
        registerCompositeService.createNewEmployee(employee);

        // Create an application
        registerCompositeService.createNewApplication(id);

        // Assign a random house
        registerCompositeService.assignNewHouse();
    }

}
