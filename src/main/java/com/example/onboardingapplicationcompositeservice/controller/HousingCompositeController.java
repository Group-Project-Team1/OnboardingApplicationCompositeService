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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("composite-housing")
public class HousingCompositeController {

    JwtProvider jwtProvider;

    HousingCompositeService housingCompositeService;
    @Autowired
    public HousingCompositeController(HousingCompositeService housingCompositeService, JwtProvider jwtProvider) {
        this.housingCompositeService = housingCompositeService;
        this.jwtProvider = jwtProvider;
    }

    @GetMapping("user-house-info/{employeeId}")
    public HouseInfoResponse getUserHouseInfo(@PathVariable Integer employeeId){
        Employee employee = housingCompositeService.findEmployeeById(employeeId);
        House house = housingCompositeService.findHouseById( "Bearer:"+jwtProvider.createToken((AuthUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()), employee.getHouseId());
        List<EmployeeSummary> employeeSummaries = housingCompositeService.findEmployeeSummariesByHouseId(employee.getHouseId());

        return HouseInfoResponse.builder()
                .address(house.getAddress())
                .roommates(employeeSummaries.stream().map(a -> new Roommate(a.getName(), a.getPhoneNumber())).collect(Collectors.toList()))
                .build();

    }

    @GetMapping("house-detail/{houseId}")
    public HouseDetailResponse getHouseDetail(@PathVariable Integer houseId){

        House house = housingCompositeService.findHouseById("Bearer:"+jwtProvider.createToken((AuthUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()), houseId);
        List<EmployeeSummary> employeeSummaries = housingCompositeService.findEmployeeSummariesByHouseId(houseId);

        return HouseDetailResponse.builder()
                .house(house)
                .employeeSummaries(employeeSummaries)
                .build();

    }

}
