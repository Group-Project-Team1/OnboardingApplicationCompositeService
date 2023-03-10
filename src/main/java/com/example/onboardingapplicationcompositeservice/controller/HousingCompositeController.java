package com.example.onboardingapplicationcompositeservice.controller;

import com.example.onboardingapplicationcompositeservice.domain.entity.EmployeeService.Employee;
import com.example.onboardingapplicationcompositeservice.domain.entity.HouseService.Facility;
import com.example.onboardingapplicationcompositeservice.domain.entity.HouseService.FacilityReport;
import com.example.onboardingapplicationcompositeservice.domain.entity.HouseService.FacilityReportInfo;
import com.example.onboardingapplicationcompositeservice.domain.entity.HouseService.House;
import com.example.onboardingapplicationcompositeservice.domain.request.FacilityReportRequest;
import com.example.onboardingapplicationcompositeservice.domain.request.FacilityReportUserRequest;
import com.example.onboardingapplicationcompositeservice.domain.response.EmployeeSummary;
import com.example.onboardingapplicationcompositeservice.domain.response.HouseDetailResponse;
import com.example.onboardingapplicationcompositeservice.domain.response.HouseInfoResponse;
import com.example.onboardingapplicationcompositeservice.domain.response.Roommate;
import com.example.onboardingapplicationcompositeservice.exception.FacilityPermissionException;
import com.example.onboardingapplicationcompositeservice.security.AuthUserDetail;
import com.example.onboardingapplicationcompositeservice.security.JwtProvider;
import com.example.onboardingapplicationcompositeservice.service.HousingCompositeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

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


    @GetMapping("user-house-info")
    @PreAuthorize("hasAuthority('employee')")
    public HouseInfoResponse getUserHouseInfo(){
        AuthUserDetail authUserDetail = (AuthUserDetail)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer employeeId = authUserDetail.getUserId();
        List<GrantedAuthority> userAuthorities = (List<GrantedAuthority>) authUserDetail.getAuthorities();
        userAuthorities.add(new SimpleGrantedAuthority("hr"));
        String token = "Bearer:"+jwtProvider.createToken(authUserDetail);

        Employee employee = housingCompositeService.findEmployeeById(token, employeeId);
        House house = housingCompositeService.findHouseById( token, employee.getHouseId());
        List<EmployeeSummary> employeeSummaries = housingCompositeService.findEmployeeSummariesByHouseId(token,employee.getHouseId());
        return HouseInfoResponse.builder()
                .address(house.getAddress())
                .roommates(employeeSummaries.stream().map(a -> new Roommate(a.getName(), a.getPhoneNumber())).collect(Collectors.toList()))
                .build();

    }

    @PostMapping("facility-report")
    @PreAuthorize("hasAuthority('employee')")
    public FacilityReport addFacilityReport(@RequestBody FacilityReportUserRequest facilityReportUserRequest) throws FacilityPermissionException{
        AuthUserDetail authUserDetail = (AuthUserDetail)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer employeeId = authUserDetail.getUserId();
        List<GrantedAuthority> userAuthorities = (List<GrantedAuthority>) authUserDetail.getAuthorities();
        userAuthorities.add(new SimpleGrantedAuthority("hr"));
        String token = "Bearer:"+jwtProvider.createToken(authUserDetail);

        Employee employee = housingCompositeService.findEmployeeById(token, employeeId);
        House house = housingCompositeService.findHouseById( token, employee.getHouseId());
        List<Integer> facilityIds = house.getFacilities().stream().map(a->a.getId()).collect(Collectors.toList());

        if(!facilityIds.contains(facilityReportUserRequest.getFacilityId())){
            throw new FacilityPermissionException();
        }


        FacilityReport facilityReport = housingCompositeService.addFacilityReport(token,
                FacilityReportRequest.builder()
                        .facilityId(facilityReportUserRequest.getFacilityId())
                        .title(facilityReportUserRequest.getTitle())
                        .description(facilityReportUserRequest.getDescription())
                        .employeeId(employeeId)
                        .build()
        );

        return facilityReport;
    }

    @GetMapping("facility-report-list")
    @PreAuthorize("hasAuthority('employee')")
    public List<FacilityReportInfo> addFacilityReport() throws FacilityPermissionException{
        AuthUserDetail authUserDetail = (AuthUserDetail)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer employeeId = authUserDetail.getUserId();
        List<GrantedAuthority> userAuthorities = (List<GrantedAuthority>) authUserDetail.getAuthorities();
        userAuthorities.add(new SimpleGrantedAuthority("hr"));
        String token = "Bearer:"+jwtProvider.createToken(authUserDetail);

        Employee employee = housingCompositeService.findEmployeeById(token, employeeId);
        House house = housingCompositeService.findHouseById(token, employee.getHouseId());

        for(Facility facility: house.getFacilities()){
            for(FacilityReport facilityReport: facility.getFacilityReports()){
                facilityReport.setFacility(facility);
            }
        }


        return house.getFacilities()
                .stream()
                .flatMap(a -> a.getFacilityReports().stream())
                .map(a -> new FacilityReportInfo(a.getId(),
                        a.getFacility().getType(),
                        housingCompositeService.findEmployeeByIdHr(token, a.getEmployeeId()).getPreferredName(),
                        a.getDescription(),
                        a.getTitle(),
                        a.getStatus(),
                        a.getCreateDate(),
                        a.getFacilityReportDetails()))
                .collect(Collectors.toList());
    }

    @GetMapping("house-detail/{houseId}")
    @PreAuthorize("hasAuthority('hr')")
    public HouseDetailResponse getHouseDetail(@PathVariable Integer houseId){

        House house = housingCompositeService.findHouseById("Bearer:"+jwtProvider.createToken((AuthUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()), houseId);
        List<EmployeeSummary> employeeSummaries = housingCompositeService.findEmployeeSummariesByHouseId("Bearer:"+jwtProvider.createToken((AuthUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal()),houseId);

        return HouseDetailResponse.builder()
                .house(house)
                .employeeSummaries(employeeSummaries)
                .build();

    }

}
