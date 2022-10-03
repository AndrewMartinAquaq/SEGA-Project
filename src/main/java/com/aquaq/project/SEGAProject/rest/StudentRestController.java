package com.aquaq.project.SEGAProject.rest;

import com.aquaq.project.SEGAProject.entity.Student;
import com.aquaq.project.SEGAProject.repository.StudentJdbcDao;
import com.aquaq.project.SEGAProject.rest.exceptions.RecordNotFoundException;
import netscape.javascript.JSObject;
import org.apache.tomcat.util.json.JSONParser;
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

    @PutMapping("/student/{id}")
    @ResponseBody
    public String putStudent(@Valid Student student, @PathVariable @NotBlank int id){
        student.setId(id);

        try {
            repository.update(student);
        } catch (EmptyResultDataAccessException e) {
            throw new RecordNotFoundException("Student record not found at id - " + id);
        }

        return "{ \"studentsUpdated\" : 1, \"Link\" : \"http://localhost:8080/api/student/" + id + "\" }";
    }

    @GetMapping("/student/name")
    public List<Student> getStudentByName(@RequestParam @NotBlank String name){
        List<Student> studentList = repository.getByName(name);

        if(studentList.size() == 0){
            throw new RecordNotFoundException("No student records found where name matches - " + name );
        }

        return studentList;
    }

    @GetMapping("/student/{id}")
    public Student getStudentById(@PathVariable @NotBlank  int id) {
        Student student;
        try {
            student = repository.getById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new RecordNotFoundException("Student record not found at id - " + id);
        }
        return student;
    }

    @DeleteMapping("/student/{id}")
    public String deleteStudentById(@PathVariable @NotBlank int id){
        try {
            repository.deleteByID(id);
        } catch (EmptyResultDataAccessException e) {
            throw new RecordNotFoundException("Student record not found at id - " + id);
        }
        return "{ \"studentsDeleted\" : 1 }";
    }

}
