package com.aquaq.project.SEGAProject.repository;

import com.aquaq.project.SEGAProject.SegaProjectApplication;
import com.aquaq.project.SEGAProject.entity.Course;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes= SegaProjectApplication.class)
public class CourseJdbcDaoTest {

    @InjectMocks
    private CourseJdbcDao repository;

    @Mock
    private JdbcTemplate templateMock;

    @Mock
    private GeneratedKeyHolderFactory keyHolderFactory;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllCoursesTest(){
        int actualId = 1;
        String actualCourseName = "COM101";
        int actualCapacity = 100;
        int actualCredit = 10;
        String actualSubject = "Java Programming";
        String actualSemester = "DECEMBER2022";

        List<Course> mockList = new ArrayList<>();

        mockList.add(new Course(actualId, actualCourseName, actualCapacity, actualCredit, actualSubject, actualSemester));


        when(templateMock.query(anyString(), any(CourseRowMapper.class))).thenReturn(mockList);

        List<Course> courseList = repository.getAllCourses();
        Course course = courseList.get(0);

        verify(templateMock, times(1)).query(anyString(), any(CourseRowMapper.class));

        assertEquals(actualId, course.getId());
        assertEquals(actualCourseName, course.getCourseName());
        assertEquals(actualCapacity, course.getCapacity());
        assertEquals(actualCredit, course.getCredit());
        assertEquals(actualSubject, course.getSubject());
        assertEquals(actualSemester, course.getSemester());


    }

    @DirtiesContext
    @Test
    public void insertCourseTest(){
        int actualId = 1;
        String actualCourseName = "COM101";
        int actualCapacity = 100;
        int actualCredit = 10;
        String actualSubject = "Java Programming";
        String actualSemester = "DECEMBER2022";

        KeyHolder keyHolder = new GeneratedKeyHolder(Arrays.asList(Map.of("id", actualId)));

        when(keyHolderFactory.newKeyHolder()).thenReturn(keyHolder);
        when(templateMock.update(any(PreparedStatementCreator.class), any(GeneratedKeyHolder.class))).thenReturn(1);

        int id = repository.insert(new Course(actualId, actualCourseName, actualCapacity, actualCredit, actualSubject, actualSemester));

        verify(templateMock, times(1)).update(any(PreparedStatementCreator.class), any(GeneratedKeyHolder.class));
        verify(keyHolderFactory, times(1)).newKeyHolder();

        assertEquals(id, actualId);
    }

    @Test
    public void getByIdTest(){
        int actualId = 1;
        String actualCourseName = "COM101";
        int actualCapacity = 100;
        int actualCredit = 10;
        String actualSubject = "Java Programming";
        String actualSemester = "DECEMBER2022";

       Course expectedCourse = new Course(actualId, actualCourseName, actualCapacity, actualCredit, actualSubject, actualSemester);

        when(templateMock.queryForObject(anyString(), any(), any(CourseRowMapper.class))).thenReturn(expectedCourse);

        Course actualCourse = repository.getById(1);

        verify(templateMock, times(1)).queryForObject(anyString(), any(), any(CourseRowMapper.class));

        assertEquals(expectedCourse, actualCourse);
    }

    @Test
    public void getCourseByNameTest(){
        int actualId = 1;
        String actualCourseName = "COM101";
        int actualCapacity = 100;
        int actualCredit = 10;
        String actualSubject = "Java Programming";
        String actualSemester = "DECEMBER2022";

        List<Course> mockList = new ArrayList<>();

        mockList.add(new Course(actualId, actualCourseName, actualCapacity, actualCredit, actualSubject, actualSemester));

        when(templateMock.query(anyString(), any(Object[].class), any(CourseRowMapper.class))).thenReturn(mockList);

        List<Course> courseList = repository.getCourseByName(actualCourseName);
       Course course = courseList.get(0);

        verify(templateMock, times(1)).query(anyString(), any(Object[].class), any(CourseRowMapper.class));

        assertEquals(actualId, course.getId());
        assertEquals(actualCourseName, course.getCourseName());
        assertEquals(actualCapacity, course.getCapacity());
        assertEquals(actualCredit, course.getCredit());
        assertEquals(actualSubject, course.getSubject());
        assertEquals(actualSemester, course.getSemester());
    }

