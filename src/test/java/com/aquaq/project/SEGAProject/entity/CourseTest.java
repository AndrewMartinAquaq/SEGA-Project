package com.aquaq.project.SEGAProject.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CourseTest {

    @Test
    public void courseSettersTest(){
        int actualId = 1;
        String actualCourseName = "COM101";
        int actualCapacity = 100;
        int actualCredit = 10;
        String actualSubject = "Java Programming";
        String actualSemester = "DECEMBER2022";

        Course course = new Course();

        course.setId(actualId);
        course.setCourseName(actualCourseName);
        course.setCapacity(actualCapacity);
        course.setCredit(actualCredit);
        course.setSubject(actualSubject);
        course.setSemester(actualSemester);

        assertEquals(actualId, course.getId());
        assertEquals(actualCourseName, course.getCourseName());
        assertEquals(actualCapacity, course.getCapacity());
        assertEquals(actualCredit, course.getCredit());
        assertEquals(actualSubject, course.getSubject());
        assertEquals(actualSemester, course.getSemester());

    }

    @Test
    public void courseObjectTest(){
        int actualId = 1;
        String actualCourseName = "COM101";
        int actualCapacity = 100;
        int actualCredit = 10;
        String actualSubject = "Java Programming";
        String actualSemester = "DECEMBER2022";

        Course course = new Course(actualId, actualCourseName, actualCapacity, actualCredit, actualSubject, actualSemester);

        assertEquals(actualId, course.getId());
        assertEquals(actualCourseName, course.getCourseName());
        assertEquals(actualCapacity, course.getCapacity());
        assertEquals(actualCredit, course.getCredit());
        assertEquals(actualSubject, course.getSubject());
        assertEquals(actualSemester, course.getSemester());

    }

}
