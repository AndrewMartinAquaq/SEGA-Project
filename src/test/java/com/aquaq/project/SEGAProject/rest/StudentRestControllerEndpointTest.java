package com.aquaq.project.SEGAProject.rest;


import com.aquaq.project.SEGAProject.dto.StudentDTO;
import com.aquaq.project.SEGAProject.entity.Course;
import com.aquaq.project.SEGAProject.entity.Student;
import com.aquaq.project.SEGAProject.repository.CourseJdbcDao;
import com.aquaq.project.SEGAProject.repository.StudentJdbcDao;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentRestController.class)
public class StudentRestControllerEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentJdbcDao studentRepository;

    @MockBean
    private CourseJdbcDao courseRepository;

    @MockBean
    private RestValidation restValidation;

    @MockBean
    private ModelMapper modelMapper;

    @Test
    public void getStudentByIdTest() throws Exception {
        int expectedId = 1;
        String actualFirstName = "John";
        String actualLastName = "Doe";
        String actualGradDate = "2022";
        when(studentRepository.getById(1)).thenReturn(new Student(expectedId, actualFirstName, actualLastName, actualGradDate));
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/api/student/1"))
                .andExpect(status().isOk()).andReturn().getResponse();
        assertTrue(response.getContentAsString().contains(Integer.toString(expectedId)));
        assertTrue(response.getContentAsString().contains(actualFirstName));
        assertTrue(response.getContentAsString().contains(actualLastName));
        assertTrue(response.getContentAsString().contains(actualGradDate));
    }

    @Test
    public void getStudentByNameTest() throws Exception {
        int expectedId = 1;
        String actualFirstName = "John";
        String actualLastName = "Doe";
        String actualGradDate = "2022";

        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student(expectedId, actualFirstName, actualLastName, actualGradDate));

        when(studentRepository.getByName("John")).thenReturn(studentList);
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/api/student/name?name=John"))
                .andExpect(status().isOk()).andReturn().getResponse();
        assertTrue(response.getContentAsString().contains(Integer.toString(expectedId)));
        assertTrue(response.getContentAsString().contains(actualFirstName));
        assertTrue(response.getContentAsString().contains(actualLastName));
        assertTrue(response.getContentAsString().contains(actualGradDate));
    }


    @Test
    @DirtiesContext
    public void postStudentTest() throws Exception {
        int expectedId = 1;
        String actualFirstName = "John";
        String actualLastName = "Doe";
        String actualGradDate = "2022";
        String body = "{ \"studentsAdded\" : 1, \"Link\"  : \"http://localhost:8080/api/student/" + expectedId + "\" }";


        Student student = new Student(expectedId, actualFirstName, actualLastName, actualGradDate);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(student);
        when(restValidation.createResponse(anyString(), any(HttpStatus.class)))
                .thenReturn(responseEntityBuilder(body, HttpStatus.CREATED));

        when(studentRepository.insert(student)).thenReturn(1);
        when(modelMapper.map(any(StudentDTO.class), any())).thenReturn(student);
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.post("/api/student")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestJson))
                .andExpect(status().isCreated()).andReturn().getResponse();
        assertTrue(response.getContentAsString().contains(Integer.toString(1)));
    }

    @Test
    @DirtiesContext
    public void putStudentTest() throws Exception {
        int expectedId = 1;
        String actualFirstName = "John";
        String actualLastName = "Doe";
        String actualGradDate = "2022";
        String body = "{ \"studentsUpdated\" : 1, \"Link\" : \"http://localhost:8080/api/student/" + expectedId + "\" }";


        Student student = new Student(expectedId, actualFirstName, actualLastName, actualGradDate);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(student);

        when(studentRepository.update(student)).thenReturn(1);
        when(restValidation.createResponse(anyString(), any(HttpStatus.class)))
                .thenReturn(responseEntityBuilder(body, HttpStatus.OK));

        when(modelMapper.map(any(StudentDTO.class), any())).thenReturn(student);
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.put("/api/student/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isOk()).andReturn().getResponse();
        assertTrue(response.getContentAsString().contains(Integer.toString(1)));
    }



    @Test
    public void getAllStudentsTest() throws Exception {
        int expectedId = 1;
        String actualFirstName = "John";
        String actualLastName = "Doe";
        String actualGradDate = "2022";

        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student(expectedId, actualFirstName, actualLastName, actualGradDate));

        when(studentRepository.getAllStudents()).thenReturn(studentList);
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/api/student"))
                .andExpect(status().isOk()).andReturn().getResponse();

        assertTrue(response.getContentAsString().contains(Integer.toString(expectedId)));
        assertTrue(response.getContentAsString().contains(actualFirstName));
        assertTrue(response.getContentAsString().contains(actualLastName));
        assertTrue(response.getContentAsString().contains(actualGradDate));
    }

    @Test
    @DirtiesContext
    public void deleteStudentTest() throws Exception{
        String body = "{ \"studentsDeleted\" : 1 }";

        when(studentRepository.deleteByID(1)).thenReturn(1);
        when(restValidation.createResponse(anyString(), any(HttpStatus.class)))
                .thenReturn(responseEntityBuilder(body, HttpStatus.OK));

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.delete("/api/student/1"))
                .andExpect(status().isOk()).andReturn().getResponse();

        assertTrue(response.getContentAsString().contains(Integer.toString(1)));
    }

    @Test
    public void getStudentBySemesterTest() throws Exception {
        int expectedId = 1;
        String actualFirstName = "John";
        String actualLastName = "Doe";
        String actualGradDate = "2022";
        String semester = "SUMMER2023";

        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student(expectedId, actualFirstName, actualLastName, actualGradDate));

        when(studentRepository.getStudentsBySemester(semester)).thenReturn(studentList);
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/api/student/semester?semester=SUMMER2023"))
                .andExpect(status().isOk()).andReturn().getResponse();
        assertTrue(response.getContentAsString().contains(Integer.toString(expectedId)));
        assertTrue(response.getContentAsString().contains(actualFirstName));
        assertTrue(response.getContentAsString().contains(actualLastName));
        assertTrue(response.getContentAsString().contains(actualGradDate));
    }

    @Test
    public void getStudentCoursesTest() throws Exception {
        int actualId = 1;
        String actualCourseName = "COM101";
        int actualCapacity = 100;
        int actualCredit = 10;
        String actualSubject = "Java Programming";
        String actualSemester = "SUMMER2022";

        List<Course> expectedList = new ArrayList<>();

        expectedList.add(new Course(actualId, actualCourseName, actualCapacity, actualCredit, actualSubject, actualSemester));

        when(courseRepository.getStudentsCourses(anyInt(), anyString())).thenReturn(expectedList);
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/api/student/1/course?semester=SUMMER2023"))
                .andExpect(status().isOk()).andReturn().getResponse();
        assertTrue(response.getContentAsString().contains(Integer.toString(actualId)));
        assertTrue(response.getContentAsString().contains(actualCourseName));
        assertTrue(response.getContentAsString().contains(Integer.toString(actualCapacity)));
        assertTrue(response.getContentAsString().contains(Integer.toString(actualCredit)));
        assertTrue(response.getContentAsString().contains(actualSubject));
        assertTrue(response.getContentAsString().contains(actualSemester));
    }

    private ResponseEntity<String> responseEntityBuilder(String body, HttpStatus status){
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json");
        return ResponseEntity.status(status.value()).headers(responseHeaders).body(body);
    }
}
