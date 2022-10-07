package com.aquaq.project.SEGAProject.repository;

import com.aquaq.project.SEGAProject.entity.Course;
import com.aquaq.project.SEGAProject.entity.Student;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CourseRowMapperTest {

    CourseRowMapper rowMapper = new CourseRowMapper();

    @Test
    public void testMapRow() throws Exception {
        ResultSet rs = mock(ResultSet.class);
        int rowNum = 6;

        int actualId = 1;
        String actualCourseName = "COM101";
        int actualCapacity = 100;
        int actualCredit = 10;
        String actualSubject = "Java Programming";
        String actualSemester = "DECEMBER2022";

        when(rs.getInt("id")).thenReturn(actualId);
        when(rs.getString("course_name")).thenReturn(actualCourseName);
        when(rs.getInt("capacity")).thenReturn(actualCapacity);
        when(rs.getInt("credit")).thenReturn(actualCredit);
        when(rs.getString("subject")).thenReturn(actualSubject);
        when(rs.getString("semester")).thenReturn(actualSemester);

        Course result = rowMapper.mapRow(rs, rowNum);

        assertEquals(result.getId(), actualId);
        assertEquals(result.getCourseName(), actualCourseName);
        assertEquals(result.getCapacity(), actualCapacity);
        assertEquals(result.getCredit(), actualCredit);
        assertEquals(result.getSubject(), actualSubject);
        assertEquals(result.getSemester(), actualSemester);

    }
}
