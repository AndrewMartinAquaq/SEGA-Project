package com.aquaq.project.SEGAProject.rest;

import com.aquaq.project.SEGAProject.dto.StudentDTO;
import com.aquaq.project.SEGAProject.entity.Student;
import com.aquaq.project.SEGAProject.repository.StudentJdbcDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StudentRestControllerTest {
    @InjectMocks
    StudentRestController studentRestController;

    @Mock
    StudentJdbcDao studentRepository;

    @Mock
    ModelMapper modelMapper;

    @Mock
    RestValidation restValidation;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllStudentsTest(){
        int actualId = 1;
        String actualFirstName = "John";
        String actualLastName = "Doe";
        String actualGradDate = "2022";

        List<Student> expectedList = new ArrayList<>();

        expectedList.add(new Student(actualId, actualFirstName, actualLastName, actualGradDate));
        when(studentRepository.getAllStudents()).thenReturn(expectedList);
        List<Student> actualList = studentRestController.getAllStudents();

        verify(studentRepository).getAllStudents();

        assertEquals(expectedList.get(0), actualList.get(0));
    }

    @Test
    public void getStudentByNameTest(){
        int actualId = 1;
        String actualFirstName = "John";
        String actualLastName = "Doe";
        String actualGradDate = "2022";

        List<Student> expectedList = new ArrayList<>();

        expectedList.add(new Student(actualId, actualFirstName, actualLastName, actualGradDate));
        when(studentRepository.getByName(anyString())).thenReturn(expectedList);
        List<Student> actualList = studentRestController.getStudentByName(actualFirstName);

        verify(studentRepository).getByName(anyString());

        assertEquals(expectedList.get(0), actualList.get(0));
    }

    @Test
    public void getStudentByIdTest(){
        int actualId = 1;
        String actualFirstName = "John";
        String actualLastName = "Doe";
        String actualGradDate = "2022";

        Student expectedStudent = new Student(actualId, actualFirstName, actualLastName, actualGradDate);

        when(studentRepository.getById(anyInt())).thenReturn(expectedStudent);

        Student actualStudent = studentRestController.getStudentById(1);

        verify(studentRepository).getById(anyInt());

        assertEquals(expectedStudent, actualStudent);
    }

    @Test
    public void deleteStudentByIdTest(){
        String body = "{ \"studentsDeleted\" : 1 }";
        when(studentRepository.deleteByID(anyInt())).thenReturn(1);
        when(restValidation.createResponse(anyString(), any(HttpStatus.class)))
                .thenReturn(responseEntityBuilder(body, HttpStatus.OK));

        ResponseEntity<String> response = studentRestController.deleteStudentById(1);

        verify(studentRepository).deleteByID(anyInt());
        verify(restValidation).createResponse(anyString(), any(HttpStatus.class));

        assertTrue(response.toString().contains(body));
    }

    @Test
    public void postStudentTest(){
        int actualId = 1;
        String actualFirstName = "John";
        String actualLastName = "Doe";
        String actualGradDate = "2022";
        String body = "{ \"studentsAdded\" : 1, \"Link\"  : \"http://localhost:8080/api/student/" + actualId + "\" }";

        Student expectedStudent = new Student(actualId, actualFirstName, actualLastName, actualGradDate);
        StudentDTO studentDTO = new StudentDTO(actualFirstName, actualLastName, actualGradDate);

        when(studentRepository.insert(expectedStudent)).thenReturn(1);
        when(modelMapper.map(studentDTO, Student.class)).thenReturn(expectedStudent);
        when(restValidation.createResponse(anyString(), any(HttpStatus.class)))
                .thenReturn(responseEntityBuilder(body, HttpStatus.CREATED));

        ResponseEntity<String> response = studentRestController.postStudent(studentDTO);

        verify(studentRepository).insert(expectedStudent);
        verify(modelMapper).map(studentDTO, Student.class);
        verify(restValidation).createResponse(anyString(), any(HttpStatus.class));

        assertTrue(response.toString().contains(body));
    }

    @Test
    public void putStudentTest(){
        int actualId = 1;
        String actualFirstName = "John";
        String actualLastName = "Doe";
        String actualGradDate = "2022";
        String body = "{ \"studentsUpdated\" : 1, \"Link\" : \"http://localhost:8080/api/student/" + actualId + "\" }";

        Student expectedStudent = new Student(actualId, actualFirstName, actualLastName, actualGradDate);
        StudentDTO studentDTO = new StudentDTO(actualFirstName, actualLastName, actualGradDate);

        when(studentRepository.update(expectedStudent)).thenReturn(1);
        when(modelMapper.map(studentDTO, Student.class)).thenReturn(expectedStudent);
        when(restValidation.createResponse(anyString(), any(HttpStatus.class)))
                .thenReturn(responseEntityBuilder(body, HttpStatus.OK));

        ResponseEntity<String> response = studentRestController.putStudent(studentDTO, actualId);

        verify(studentRepository).update(expectedStudent);
        verify(modelMapper).map(studentDTO, Student.class);
        verify(restValidation).createResponse(anyString(), any(HttpStatus.class));

        assertTrue(response.toString().contains(body));
    }

    private ResponseEntity<String> responseEntityBuilder(String body, HttpStatus status){
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json");
        return ResponseEntity.status(status.value()).headers(responseHeaders).body(body);
    }

}
