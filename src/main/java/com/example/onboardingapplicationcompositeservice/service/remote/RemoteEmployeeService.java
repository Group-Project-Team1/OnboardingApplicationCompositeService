package com.example.onboardingapplicationcompositeservice.service.remote;

import com.example.onboardingapplicationcompositeservice.domain.entity.EmployeeService.Employee;
import com.example.onboardingapplicationcompositeservice.domain.entity.EmployeeService.PersonalDocument;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@FeignClient("employee-service")
public interface RemoteEmployeeService {

    @PostMapping("employee-service/employee/updateEmployee")
    ResponseEntity<Object> updateEmployee(@RequestBody Employee employee);

    @GetMapping("employee-service/employee/{employeeId}")
    Employee findEmployeeById(@PathVariable Integer employeeId);

    @PostMapping("employee-service/employee/{id}/addPersonalDocument")
    ResponseEntity<Object> addPersonalDocument(@PathVariable("id") Integer id, @RequestBody PersonalDocument personalDocument);

}
