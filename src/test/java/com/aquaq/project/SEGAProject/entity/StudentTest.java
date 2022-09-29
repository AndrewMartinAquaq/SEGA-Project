package com.aquaq.project.SEGAProject.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StudentTest {

    @Test
    public void StudentSettersTest(){
        int actualId = 1;
        String actualFirstName = "John";
        String actualLastName = "Doe";
        String actualGradDate = "DECEMBER2022";

        Student student = new Student();

        student.setId(actualId);
        student.setFirstName(actualFirstName);
        student.setLastName(actualLastName);
        student.setGraduationDate(actualGradDate);

        assertEquals(actualId, student.getId());
        assertEquals(actualFirstName, student.getFirstName());
        assertEquals(actualLastName, student.getLastName());
        assertEquals(actualGradDate, student.getGraduationDate());
    }

    @Test
    public void StudentObjectTest(){
        int actualId = 1;
        String actualFirstName = "John";
        String actualLastName = "Doe";
        String actualGradDate = "DECEMBER2022";

        Student student = new Student(actualId, actualFirstName, actualLastName, actualGradDate);

        assertEquals(actualId, student.getId());
        assertEquals(actualFirstName, student.getFirstName());
        assertEquals(actualLastName, student.getLastName());
        assertEquals(actualGradDate, student.getGraduationDate());
    }

}
