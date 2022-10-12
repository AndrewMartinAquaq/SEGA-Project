package com.aquaq.project.SEGAProject.rest;

import com.aquaq.project.SEGAProject.dto.EnrollDTO;
import com.aquaq.project.SEGAProject.dto.StudentDTO;
import com.aquaq.project.SEGAProject.entity.Student;
import com.aquaq.project.SEGAProject.repository.EnrollmentJdbcDao;
import com.aquaq.project.SEGAProject.repository.StudentJdbcDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EnrollmentRestControllerTest {
    @InjectMocks
    EnrollmentRestController enrollmentRestController;

    @Mock
    EnrollmentJdbcDao enrollRepository;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void postEnrollTest(){
        int actualStudentId = 1;
        int actualCourseId = 1;

        EnrollDTO enrollDTO = new EnrollDTO(actualStudentId, actualCourseId);

        when(enrollRepository.enrollInCourse(actualStudentId, actualStudentId)).thenReturn(1);

        ResponseEntity<String> response = enrollmentRestController.postEnroll(enrollDTO);

        verify(enrollRepository).enrollInCourse(actualStudentId, actualCourseId);

        assertTrue(response.toString().contains("1"));
    }

    @Test
    public void deleteEnrollTest(){

        when(enrollRepository.unEnrollFromCourse(anyInt(), anyInt())).thenReturn(1);

        ResponseEntity<String> response = enrollmentRestController.deleteEnroll(1, 1);

        verify(enrollRepository).unEnrollFromCourse(anyInt(), anyInt());

        assertTrue(response.toString().contains("1"));
    }
}
