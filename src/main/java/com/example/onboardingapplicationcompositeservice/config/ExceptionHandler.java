package com.example.onboardingapplicationcompositeservice.config;


import com.example.onboardingapplicationcompositeservice.domain.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler(value = {Exception.class})
    public ResponseEntity<ErrorResponse> handleException(Exception e){
        return new ResponseEntity<>(
                ErrorResponse.builder()
                        .message(e.getMessage())
                        .build(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }

}