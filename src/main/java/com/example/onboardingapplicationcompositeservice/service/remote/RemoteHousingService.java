package com.example.onboardingapplicationcompositeservice.service.remote;

import com.example.onboardingapplicationcompositeservice.domain.response.AllHouseResponse;
import com.example.onboardingapplicationcompositeservice.domain.response.HouseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("housing-service")
public interface RemoteHousingService {
    @GetMapping("housing-service/house/all")
    AllHouseResponse findAllHouses();

    @GetMapping("housing-service/house/{id}")
    HouseResponse findHouseById(@PathVariable Integer id);
}
