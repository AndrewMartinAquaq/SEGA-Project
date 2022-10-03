package com.aquaq.project.SEGAProject.rest;

import com.aquaq.project.SEGAProject.entity.Student;
import com.aquaq.project.SEGAProject.repository.StudentJdbcDao;
import com.aquaq.project.SEGAProject.rest.exceptions.RecordNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentRestController {

    @Autowired
    StudentJdbcDao repository;

    @PostMapping("/student")
    @ResponseBody
    public String postStudent(@Valid Student student){
            int id = repository.insert(student);

            return "{ \"studentsAdded\" : 1, \"Link\" : \"http://localhost:8080/api/student/" + id + "\" }";
    }

    @GetMapping("/student/name")
    @ResponseBody
    public List<Student> getStudentByName(@RequestParam @NotBlank String name){
        List<Student> studentList = repository.getByName(name);

        if(studentList.size() == 0){
            throw new RecordNotFoundException("No student records found where name matches - " + name );
        }

        return studentList;
    }

    @GetMapping("/student/{id}")
    @ResponseBody
    public Student getStudentById(@PathVariable @NotBlank  int id) {
        Student student = new Student();
        try {
            student = repository.getById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new RecordNotFoundException("Student record not found at id - " + id);
        }
        return student;
    }

}
