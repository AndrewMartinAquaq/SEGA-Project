package com.aquaq.project.SEGAProject.rest;

import com.aquaq.project.SEGAProject.rest.exceptions.InvalidInputException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;

@Component
public class RestValidation {

    private static final Logger logger = LoggerFactory.getLogger(RestValidation.class);
    public RestValidation(){ }

    public ResponseEntity<String> createResponse(String body, HttpStatus status) {
        logger.info("Creating Response Entity");
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json");
        return ResponseEntity.status(status.value()).headers(responseHeaders).body(body);
    }

    public void validateGradDate(String gradDate){
        logger.info("Validation graduation date format");
        Pattern pattern = Pattern.compile("\\b^[0-9]{4}+$\\b");
        Matcher matcher = pattern.matcher(gradDate);
        boolean matchFound = matcher.find();
        if(!matchFound){
            throw new InvalidInputException("Graduation date format invalid, must follow the following pattern: YEAR (eg. 2022)");
        }
        int year = parseInt(gradDate);
        if(year < 2000){
            throw new InvalidInputException("Graduation date format invalid, year must be after 2000");
        }
        if(year > 3000){
            throw new InvalidInputException("Graduation date format invalid, year must be before 3000");
        }
    }

    public void validateSemester(String semester){
        logger.info("Validating semester format");
        Pattern pattern = Pattern.compile("\\b^(?:SUMMER|WINTER|AUTUMN|SPRING)[0-9]{4}+$\\b");
        Matcher matcher = pattern.matcher(semester);
        boolean matchFound = matcher.find();
        if(!matchFound){
            throw new InvalidInputException("Semester format invalid, must follow the following pattern: SEASONYEAR (eg. SUMMER2022)");
        }
        int year = parseInt(semester.substring(semester.length() - 4));
        if(year < 2000){
            throw new InvalidInputException("Semester format invalid, year must be after 2000");
        }
        if(year > 3000){
            throw new InvalidInputException("Semester format invalid, year must be before 3000");
        }
    }

    public void validateName(String name){
        logger.info("Validating student name format");
        Pattern pattern = Pattern.compile("^[a-z A-Z]*$");
        Matcher matcher = pattern.matcher(name);
        boolean matchFound = matcher.find();
        if(!matchFound){
            throw new InvalidInputException("Name: " + name + " Invalid - Must only contain letters (No numbers or symbols)");
        }
    }
}
