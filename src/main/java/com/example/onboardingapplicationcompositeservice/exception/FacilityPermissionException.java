package com.example.onboardingapplicationcompositeservice.exception;

public class FacilityPermissionException extends Exception{
    public FacilityPermissionException() {
        super("Given Facility id not in your house!");
    }
}
