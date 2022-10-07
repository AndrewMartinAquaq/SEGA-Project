package com.aquaq.project.SEGAProject.repository;

import com.aquaq.project.SEGAProject.SegaProjectApplication;
import com.aquaq.project.SEGAProject.entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes= SegaProjectApplication.class)
public class StudentJdbcDaoTest {

    @InjectMocks
    private StudentJdbcDao repository;

    @Mock
    private JdbcTemplate templateMock;

    @Mock
    private GeneratedKeyHolderFactory keyHolderFactory;

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

        List<Student> mockList = new ArrayList<>();

        mockList.add(new Student(actualId, actualFirstName, actualLastName, actualGradDate));


        when(templateMock.query(anyString(), any(StudentRowMapper.class))).thenReturn(mockList);

        List<Student> studentList = repository.getAllStudents();
        Student student = studentList.get(0);

        verify(templateMock, times(1)).query(anyString(), any(StudentRowMapper.class));

        assertEquals(actualId, student.getId());
        assertEquals(actualFirstName, student.getFirstName());
        assertEquals(actualLastName, student.getLastName());
        assertEquals(actualGradDate, student.getGraduationDate());
    }

    @DirtiesContext
    @Test
    public void insertStudentTest(){
        int expectedId = 1;
        String actualFirstName = "John";
        String actualLastName = "Doe";
        String actualGradDate = "DECEMBER2022";

        KeyHolder keyHolder = new GeneratedKeyHolder(Arrays.asList(Map.of("id", expectedId)));

        when(keyHolderFactory.newKeyHolder()).thenReturn(keyHolder);
        when(templateMock.update(any(PreparedStatementCreator.class), any(GeneratedKeyHolder.class))).thenReturn(1);

        int actualId = repository.insert(new Student(expectedId, actualFirstName, actualLastName, actualGradDate));

        verify(templateMock, times(1)).update(any(PreparedStatementCreator.class), any(GeneratedKeyHolder.class));
        verify(keyHolderFactory, times(1)).newKeyHolder();

        assertEquals(expectedId, actualId);
    }

    @Test
    public void getByIdTest(){
        int expectedId = 1;
        String actualFirstName = "John";
        String actualLastName = "Doe";
        String actualGradDate = "DECEMBER2022";

        Student expectedStudent = new Student(expectedId, actualFirstName, actualLastName, actualGradDate);

        when(templateMock.queryForObject(anyString(), any(), any(StudentRowMapper.class))).thenReturn(expectedStudent);

        Student actualStudent = repository.getById(1);

        verify(templateMock, times(1)).queryForObject(anyString(), any(), any(StudentRowMapper.class));

        assertEquals(expectedStudent, actualStudent);
    }

    @Test
    public void getByNameTest(){
        int actualId = 1;
        String actualFirstName = "John";
        String actualLastName = "Doe";
        String actualGradDate = "DECEMBER2022";

        List<Student> mockList = new ArrayList<>();

        mockList.add(new Student(actualId, actualFirstName, actualLastName, actualGradDate));

        when(templateMock.query(anyString(), any(Object[].class), any(StudentRowMapper.class))).thenReturn(mockList);

        List<Student> studentList = repository.getByName(actualFirstName);
        Student student = studentList.get(0);

        verify(templateMock, times(1)).query(anyString(), any(Object[].class), any(StudentRowMapper.class));

        assertEquals(actualId, student.getId());
        assertEquals(actualFirstName, student.getFirstName());
        assertEquals(actualLastName, student.getLastName());
        assertEquals(actualGradDate, student.getGraduationDate());
    }

    @Test
    @DirtiesContext
    public void updateTest(){
        int expectedId = 1;
        String actualFirstName = "John";
        String actualLastName = "Doe";
        String actualGradDate = "DECEMBER2022";
        Student student = new Student(expectedId, actualFirstName, actualLastName, actualGradDate);

        String sql = "update student set first_name = ?, last_name = ?, grad_year = ? "
                + "where id = ?";

        Object[] values = new Object[]{
                student.getFirstName(),
                student.getLastName(),
                student.getGraduationDate(),
                student.getId()};

        when(templateMock.update(sql, values)).thenReturn(1);

        int actualId = repository.update(student);

        verify(templateMock, times(1)).update(sql, values);

        assertEquals(expectedId, actualId);
    }

    @Test
    @DirtiesContext
    public void deleteStudentTest(){
        String sql = "delete from Student where id=?";

        Object[] values = new Object[]{1};

        when(templateMock.update(sql, values)).thenReturn(1);

        int actualNoDeleted = repository.deleteByID(1);

        verify(templateMock, times(1)).update(sql, values);

        assertEquals(1, actualNoDeleted);
    }

}
