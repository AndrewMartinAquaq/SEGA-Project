package com.aquaq.project.SEGAProject.dto;

import com.aquaq.project.SEGAProject.entity.Course;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CourseDTOTest {

    @Test
    public void courseDTOSettersTest(){

        String actualCourseName = "COM101";
        int actualCapacity = 100;
        int actualCredit = 10;
        String actualSubject = "Java Programming";
        String actualSemester = "DECEMBER2022";

        CourseDTO courseDTO = new CourseDTO();

        courseDTO.setCourseName(actualCourseName);
        courseDTO.setCapacity(actualCapacity);
        courseDTO.setCredit(actualCredit);
        courseDTO.setSubject(actualSubject);
        courseDTO.setSemester(actualSemester);

        assertEquals(actualCourseName, courseDTO.getCourseName());
        assertEquals(actualCapacity, courseDTO.getCapacity());
        assertEquals(actualCredit, courseDTO.getCredit());
        assertEquals(actualSubject, courseDTO.getSubject());
        assertEquals(actualSemester, courseDTO.getSemester());

    }

    @Test
    public void courseDTObjectTest(){
        String actualCourseName = "COM101";
        int actualCapacity = 100;
        int actualCredit = 10;
        String actualSubject = "Java Programming";
        String actualSemester = "DECEMBER2022";

        CourseDTO courseDTO = new CourseDTO(actualCourseName, actualCapacity, actualCredit, actualSubject, actualSemester);

        assertEquals(actualCourseName, courseDTO.getCourseName());
        assertEquals(actualCapacity, courseDTO.getCapacity());
        assertEquals(actualCredit, courseDTO.getCredit());
        assertEquals(actualSubject, courseDTO.getSubject());
        assertEquals(actualSemester, courseDTO.getSemester());

    }
}
