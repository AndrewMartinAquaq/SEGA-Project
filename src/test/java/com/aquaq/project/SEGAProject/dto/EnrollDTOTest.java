package com.aquaq.project.SEGAProject.dto;

import com.aquaq.project.SEGAProject.entity.EnrollValues;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnrollDTOTest {
    @Test
    public void enrollDTOSettersTest(){
        int actualStudentId = 1;
        int actualCourseId = 2;

        EnrollDTO enrollDTO = new EnrollDTO();

        enrollDTO.setStudentId(actualStudentId);
        enrollDTO.setCourseId(actualCourseId);

        assertEquals(actualStudentId, enrollDTO.getStudentId());
        assertEquals(actualCourseId, enrollDTO.getCourseId());
    }

    @Test
    public void enrollDTOObjectTest(){
        int actualStudentId = 1;
        int actualCourseId = 2;

        EnrollDTO enrollDTO = new EnrollDTO(actualStudentId, actualCourseId);

        assertEquals(actualStudentId, enrollDTO.getStudentId());
        assertEquals(actualCourseId, enrollDTO.getCourseId());
    }

}
