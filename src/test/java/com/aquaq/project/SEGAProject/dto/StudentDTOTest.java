package com.aquaq.project.SEGAProject.dto;

import com.aquaq.project.SEGAProject.entity.Student;
import org.junit.jupiter.api.Test;

import javax.validation.Valid;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StudentDTOTest {
    @Test
    public void StudentDTOSettersTest(){
        String actualFirstName = "John";
        String actualLastName = "Doe";
        String actualGradDate = "DECEMBER2022";

        StudentDTO studentDTO = new StudentDTO();

        studentDTO.setFirstName(actualFirstName);
        studentDTO.setLastName(actualLastName);
        studentDTO.setGraduationDate(actualGradDate);

        assertEquals(actualFirstName, studentDTO.getFirstName());
        assertEquals(actualLastName, studentDTO.getLastName());
        assertEquals(actualGradDate, studentDTO.getGraduationDate());
    }

    @Test
    public void StudentDTObjectTest(){
        String actualFirstName = "John";
        String actualLastName = "Doe";
        String actualGradDate = "DECEMBER2022";

        StudentDTO studentDTO = new StudentDTO(actualFirstName, actualLastName, actualGradDate);

        assertEquals(actualFirstName, studentDTO.getFirstName());
        assertEquals(actualLastName, studentDTO.getLastName());
        assertEquals(actualGradDate, studentDTO.getGraduationDate());
    }


}
