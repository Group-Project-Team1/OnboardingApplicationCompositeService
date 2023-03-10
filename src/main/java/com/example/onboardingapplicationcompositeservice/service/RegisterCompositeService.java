package com.example.onboardingapplicationcompositeservice.service;

import com.example.onboardingapplicationcompositeservice.domain.entity.EmployeeService.Employee;
import com.example.onboardingapplicationcompositeservice.domain.entity.HouseService.House;
import com.example.onboardingapplicationcompositeservice.domain.response.AllHouseResponse;
import com.example.onboardingapplicationcompositeservice.domain.response.EmployeeSummary;
import com.example.onboardingapplicationcompositeservice.domain.response.HouseAssignInfo;
import com.example.onboardingapplicationcompositeservice.service.remote.RemoteApplicationService;
import com.example.onboardingapplicationcompositeservice.service.remote.RemoteEmployeeService;
import com.example.onboardingapplicationcompositeservice.service.remote.RemoteHousingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

@Service
public class RegisterCompositeService {
    private RemoteApplicationService applicationService;
    private RemoteEmployeeService employeeService;
    private RemoteHousingService housingService;

    private HousingCompositeService housingCompositeService;

    @Autowired
    public void setHousingCompositeService(HousingCompositeService housingCompositeService) {
        this.housingCompositeService = housingCompositeService;
    }
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

    public void createNewEmployee(String token, Employee employee) {
        employeeService.updateEmployee(token, employee);
    }

    public void createNewApplication(String token, Integer employeeId) {
        applicationService.createNewApplication(token, employeeId);
    }

    public void assignNewHouse(String token, Integer employeeId) {
        // Find all houses
        AllHouseResponse allHouseResponse = housingService.findAllHousesEmployee(token);
        List<HouseAssignInfo> houses = allHouseResponse.getHouseAssignInfo();

        // Filter houses < maxOccupant
//        List<HouseAssignInfo> freeHouses = new ArrayList<>();
//        for (HouseAssignInfo house : houses) {
//            List<EmployeeSummary> employeeSummaries = housingCompositeService.findEmployeeSummariesByHouseId(
//                    token,
//                    house.getHouseId()
//            );
//            if (employeeSummaries.size() < house.getMaxOccupant()) {
//                freeHouses.add(house);
//            }
//        }
        // Random a free house
        Random random = new Random();
        Integer randomIdx = random.nextInt(houses.size());
        Integer houseId = houses.get(randomIdx).getHouseId();

        // assign that house to the employee and save the employee
        Employee employee = employeeService.findEmployeeById(token, employeeId);
        employee.setHouseId(houseId);
        employeeService.updateEmployee(token, employee);
    }
}
