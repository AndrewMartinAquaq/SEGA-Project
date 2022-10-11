package com.aquaq.project.SEGAProject.rest;

import com.aquaq.project.SEGAProject.dto.EnrollDTO;
import com.aquaq.project.SEGAProject.repository.EnrollmentJdbcDao;
import com.aquaq.project.SEGAProject.rest.exceptions.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class EnrollmentRestController {

    @Autowired
    EnrollmentJdbcDao repository;

    @PostMapping("/enroll")
    public ResponseEntity<String> postEnroll(@RequestBody @Valid EnrollDTO enrollDTO){
        int studentsEnrolled;
        try{
            studentsEnrolled = repository.enrollInCourse(enrollDTO.getStudentId(), enrollDTO.getCourseId());
        }
        catch (EmptyResultDataAccessException e) {
            throw new RecordNotFoundException("Student or Course record not found at student id - " +
                    enrollDTO.getStudentId() + " or course id - " + enrollDTO.getCourseId());
        }

        String body = "{ \"studentsEnrolled\" : " + studentsEnrolled + ", " +
                "\"StudentLink\"  : \"http://localhost:8080/api/student/" + enrollDTO.getStudentId() + "\", " +
                "\"CourseLink\"  : \"http://localhost:8080/api/course/" + enrollDTO.getCourseId() + "\"}";
        return createResponseEntity(body, HttpStatus.CREATED);
    }

    private static ResponseEntity<String> createResponseEntity(String body, HttpStatus status) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json");
        return ResponseEntity.status(status.value()).headers(responseHeaders).body(body);
    }
}
