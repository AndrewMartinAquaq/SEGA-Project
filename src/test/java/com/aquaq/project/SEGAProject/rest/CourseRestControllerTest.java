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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CourseRestControllerTest {
    @InjectMocks
    CourseRestController courseRestController;

    @Mock
    CourseJdbcDao courseJdbcDao;

    @Mock
    ModelMapper modelMapper;

    @Mock
    RestValidation restValidation;

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
        String body = "{ \"coursesDeleted\" : 1 }";

        when(courseJdbcDao.deleteByID(anyInt())).thenReturn(1);
        when(restValidation.createResponse(anyString(), any(HttpStatus.class)))
                .thenReturn(responseEntityBuilder(body, HttpStatus.OK));

        ResponseEntity<String> response = courseRestController.deleteCourseById(1);

        verify(courseJdbcDao).deleteByID(anyInt());
        verify(restValidation).createResponse(anyString(), any(HttpStatus.class));

        assertTrue(response.toString().contains(body));
    }

    @Test
    public void postCourseTest(){
        int actualId = 1;
        String actualCourseName = "COM101";
        int actualCapacity = 100;
        int actualCredit = 10;
        String actualSubject = "Java Programming";
        String actualSemester = "SUMMER2022";
        String body =  "{ \"Courses\" : 1, \"Link\" : \"http://localhost:8080/api/course/" + actualId + "\" }";

        Course expectedCourse = new Course(actualId, actualCourseName, actualCapacity, actualCredit, actualSubject, actualSemester);
        CourseDTO courseDTO = new CourseDTO(actualCourseName, actualCapacity, actualCredit, actualSubject, actualSemester);

        when(courseJdbcDao.insert(expectedCourse)).thenReturn(1);
        when(modelMapper.map(courseDTO, Course.class)).thenReturn(expectedCourse);
        when(restValidation.createResponse(anyString(), any(HttpStatus.class)))
                .thenReturn(responseEntityBuilder(body, HttpStatus.CREATED));

        ResponseEntity<String> response = courseRestController.postCourse(courseDTO);

        verify(courseJdbcDao).insert(expectedCourse);
        verify(modelMapper).map(courseDTO, Course.class);
        verify(restValidation).createResponse(anyString(), any(HttpStatus.class));

        assertTrue(response.toString().contains(body));
    }

    @Test
    public void putCourseTest(){
        int actualId = 1;
        String actualCourseName = "COM101";
        int actualCapacity = 100;
        int actualCredit = 10;
        String actualSubject = "Java Programming";
        String actualSemester = "SUMMER2022";
        String body = "{ \"coursesUpdated\" : 1, \"Link\" : \"http://localhost:8080/api/course/" + actualId + "\" }";

        Course expectedCourse = new Course(actualId, actualCourseName, actualCapacity, actualCredit, actualSubject, actualSemester);
        CourseDTO courseDTO = new CourseDTO(actualCourseName, actualCapacity, actualCredit, actualSubject, actualSemester);

        when(courseJdbcDao.update(expectedCourse)).thenReturn(1);
        when(modelMapper.map(courseDTO, Course.class)).thenReturn(expectedCourse);
        when(restValidation.createResponse(anyString(), any(HttpStatus.class)))
                .thenReturn(responseEntityBuilder(body, HttpStatus.OK));

        ResponseEntity<String> response = courseRestController.putCourse(courseDTO, actualId);

        verify(courseJdbcDao).update(expectedCourse);
        verify(modelMapper).map(courseDTO, Course.class);
        verify(restValidation).createResponse(anyString(), any(HttpStatus.class));

        assertTrue(response.toString().contains(body));
    }

    private ResponseEntity<String> responseEntityBuilder(String body, HttpStatus status){
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json");
        return ResponseEntity.status(status.value()).headers(responseHeaders).body(body);
    }

}
