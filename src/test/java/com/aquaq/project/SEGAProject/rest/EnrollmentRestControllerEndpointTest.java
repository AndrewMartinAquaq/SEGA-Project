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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EnrollmentRestController.class)
public class EnrollmentRestControllerEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EnrollmentJdbcDao enrollRepository;

    @Test
    @DirtiesContext
    public void postEnrollTest() throws Exception {
        int expectedStudent = 1;
        int expectedCourse = 1;

        EnrollDTO enrollDTO = new EnrollDTO(expectedStudent, expectedCourse);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(enrollDTO);

        when(enrollRepository.enrollInCourse(1, 1)).thenReturn(1);
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.post("/api/enroll")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andExpect(status().isCreated()).andReturn().getResponse();
        assertTrue(response.getContentAsString().contains(Integer.toString(1)));
    }

    @Test
    @DirtiesContext
    public void deleteEnrollTest() throws Exception{
        when(enrollRepository.unEnrollFromCourse(1, 1)).thenReturn(1);

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.delete("/api/enroll?studentId=1&courseId=1"))
                .andExpect(status().isOk()).andReturn().getResponse();

        assertTrue(response.getContentAsString().contains(Integer.toString(1)));
    }
}
