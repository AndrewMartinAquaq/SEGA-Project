package com.aquaq.project.SEGAProject.rest;

import com.aquaq.project.SEGAProject.dto.StudentDTO;
import com.aquaq.project.SEGAProject.entity.Student;
import com.aquaq.project.SEGAProject.repository.StudentJdbcDao;
import com.aquaq.project.SEGAProject.rest.exceptions.InvalidInputException;
import com.aquaq.project.SEGAProject.rest.exceptions.RecordNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

            validatedGradDate(student.getGraduationDate());

            int id = repository.insert(student);
            String body = "{ \"studentsAdded\" : 1, \"Link\"  : \"http://localhost:8080/api/student/" + id + "\" }";

            return createResponseEntity(body, HttpStatus.CREATED);
    }

    @PutMapping("/student/{id}")
    @ResponseBody()
    public ResponseEntity<String> putStudent(@RequestBody @Valid StudentDTO studentDTO, @PathVariable @NotBlank int id){
        Student student = this.modelMapper.map(studentDTO, Student.class);
        student.setId(id);

        validatedGradDate(student.getGraduationDate());

        try {
            repository.update(student);
        } catch (EmptyResultDataAccessException e) {
            throw new RecordNotFoundException("Student record not found at id - " + id);
        }
        String body = "{ \"studentsUpdated\" : 1, \"Link\" : \"http://localhost:8080/api/student/" + id + "\" }";
        return createResponseEntity(body, HttpStatus.OK);
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

        return createResponseEntity("{ \"studentsDeleted\" : 1 }", HttpStatus.OK);
    }

    private void validatedGradDate(String gradDate){
        Pattern pattern = Pattern.compile("\\b[0-9]{4}+$\\b");
        Matcher matcher = pattern.matcher(gradDate);
        boolean matchFound = matcher.find();
        if(!matchFound){
            throw new InvalidInputException("Graduation date format invalid, must follow the following pattern: YEAR (eg. 2022)");
        }
    }

    private static ResponseEntity<String> createResponseEntity(String body, HttpStatus status) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json");
        return ResponseEntity.status(status.value()).headers(responseHeaders).body(body);
    }
}
