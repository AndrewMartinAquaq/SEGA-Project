package com.aquaq.project.SEGAProject.rest;

import com.aquaq.project.SEGAProject.dto.EnrollDTO;
import com.aquaq.project.SEGAProject.repository.EnrollmentJdbcDao;
import com.aquaq.project.SEGAProject.rest.exceptions.InvalidInputException;
import com.aquaq.project.SEGAProject.rest.exceptions.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class EnrollmentRestController {

    @Autowired
    EnrollmentJdbcDao repository;

    @Autowired
    RestValidation restValidation;

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
        catch (DuplicateKeyException e){
            throw new InvalidInputException("Student with Id - " + enrollDTO.getStudentId()
                    + " is already enrolled in course with Id - " + enrollDTO.getCourseId());
        }

        String body = "{ \"studentsEnrolled\" : " + studentsEnrolled + ", " +
                "\"StudentLink\"  : \"http://localhost:8080/api/student/" + enrollDTO.getStudentId() + "\", " +
                "\"CourseLink\"  : \"http://localhost:8080/api/course/" + enrollDTO.getCourseId() + "\"}";
        return restValidation.createResponse(body, HttpStatus.CREATED);
    }

    @DeleteMapping("/enroll")
    public ResponseEntity<String> deleteEnroll(@RequestBody @Valid EnrollDTO enrollDTO){

        try {
            repository.unEnrollFromCourse(enrollDTO.getStudentId(), enrollDTO.getCourseId());
        } catch (EmptyResultDataAccessException e) {
            throw new RecordNotFoundException("Enrollment record not found at studentId - "
                    + enrollDTO.getStudentId() + " and CourseId - " + enrollDTO.getCourseId());
        }

        return restValidation.createResponse("{ \"studentsUnEnrolled\" : 1 }", HttpStatus.OK);
    }
}
