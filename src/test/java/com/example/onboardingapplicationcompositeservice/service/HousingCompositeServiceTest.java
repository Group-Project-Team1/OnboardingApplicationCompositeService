package com.example.onboardingapplicationcompositeservice.service;

import com.example.onboardingapplicationcompositeservice.domain.response.EmployeeSummary;
import com.example.onboardingapplicationcompositeservice.service.remote.RemoteEmployeeService;
import com.example.onboardingapplicationcompositeservice.service.remote.RemoteHousingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HousingCompositeServiceTest {
    @InjectMocks
    HousingCompositeService housingCompositeService;

    @Mock
    RemoteEmployeeService employeeService;

    @Mock
    RemoteHousingService housingService;

    @Test
    void test_findEmployeeSummariesByHouseId(){
        String token="token";
        int houseId=1;

        LinkedHashMap<String, List<LinkedHashMap<String,String>>> linkedHashMap = new LinkedHashMap<String,List<LinkedHashMap<String,String>>>();
        linkedHashMap.put("data", new ArrayList<LinkedHashMap<String,String>>());

        ResponseEntity<Object> responseEntity = new ResponseEntity<Object>(linkedHashMap, HttpStatus.OK);

        List<EmployeeSummary>employeeSummaries = new ArrayList<>();

        when(employeeService.findEmployeeSummariesByHouseId(token, houseId)).thenReturn(responseEntity);

        assertEquals(housingCompositeService.findEmployeeSummariesByHouseId(token, houseId), employeeSummaries);
    }

}