    @Test
    public void getCourseBySubjectTest(){
        int actualId = 1;
        String actualCourseName = "COM101";
        int actualCapacity = 100;
        int actualCredit = 10;
        String actualSubject = "Java Programming";
        String actualSemester = "DECEMBER2022";

        List<Course> mockList = new ArrayList<>();

        mockList.add(new Course(actualId, actualCourseName, actualCapacity, actualCredit, actualSubject, actualSemester));

        when(templateMock.query(anyString(), any(Object[].class), any(CourseRowMapper.class))).thenReturn(mockList);

        List<Course> courseList = repository.getCourseBySubject(actualCourseName);
        Course course = courseList.get(0);

        verify(templateMock, times(1)).query(anyString(), any(Object[].class), any(CourseRowMapper.class));

        assertEquals(actualId, course.getId());
        assertEquals(actualCourseName, course.getCourseName());
        assertEquals(actualCapacity, course.getCapacity());
        assertEquals(actualCredit, course.getCredit());
        assertEquals(actualSubject, course.getSubject());
        assertEquals(actualSemester, course.getSemester());
    }

    @Test
    @DirtiesContext
    public void updateTest(){
        int actualId = 1;
        String actualCourseName = "COM101";
        int actualCapacity = 100;
        int actualCredit = 10;
        String actualSubject = "Java Programming";
        String actualSemester = "DECEMBER2022";
        Course course = new Course(actualId, actualCourseName, actualCapacity, actualCredit, actualSubject, actualSemester);

        String sql = "update course set course_name=?, capacity=?, credit=?, subject=?, semester=? "
                + "where id = ?";

        Object[] values = new Object[]{
                course.getSubject(),
                course.getCapacity(),
                course.getCredit(),
                course.getSubject(),
                course.getSemester(),
                course.getId()
        };

        when(templateMock.update(sql, values)).thenReturn(1);

        int id = repository.update(course);

        verify(templateMock, times(1)).update(sql, values);

        assertEquals(id, actualId);
    }

    @Test
    @DirtiesContext
    public void deleteCourseTest(){
        String sql = "delete from Course where id=?";

        Object[] values = new Object[]{1};

        when(templateMock.update(sql, values)).thenReturn(1);

        int actualNoDeleted = repository.deleteByID(1);

        verify(templateMock, times(1)).update(sql, values);

        assertEquals(1, actualNoDeleted);
    }

    @Test
    public void getCourseWrongIdTest(){
        when(templateMock.queryForObject(anyString(), any(Object[].class), any(CourseRowMapper.class)))
                .thenThrow(EmptyResultDataAccessException.class);
        assertThrows(EmptyResultDataAccessException.class, () -> {
            repository.getById(1);
        });
    }

    @Test
    public void updateCourseWrongIdTest(){
        when(templateMock.update(anyString(), any(Object[].class)))
                .thenReturn(0);
        assertThrows(EmptyResultDataAccessException.class, () -> {
            int actualId = 1;
            String actualCourseName = "COM101";
            int actualCapacity = 100;
            int actualCredit = 10;
            String actualSubject = "Java Programming";
            String actualSemester = "DECEMBER2022";
            Course course = new Course(actualId, actualCourseName, actualCapacity, actualCredit, actualSubject, actualSemester);

            repository.update(course);
        });
    }
    @Test
    public void deleteCourseWrongIdTest(){
        when(templateMock.update(anyString(), any(Object[].class)))
                .thenReturn(0);
        assertThrows(EmptyResultDataAccessException.class, () -> {
            repository.deleteByID(1);
        });
    }

}
