package com.aquaq.project.SEGAProject.rest;

import com.aquaq.project.SEGAProject.dto.EnrollDTO;
import com.aquaq.project.SEGAProject.dto.StudentDTO;
import com.aquaq.project.SEGAProject.entity.Student;
import com.aquaq.project.SEGAProject.repository.EnrollmentJdbcDao;
import com.aquaq.project.SEGAProject.repository.StudentJdbcDao;
import com.aquaq.project.SEGAProject.repository.StudentRowMapper;
import com.aquaq.project.SEGAProject.rest.exceptions.InvalidInputException;
import com.aquaq.project.SEGAProject.rest.exceptions.RecordNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EnrollmentRestControllerTest {
    @InjectMocks
    EnrollmentRestController enrollmentRestController;

    @Mock
    EnrollmentJdbcDao enrollRepository;

    @Mock
    RestValidation restValidation;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void postEnrollTest(){
        int actualStudentId = 1;
        int actualCourseId = 1;
        String body = "{ \"studentsEnrolled\" : 1," +
                "\"StudentLink\"  : \"http://localhost:8080/api/student/" + actualStudentId + "\", " +
                "\"CourseLink\"  : \"http://localhost:8080/api/course/" + actualCourseId + "\"}";

        EnrollDTO enrollDTO = new EnrollDTO(actualStudentId, actualCourseId);

        when(enrollRepository.enrollInCourse(actualStudentId, actualStudentId)).thenReturn(1);
        when(restValidation.createResponse(anyString(), any(HttpStatus.class))).
                thenReturn(responseEntityBuilder(body, HttpStatus.CREATED));

        ResponseEntity<String> response = enrollmentRestController.postEnroll(enrollDTO);

        verify(enrollRepository).enrollInCourse(actualStudentId, actualCourseId);
        verify(restValidation).createResponse(anyString(), any(HttpStatus.class));

        assertTrue(response.toString().contains(body));
    }

    @Test
    public void deleteEnrollTest(){
        String body = "{ \"studentsUnEnrolled\" : 1 }";

        when(enrollRepository.unEnrollFromCourse(anyInt(), anyInt())).thenReturn(1);
        when(restValidation.createResponse(anyString(), any(HttpStatus.class))).
                thenReturn(responseEntityBuilder(body, HttpStatus.OK));

        EnrollDTO enrollDTO = new EnrollDTO(1, 1);

        ResponseEntity<String> response = enrollmentRestController.deleteEnroll(enrollDTO);

        verify(enrollRepository).unEnrollFromCourse(anyInt(), anyInt());
        verify(restValidation).createResponse(anyString(), any(HttpStatus.class));

        assertTrue(response.toString().contains(body));
    }

    @Test
    public void postEnrollIncorrectIdTest(){
        when(enrollRepository.enrollInCourse(anyInt(), anyInt()))
                .thenThrow(EmptyResultDataAccessException.class);
        assertThrows(RecordNotFoundException.class, () -> {
            enrollmentRestController.postEnroll(new EnrollDTO(1, 1));
        });
    }

    @Test
    public void postEnrollAlreadyExistsTest(){
        when(enrollRepository.enrollInCourse(anyInt(), anyInt()))
                .thenThrow(DuplicateKeyException.class);
        assertThrows(InvalidInputException.class, () -> {
            enrollmentRestController.postEnroll(new EnrollDTO(1, 1));
        });
    }

    @Test
    public void deleteUnEnrollDoseNotExistsTest(){
        when(enrollRepository.unEnrollFromCourse(anyInt(), anyInt()))
                .thenThrow(EmptyResultDataAccessException.class);
        assertThrows(RecordNotFoundException.class, () -> {
            enrollmentRestController.deleteEnroll(new EnrollDTO(1, 1));
        });
    }

    private ResponseEntity<String> responseEntityBuilder(String body, HttpStatus status){
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json");
        return ResponseEntity.status(status.value()).headers(responseHeaders).body(body);
    }
}
