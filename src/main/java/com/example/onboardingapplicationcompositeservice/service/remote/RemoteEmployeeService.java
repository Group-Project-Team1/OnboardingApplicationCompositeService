package com.example.onboardingapplicationcompositeservice.service.remote;

import com.example.onboardingapplicationcompositeservice.domain.entity.EmployeeService.Employee;
import com.example.onboardingapplicationcompositeservice.domain.entity.EmployeeService.PersonalDocument;
import com.example.onboardingapplicationcompositeservice.domain.response.AllHouseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@FeignClient("employee-service")
public interface RemoteEmployeeService {

    @PostMapping("employee-service/employee/updateEmployee")
    ResponseEntity<Object> updateEmployee(@RequestHeader(value = "Authorization", required = true) String authorizationHeader,
                                          @RequestBody Employee employee);

    @GetMapping("employee-service/employee/{employeeId}")
    Employee findEmployeeById(@RequestHeader(value = "Authorization", required = true) String authorizationHeader,
                              @PathVariable Integer employeeId);

    @PostMapping("employee-service/employee/{id}/addPersonalDocument")
    ResponseEntity<Object> addPersonalDocument(@RequestHeader(value = "Authorization", required = true) String authorizationHeader,
                                               @PathVariable("id") Integer id, @RequestBody PersonalDocument personalDocument);

    @GetMapping("employee-service/hr/housing")
    ResponseEntity<Object> findEmployeeSummariesByHouseId(@RequestHeader(value = "Authorization", required = true) String authorizationHeader, @RequestParam("houseId") Integer houseId);


}
