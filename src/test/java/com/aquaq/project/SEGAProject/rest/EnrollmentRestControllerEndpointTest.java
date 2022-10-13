package com.aquaq.project.SEGAProject.rest;

import com.aquaq.project.SEGAProject.dto.EnrollDTO;
import com.aquaq.project.SEGAProject.dto.StudentDTO;
import com.aquaq.project.SEGAProject.entity.Student;
import com.aquaq.project.SEGAProject.repository.EnrollmentJdbcDao;
import com.aquaq.project.SEGAProject.repository.StudentJdbcDao;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EnrollmentRestController.class)
public class EnrollmentRestControllerEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EnrollmentJdbcDao enrollRepository;

    @MockBean
    private RestValidation restValidation;

    @Test
    @DirtiesContext
    public void postEnrollTest() throws Exception {
        int expectedStudent = 1;
        int expectedCourse = 1;
        String body = "{ \"studentsEnrolled\" : 1," +
                "\"StudentLink\"  : \"http://localhost:8080/api/student/" + expectedStudent + "\", " +
                "\"CourseLink\"  : \"http://localhost:8080/api/course/" + expectedCourse + "\"}";

        EnrollDTO enrollDTO = new EnrollDTO(expectedStudent, expectedCourse);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(enrollDTO);

        when(enrollRepository.enrollInCourse(1, 1)).thenReturn(1);
        when(restValidation.createResponse(anyString(), any(HttpStatus.class))).
                thenReturn(responseEntityBuilder(body, HttpStatus.CREATED));

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.post("/api/enroll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated()).andReturn().getResponse();
        assertTrue(response.getContentAsString().contains(Integer.toString(1)));
    }

    @Test
    @DirtiesContext
    public void deleteEnrollTest() throws Exception{
        int expectedStudent = 1;
        int expectedCourse = 1;

        String body = "{ \"studentsUnEnrolled\" : 1 }";

        EnrollDTO enrollDTO = new EnrollDTO(expectedStudent, expectedCourse);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(enrollDTO);

        when(enrollRepository.enrollInCourse(1, 1)).thenReturn(1);
        when(restValidation.createResponse(anyString(), any(HttpStatus.class))).
                thenReturn(responseEntityBuilder(body, HttpStatus.OK));

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.delete("/api/enroll")
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
