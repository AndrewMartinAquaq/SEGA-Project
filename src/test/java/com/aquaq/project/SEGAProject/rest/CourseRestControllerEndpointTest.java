package com.aquaq.project.SEGAProject.rest;

import com.aquaq.project.SEGAProject.dto.CourseDTO;
import com.aquaq.project.SEGAProject.entity.Course;
import com.aquaq.project.SEGAProject.repository.CourseJdbcDao;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CourseRestController.class)
public class CourseRestControllerEndpointTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CourseJdbcDao courseRepository;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private RestValidation restValidation;

    @Test
    public void getAllCoursesTest() throws Exception {
        int actualId = 1;
        String actualCourseName = "COM101";
        int actualCapacity = 100;
        int actualCredit = 10;
        String actualSubject = "Java Programming";
        String actualSemester = "SUMMER2022";

        List<Course> courseList = new ArrayList<>();

        courseList.add(new Course(actualId, actualCourseName, actualCapacity, actualCredit, actualSubject, actualSemester));

        when(courseRepository.getAllCourses()).thenReturn(courseList);
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/api/course"))
                .andExpect(status().isOk()).andReturn().getResponse();
        assertTrue(response.getContentAsString().contains(Integer.toString(actualId)));
        assertTrue(response.getContentAsString().contains(actualCourseName));
        assertTrue(response.getContentAsString().contains(Integer.toString(actualCapacity)));
        assertTrue(response.getContentAsString().contains(Integer.toString(actualCredit)));
        assertTrue(response.getContentAsString().contains(actualSubject));
        assertTrue(response.getContentAsString().contains(actualSemester));
    }

    @Test
    public void getAllCoursesBySubjectTest() throws Exception {
        int actualId = 1;
        String actualCourseName = "COM101";
        int actualCapacity = 100;
        int actualCredit = 10;
        String actualSubject = "Python";
        String actualSemester = "SUMMER2022";

        List<Course> courseList = new ArrayList<>();

        courseList.add(new Course(actualId, actualCourseName, actualCapacity, actualCredit, actualSubject, actualSemester));

        when(courseRepository.getCourseBySubject(actualSubject)).thenReturn(courseList);
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/api/course?subject=Python"))
                .andExpect(status().isOk()).andReturn().getResponse();
        assertTrue(response.getContentAsString().contains(Integer.toString(actualId)));
        assertTrue(response.getContentAsString().contains(actualCourseName));
        assertTrue(response.getContentAsString().contains(Integer.toString(actualCapacity)));
        assertTrue(response.getContentAsString().contains(Integer.toString(actualCredit)));
        assertTrue(response.getContentAsString().contains(actualSubject));
        assertTrue(response.getContentAsString().contains(actualSemester));
    }

    @Test
    public void getCoursesByNameTest() throws Exception {
        int actualId = 1;
        String actualCourseName = "COM101";
        int actualCapacity = 100;
        int actualCredit = 10;
        String actualSubject = "Java Programming";
        String actualSemester = "SUMMER2022";

        List<Course> courseList = new ArrayList<>();

        courseList.add(new Course(actualId, actualCourseName, actualCapacity, actualCredit, actualSubject, actualSemester));

        when(courseRepository.getCourseByName("COM101")).thenReturn(courseList);
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/api/course/name?name=COM101"))
                .andExpect(status().isOk()).andReturn().getResponse();
        assertTrue(response.getContentAsString().contains(Integer.toString(actualId)));
        assertTrue(response.getContentAsString().contains(actualCourseName));
        assertTrue(response.getContentAsString().contains(Integer.toString(actualCapacity)));
        assertTrue(response.getContentAsString().contains(Integer.toString(actualCredit)));
        assertTrue(response.getContentAsString().contains(actualSubject));
        assertTrue(response.getContentAsString().contains(actualSemester));
    }

    @Test
    public void getCoursesByIdTest() throws Exception {
        int actualId = 1;
        String actualCourseName = "COM101";
        int actualCapacity = 100;
        int actualCredit = 10;
        String actualSubject = "Java Programming";
        String actualSemester = "SUMMER2022";


        Course course = new Course(actualId, actualCourseName, actualCapacity, actualCredit, actualSubject, actualSemester);

        when(courseRepository.getById(actualId)).thenReturn(course);
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/api/course/1"))
                .andExpect(status().isOk()).andReturn().getResponse();
        assertTrue(response.getContentAsString().contains(Integer.toString(actualId)));
        assertTrue(response.getContentAsString().contains(actualCourseName));
        assertTrue(response.getContentAsString().contains(Integer.toString(actualCapacity)));
        assertTrue(response.getContentAsString().contains(Integer.toString(actualCredit)));
        assertTrue(response.getContentAsString().contains(actualSubject));
        assertTrue(response.getContentAsString().contains(actualSemester));

    }

    @Test
    @DirtiesContext
    public void deleteCourseByIdTest() throws Exception {
        String body = "{ \"coursesDeleted\" : 1 }";

        when(courseRepository.deleteByID(1)).thenReturn(1);
        when(restValidation.createResponse(anyString(), any(HttpStatus.class)))
                .thenReturn(responseEntityBuilder(body, HttpStatus.OK));

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.delete("/api/course/1"))
                .andExpect(status().isOk()).andReturn().getResponse();
        assertTrue(response.getContentAsString().contains(Integer.toString(1)));
    }

    @Test
    @DirtiesContext
    public void postCourseTest() throws Exception {
        int actualId = 1;
        String actualCourseName = "COM101";
        int actualCapacity = 100;
        int actualCredit = 10;
        String actualSubject = "Java Programming";
        String actualSemester = "SUMMER2022";
        String body =  "{ \"Courses\" : 1, \"Link\" : \"http://localhost:8080/api/course/" + actualId + "\" }";


        Course course = new Course(actualId, actualCourseName, actualCapacity, actualCredit, actualSubject, actualSemester);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(course);

        when(courseRepository.insert(course)).thenReturn(1);
        when(modelMapper.map(any(CourseDTO.class), any())).thenReturn(course);
        when(restValidation.createResponse(anyString(), any(HttpStatus.class)))
                .thenReturn(responseEntityBuilder(body, HttpStatus.CREATED));

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.post("/api/course")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated()).andReturn().getResponse();
        assertTrue(response.getContentAsString().contains(Integer.toString(1)));
    }

    @Test
    @DirtiesContext
    public void putCourseTest() throws Exception {
        int actualId = 1;
        String actualCourseName = "COM101";
        int actualCapacity = 100;
        int actualCredit = 10;
        String actualSubject = "Java Programming";
        String actualSemester = "SUMMER2022";
        String body = "{ \"coursesUpdated\" : 1, \"Link\" : \"http://localhost:8080/api/course/" + actualId + "\" }";

        Course course = new Course(actualId, actualCourseName, actualCapacity, actualCredit, actualSubject, actualSemester);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(course);

        when(courseRepository.update(course)).thenReturn(1);
        when(modelMapper.map(any(CourseDTO.class), any())).thenReturn(course);
        when(restValidation.createResponse(anyString(), any(HttpStatus.class)))
                .thenReturn(responseEntityBuilder(body, HttpStatus.OK));

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.put("/api/course/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk()).andReturn().getResponse();
        assertTrue(response.getContentAsString().contains(Integer.toString(1)));
    }

    private ResponseEntity<String> responseEntityBuilder(String body, HttpStatus status){
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json");
        return ResponseEntity.status(status.value()).headers(responseHeaders).body(body);
    }
}
