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
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StudentRestControllerTest {
    @InjectMocks
    StudentRestController studentRestController;

    @Mock
    StudentJdbcDao studentRepository;

    @Mock
    ModelMapper modelMapper;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllStudentsTest(){
        int actualId = 1;
        String actualFirstName = "John";
        String actualLastName = "Doe";
        String actualGradDate = "DECEMBER2022";

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
        String actualGradDate = "DECEMBER2022";

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
        String actualGradDate = "DECEMBER2022";

        Student expectedStudent = new Student(actualId, actualFirstName, actualLastName, actualGradDate);

        when(studentRepository.getById(anyInt())).thenReturn(expectedStudent);

        Student actualStudent = studentRestController.getStudentById(1);

        verify(studentRepository).getById(anyInt());

        assertEquals(expectedStudent, actualStudent);
    }

    @Test
    public void deleteStudentByIdTest(){

        when(studentRepository.deleteByID(anyInt())).thenReturn(1);

        ResponseEntity<String> response = studentRestController.deleteStudentById(1);

        verify(studentRepository).deleteByID(anyInt());

        assertTrue(response.toString().contains("1"));
    }

    @Test
    public void postStudentTest(){
        int actualId = 1;
        String actualFirstName = "John";
        String actualLastName = "Doe";
        String actualGradDate = "DECEMBER2022";

        Student expectedStudent = new Student(actualId, actualFirstName, actualLastName, actualGradDate);
        StudentDTO studentDTO = new StudentDTO(actualFirstName, actualLastName, actualGradDate);

        when(studentRepository.insert(expectedStudent)).thenReturn(1);
        when(modelMapper.map(studentDTO, Student.class)).thenReturn(expectedStudent);

        ResponseEntity<String> response = studentRestController.postStudent(studentDTO);

        verify(studentRepository).insert(expectedStudent);
        verify(modelMapper).map(studentDTO, Student.class);

        assertTrue(response.toString().contains("1"));
    }

    @Test
    public void putStudentTest(){
        int actualId = 1;
        String actualFirstName = "John";
        String actualLastName = "Doe";
        String actualGradDate = "DECEMBER2022";

        Student expectedStudent = new Student(actualId, actualFirstName, actualLastName, actualGradDate);
        StudentDTO studentDTO = new StudentDTO(actualFirstName, actualLastName, actualGradDate);

        when(studentRepository.update(expectedStudent)).thenReturn(1);
        when(modelMapper.map(studentDTO, Student.class)).thenReturn(expectedStudent);

        ResponseEntity<String> response = studentRestController.putStudent(studentDTO, actualId);

        verify(studentRepository).update(expectedStudent);
        verify(modelMapper).map(studentDTO, Student.class);

        assertTrue(response.toString().contains("1"));
    }

}
