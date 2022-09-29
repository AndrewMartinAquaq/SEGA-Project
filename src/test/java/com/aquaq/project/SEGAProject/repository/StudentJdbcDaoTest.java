package com.aquaq.project.SEGAProject.repository;

import com.aquaq.project.SEGAProject.SegaProjectApplication;
import com.aquaq.project.SEGAProject.entity.Student;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes= SegaProjectApplication.class)
public class StudentJdbcDaoTest {

    @Autowired
    private StudentJdbcDao repository;

    @Test
    public void getAllStudentsTest(){
        int actualId = 1;
        String actualFirstName = "John";
        String actualLastName = "Doe";
        String actualGradDate = "DECEMBER2022";

        List<Student> studentList = repository.getAllStudents();
        Student student = studentList.get(0);

        assertEquals(actualId, student.getId());
        assertEquals(actualFirstName, student.getFirstName());
        assertEquals(actualLastName, student.getLastName());
        assertEquals(actualGradDate, student.getGraduationDate());
    }

}
