package com.aquaq.project.SEGAProject.rest;

import com.aquaq.project.SEGAProject.dto.StudentDTO;
import com.aquaq.project.SEGAProject.entity.Student;
import com.aquaq.project.SEGAProject.repository.StudentJdbcDao;
import com.aquaq.project.SEGAProject.rest.exceptions.RecordNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentRestController {

    @Autowired
    StudentJdbcDao repository;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping("/student")
    public ResponseEntity<String> postStudent(@RequestBody @Valid StudentDTO studentDTO){
            Student student = this.modelMapper.map(studentDTO, Student.class);
            int id = repository.insert(student);
            String body = "{ \"studentsAdded\" : 1, \"Link\"  : \"http://localhost:8080/api/student/" + id + "\" }";
            ResponseEntity<String> response = new ResponseEntity<String>(body, HttpStatus.CREATED);

            return response;
    }

    @PutMapping("/student/{id}")
    public ResponseEntity<String> putStudent(@RequestBody @Valid StudentDTO studentDTO, @PathVariable @NotBlank int id){
        Student student = this.modelMapper.map(studentDTO, Student.class);
        student.setId(id);

        try {
            repository.update(student);
        } catch (EmptyResultDataAccessException e) {
            throw new RecordNotFoundException("Student record not found at id - " + id);
        }
        String body = "{ \"studentsUpdated\" : 1, \"Link\" : \"http://localhost:8080/api/student/" + id + "\" }";
        ResponseEntity<String> response = new ResponseEntity<String>(body, HttpStatus.OK);

        return response;
    }

    @GetMapping("/student")
    public List<Student> getAllStudents(){
        return repository.getAllStudents();
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
    public ResponseEntity<String> deleteStudentById(@PathVariable @NotBlank int id){
        try {
            repository.deleteByID(id);
        } catch (EmptyResultDataAccessException e) {
            throw new RecordNotFoundException("Student record not found at id - " + id);
        }
        ResponseEntity<String> response = new ResponseEntity<String>("{ \"studentsDeleted\" : 1 }", HttpStatus.OK);

        return response;
    }

}
