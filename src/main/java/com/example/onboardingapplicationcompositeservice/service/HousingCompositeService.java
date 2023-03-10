package com.example.onboardingapplicationcompositeservice.service;

import com.example.onboardingapplicationcompositeservice.domain.entity.EmployeeService.Employee;
import com.example.onboardingapplicationcompositeservice.domain.entity.HouseService.FacilityReport;
import com.example.onboardingapplicationcompositeservice.domain.entity.HouseService.House;
import com.example.onboardingapplicationcompositeservice.domain.request.FacilityReportRequest;
import com.example.onboardingapplicationcompositeservice.domain.response.EmployeeSummary;
import com.example.onboardingapplicationcompositeservice.service.remote.RemoteEmployeeService;
import com.example.onboardingapplicationcompositeservice.service.remote.RemoteHousingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class HousingCompositeService {
    private RemoteEmployeeService employeeService;
    private RemoteHousingService housingService;
    @Autowired
    public void setEmployeeService(RemoteEmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    @Autowired
    public void setHousingService(RemoteHousingService housingService) {
        this.housingService = housingService;
    }

    public Employee findEmployeeById(String token, Integer employeeId){
        return employeeService.findEmployeeById(token, employeeId);
    }

    public House findHouseById(String token, Integer houseId) { return housingService.findHouseById(token, houseId).getHouse(); }

    public List<EmployeeSummary> findEmployeeSummariesByHouseId(String token, Integer houseId){
        ResponseEntity<Object> responseEntity = employeeService.findEmployeeSummariesByHouseId(token, houseId);
        LinkedHashMap<String,List<LinkedHashMap<String,String>>> linkedHashMap = (LinkedHashMap<String,List<LinkedHashMap<String,String>>>) responseEntity.getBody();
        List<LinkedHashMap<String,String>> employeeSummariesLinkedHashMapList = linkedHashMap.get("data");

        List<EmployeeSummary>employeeSummaries = new ArrayList<>();


        for(LinkedHashMap<String,String> linkedHashMapEmployeeSummary : employeeSummariesLinkedHashMapList){
            employeeSummaries.add(
                    new EmployeeSummary(
                            linkedHashMapEmployeeSummary.get("name"),
                            linkedHashMapEmployeeSummary.get("ssn"),
                            linkedHashMapEmployeeSummary.get("workAuthorizationTitle"),
                            linkedHashMapEmployeeSummary.get("phoneNumber"),
                            linkedHashMapEmployeeSummary.get("email")

                    ));
        }

        return employeeSummaries;
    }

    public FacilityReport addFacilityReport(String token, FacilityReportRequest facilityReportRequest){
        return housingService.addFacilityReport(token, facilityReportRequest);
    }

    public Employee findEmployeeByIdHr(String token, Integer employeeId){
        ResponseEntity<Object> responseEntity = employeeService.findEmployeeByIdHr(token, employeeId);
        LinkedHashMap<String,Object> linkedHashMap = (LinkedHashMap<String,Object>) responseEntity.getBody();
        LinkedHashMap<String,Object> employeeLinkedHashMap = (LinkedHashMap<String,Object>)linkedHashMap.get("data");
        Employee employee = new Employee((Integer)employeeLinkedHashMap.get("id"),
                (Integer)employeeLinkedHashMap.get("userId"),
                (String)employeeLinkedHashMap.get("preferredName")
        );
        return employee;
    }
}
