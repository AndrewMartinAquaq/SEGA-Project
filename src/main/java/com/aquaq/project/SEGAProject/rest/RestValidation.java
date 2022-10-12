package com.aquaq.project.SEGAProject.rest;

import com.aquaq.project.SEGAProject.rest.exceptions.InvalidInputException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class RestValidation {

    public RestValidation(){

    }

    public ResponseEntity<String> createResponse(String body, HttpStatus status) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json");
        return ResponseEntity.status(status.value()).headers(responseHeaders).body(body);
    }

    public void validateGradDate(String gradDate){
        Pattern pattern = Pattern.compile("\\b^[0-9]{4}+$\\b");
        Matcher matcher = pattern.matcher(gradDate);
        boolean matchFound = matcher.find();
        if(!matchFound){
            throw new InvalidInputException("Graduation date format invalid, must follow the following pattern: YEAR (eg. 2022)");
        }
    }

    public void validateSemester(String semester){
        Pattern pattern = Pattern.compile("\\b^(?:SUMMER|WINTER|AUTUMN|SPRING)[0-9]{4}+$\\b");
        Matcher matcher = pattern.matcher(semester);
        boolean matchFound = matcher.find();
        if(!matchFound){
            throw new InvalidInputException("Semester format invalid, must follow the following pattern: SEASONYEAR (eg. SUMMER2022)");
        }
    }
}
