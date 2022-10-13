package com.aquaq.project.SEGAProject.repository;


import com.aquaq.project.SEGAProject.SegaProjectApplication;
import com.aquaq.project.SEGAProject.entity.Course;
import com.aquaq.project.SEGAProject.entity.EnrollValues;
import com.aquaq.project.SEGAProject.entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes= SegaProjectApplication.class)
public class EnrollmentJdbcDoaTest {

    @InjectMocks
    private EnrollmentJdbcDao repository;

    @Mock
    private JdbcTemplate templateMock;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }


    @Test
    @DirtiesContext
    public void enrollInCourseTest(){
        int actualStudentId = 1;
        int actualCourseId = 2;

        EnrollValues enrollValues = new EnrollValues(5, 10);

        String insertSql = "insert into Enrollment(student_id, course_id) values(?, ?);";
        Object[] insertValues = new Object[]{actualStudentId, actualCourseId};

        List<Student> studentList = new ArrayList<>();
        List<Course> courseList = new ArrayList<>();

        studentList.add(new Student());
        courseList.add(new Course());

        when(templateMock.query(anyString(), any(Object[].class), any(StudentRowMapper.class))).thenReturn(studentList);
        when(templateMock.query(anyString(), any(Object[].class), any(CourseRowMapper.class))).thenReturn(courseList);

        when(templateMock.queryForObject(anyString(), any(Object[].class),
                any(BeanPropertyRowMapper.class))).thenReturn(enrollValues);

        when(templateMock.update(insertSql, insertValues)).thenReturn(1);

        int updated = repository.enrollInCourse(actualStudentId, actualCourseId);

        verify(templateMock, times(2)).queryForObject(anyString(), any(Object[].class),
                any(BeanPropertyRowMapper.class));
        verify(templateMock, times(1)).update(insertSql, insertValues);
        verify(templateMock, times(1)).query(anyString(), any(Object[].class), any(CourseRowMapper.class));
        verify(templateMock, times(1)).query(anyString(), any(Object[].class), any(StudentRowMapper.class));

        assertEquals(1, updated);
    }

    @Test
    @DirtiesContext
    public void unEnrollFromCourseTest(){
        String sql = "delete from Enrollment where student_id = ? and course_id = ?";

        Object[] values = new Object[]{1, 1};

        when(templateMock.update(sql, values)).thenReturn(1);

        int actualNoDeleted = repository.unEnrollFromCourse(1, 1);

        verify(templateMock, times(1)).update(sql, values);

        assertEquals(1, actualNoDeleted);
    }

}
