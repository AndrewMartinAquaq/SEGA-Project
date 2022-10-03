package com.aquaq.project.SEGAProject.rest;

import com.aquaq.project.SEGAProject.SegaProjectApplication;
import com.aquaq.project.SEGAProject.entity.Student;
import com.aquaq.project.SEGAProject.repository.StudentJdbcDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class StudentRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentJdbcDao repository;

    @BeforeEach
    public void setup(){
        //repository = mock(StudentJdbcDao.class);
    }

    @Test
    public void getStudentByIdTest() throws Exception {
        int expectedId = 1;
        String actualFirstName = "John";
        String actualLastName = "Doe";
        String actualGradDate = "DECEMBER2022";
        when(repository.getById(1)).thenReturn(new Student(expectedId, actualFirstName, actualLastName, actualGradDate));
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

        when(repository.getByName("John")).thenReturn(studentList);
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/api/student/name?name=John"))
                .andExpect(status().isOk()).andReturn().getResponse();
        assertTrue(response.getContentAsString().contains(Integer.toString(expectedId)));
        assertTrue(response.getContentAsString().contains(actualFirstName));
        assertTrue(response.getContentAsString().contains(actualLastName));
        assertTrue(response.getContentAsString().contains(actualGradDate));
    }

    /*
    @Test
    public void postStudentTest() throws Exception {
        int expectedId = 1;
        String actualFirstName = "John";
        String actualLastName = "Doe";
        String actualGradDate = "DECEMBER2022";;

        when(repository.insert(new Student(expectedId, actualFirstName, actualLastName, actualGradDate))).thenReturn(1);
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.post("/api/student"))
                .andExpect(status().isCreated()).andReturn().getResponse();
        assertTrue(response.getContentAsString().contains(Integer.toString(1)));

    }
    */
}
