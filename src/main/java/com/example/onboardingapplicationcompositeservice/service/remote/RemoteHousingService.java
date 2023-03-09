package com.example.onboardingapplicationcompositeservice.service.remote;

import com.example.onboardingapplicationcompositeservice.domain.entity.HouseService.FacilityReport;
import com.example.onboardingapplicationcompositeservice.domain.request.FacilityReportRequest;
import com.example.onboardingapplicationcompositeservice.domain.response.AllHouseResponse;
import com.example.onboardingapplicationcompositeservice.domain.response.HouseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@FeignClient("housing-service")
public interface RemoteHousingService {
    @GetMapping("housing-service/house/all")
    AllHouseResponse findAllHouses(@RequestHeader(value = "Authorization", required = true) String authorizationHeader);

    @GetMapping("housing-service/house/{id}")
    HouseResponse findHouseById(@RequestHeader(value = "Authorization", required = true) String authorizationHeader, @PathVariable Integer id);

    @PostMapping("housing-service/facility-report")
    public FacilityReport addFacilityReport(@RequestHeader(value = "Authorization", required = true) String authorizationHeader, @RequestBody FacilityReportRequest facilityReportRequest);
}
