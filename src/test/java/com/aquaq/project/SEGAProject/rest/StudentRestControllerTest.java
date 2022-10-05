package com.aquaq.project.SEGAProject.rest;


import com.aquaq.project.SEGAProject.entity.Student;
import com.aquaq.project.SEGAProject.repository.StudentJdbcDao;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentRestController.class)
public class StudentRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentJdbcDao studentRepository;

    @MockBean
    private ModelMapper modelMapper;

    @Test
    public void getStudentByIdTest() throws Exception {
        int expectedId = 1;
        String actualFirstName = "John";
        String actualLastName = "Doe";
        String actualGradDate = "DECEMBER2022";
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
        String actualGradDate = "DECEMBER2022";

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
        String actualGradDate = "DECEMBER2022";;

        Student student = new Student(expectedId, actualFirstName, actualLastName, actualGradDate);

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson=ow.writeValueAsString(student);

        when(studentRepository.insert(student)).thenReturn(1);
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.post("/api/student")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(requestJson))
                .andExpect(status().isCreated()).andReturn().getResponse();
        assertTrue(response.getContentAsString().contains(Integer.toString(1)));
    }



    @Test
    public void getAllStudentsTest() throws Exception {
        int expectedId = 1;
        String actualFirstName = "John";
        String actualLastName = "Doe";
        String actualGradDate = "DECEMBER2022";

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
        when(studentRepository.deleteByID(1)).thenReturn(1);

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.delete("/api/student/1"))
                .andExpect(status().isOk()).andReturn().getResponse();

        assertTrue(response.getContentAsString().contains(Integer.toString(1)));
    }
}
