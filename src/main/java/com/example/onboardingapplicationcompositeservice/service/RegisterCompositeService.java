package com.example.onboardingapplicationcompositeservice.service;

import com.example.onboardingapplicationcompositeservice.domain.entity.EmployeeService.Employee;
import com.example.onboardingapplicationcompositeservice.domain.entity.HouseService.House;
import com.example.onboardingapplicationcompositeservice.domain.response.EmployeeSummary;
import com.example.onboardingapplicationcompositeservice.service.remote.RemoteApplicationService;
import com.example.onboardingapplicationcompositeservice.service.remote.RemoteEmployeeService;
import com.example.onboardingapplicationcompositeservice.service.remote.RemoteHousingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class RegisterCompositeService {
    private RemoteApplicationService applicationService;
    private RemoteEmployeeService employeeService;
    private RemoteHousingService housingService;
    @Autowired
    public void setApplicationService(RemoteApplicationService applicationService) {
        this.applicationService = applicationService;
    }
    @Autowired
    public void setEmployeeService(RemoteEmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    @Autowired
    public void setHousingService(RemoteHousingService housingService) {
        this.housingService = housingService;
    }

    public void createNewEmployee(Employee employee) {
        employeeService.updateEmployee(employee);
    }

    public void createNewApplication(Integer employeeId) {
        applicationService.createNewApplication(employeeId);
    }

    public void assignNewHouse() {

    }
}
