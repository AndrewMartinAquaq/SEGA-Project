package com.aquaq.project.SEGAProject.rest;

import com.aquaq.project.SEGAProject.dto.CourseDTO;
import com.aquaq.project.SEGAProject.entity.Course;
import com.aquaq.project.SEGAProject.repository.CourseJdbcDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CourseRestControllerTest {
    @InjectMocks
    CourseRestController courseRestController;

    @Mock
    CourseJdbcDao courseJdbcDao;

    @Mock
    ModelMapper modelMapper;

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
        String actualSemester = "SUMMER2022";

        List<Course> expectedList = new ArrayList<>();

        expectedList.add(new Course(actualId, actualCourseName, actualCapacity, actualCredit, actualSubject, actualSemester));
        when(courseJdbcDao.getAllCourses()).thenReturn(expectedList);
        List<Course> actualList = courseRestController.getAllCourse(null);

        verify(courseJdbcDao).getAllCourses();

        assertEquals(expectedList.get(0), actualList.get(0));
    }

    @Test
    public void getCourseByNameTest(){
        int actualId = 1;
        String actualCourseName = "COM101";
        int actualCapacity = 100;
        int actualCredit = 10;
        String actualSubject = "Java Programming";
        String actualSemester = "SUMMER2022";

        List<Course> expectedList = new ArrayList<>();

        expectedList.add(new Course(actualId, actualCourseName, actualCapacity, actualCredit, actualSubject, actualSemester));
        when(courseJdbcDao.getCourseByName(actualCourseName)).thenReturn(expectedList);
        List<Course> actualList = courseRestController.getCourseByName(actualCourseName);

        verify(courseJdbcDao).getCourseByName(actualCourseName);

        assertEquals(expectedList.get(0), actualList.get(0));
    }

    @Test
    public void getCourseByIdTest(){
        int actualId = 1;
        String actualCourseName = "COM101";
        int actualCapacity = 100;
        int actualCredit = 10;
        String actualSubject = "Java Programming";
        String actualSemester = "SUMMER2022";

        Course expectedCourse = new Course(actualId, actualCourseName, actualCapacity, actualCredit, actualSubject, actualSemester);

        when(courseJdbcDao.getById(anyInt())).thenReturn(expectedCourse);

        Course actualCourse = courseRestController.getCourseById(1);

        verify(courseJdbcDao).getById(anyInt());

        assertEquals(expectedCourse, actualCourse);
    }

    @Test
    public void deleteCourseByIdTest(){

        when(courseJdbcDao.deleteByID(anyInt())).thenReturn(1);

        ResponseEntity<String> response = courseRestController.deleteCourseById(1);

        verify(courseJdbcDao).deleteByID(anyInt());

        assertTrue(response.toString().contains("1"));
    }

    @Test
    public void postCourseTest(){
        int actualId = 1;
        String actualCourseName = "COM101";
        int actualCapacity = 100;
        int actualCredit = 10;
        String actualSubject = "Java Programming";
        String actualSemester = "SUMMER2022";

        Course expectedCourse = new Course(actualId, actualCourseName, actualCapacity, actualCredit, actualSubject, actualSemester);
        CourseDTO courseDTO = new CourseDTO(actualCourseName, actualCapacity, actualCredit, actualSubject, actualSemester);

        when(courseJdbcDao.insert(expectedCourse)).thenReturn(1);
        when(modelMapper.map(courseDTO, Course.class)).thenReturn(expectedCourse);

        ResponseEntity<String> response = courseRestController.postCourse(courseDTO);

        verify(courseJdbcDao).insert(expectedCourse);
        verify(modelMapper).map(courseDTO, Course.class);

        assertTrue(response.toString().contains("1"));
    }

    @Test
    public void putCourseTest(){
        int actualId = 1;
        String actualCourseName = "COM101";
        int actualCapacity = 100;
        int actualCredit = 10;
        String actualSubject = "Java Programming";
        String actualSemester = "SUMMER2022";

        Course expectedCourse = new Course(actualId, actualCourseName, actualCapacity, actualCredit, actualSubject, actualSemester);
        CourseDTO courseDTO = new CourseDTO(actualCourseName, actualCapacity, actualCredit, actualSubject, actualSemester);

        when(courseJdbcDao.update(expectedCourse)).thenReturn(1);
        when(modelMapper.map(courseDTO, Course.class)).thenReturn(expectedCourse);

        ResponseEntity<String> response = courseRestController.putCourse(courseDTO, actualId);

        verify(courseJdbcDao).update(expectedCourse);
        verify(modelMapper).map(courseDTO, Course.class);

        assertTrue(response.toString().contains("1"));
    }

}
