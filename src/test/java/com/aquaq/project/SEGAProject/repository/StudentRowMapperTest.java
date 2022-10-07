package com.aquaq.project.SEGAProject.repository;

import com.aquaq.project.SEGAProject.entity.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.ResultSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StudentRowMapperTest {

    StudentRowMapper rowMapper = new StudentRowMapper();

    @Test
    public void testMapRow() throws Exception {
        ResultSet rs = mock(ResultSet.class);
        int rowNum = 4;

        int actualId = 1;
        String actualFirstName = "John";
        String actualLastName = "Doe";
        String actualGradDate = "DECEMBER2022";

        when(rs.getInt("id")).thenReturn(actualId);
        when(rs.getString("first_name")).thenReturn(actualFirstName);
        when(rs.getString("last_name")).thenReturn(actualLastName);
        when(rs.getString("grad_year")).thenReturn(actualGradDate);


        Student result = rowMapper.mapRow(rs, rowNum);

        assertEquals(result.getId(), actualId);
        assertEquals(result.getFirstName(), actualFirstName);
        assertEquals(result.getLastName(), actualLastName);
        assertEquals(result.getGraduationDate(), actualGradDate);
    }
}
