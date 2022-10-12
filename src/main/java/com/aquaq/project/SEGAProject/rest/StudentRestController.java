package com.aquaq.project.SEGAProject.rest;

import com.aquaq.project.SEGAProject.dto.StudentDTO;
import com.aquaq.project.SEGAProject.entity.Course;
import com.aquaq.project.SEGAProject.entity.Student;
import com.aquaq.project.SEGAProject.repository.StudentJdbcDao;
import com.aquaq.project.SEGAProject.rest.exceptions.RecordNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
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

    @Autowired
    RestValidation restValidation;

    @PostMapping("/student")
    public ResponseEntity<String> postStudent(@RequestBody @Valid StudentDTO studentDTO){
            Student student = this.modelMapper.map(studentDTO, Student.class);

            restValidation.validateGradDate(student.getGraduationDate());

            int id = repository.insert(student);
            String body = "{ \"studentsAdded\" : 1, \"Link\"  : \"http://localhost:8080/api/student/" + id + "\" }";

            return restValidation.createResponse(body, HttpStatus.CREATED);
    }

    @PutMapping("/student/{id}")
    @ResponseBody()
    public ResponseEntity<String> putStudent(@RequestBody @Valid StudentDTO studentDTO, @PathVariable @NotBlank int id){
        Student student = this.modelMapper.map(studentDTO, Student.class);
        student.setId(id);

        restValidation.validateGradDate(student.getGraduationDate());

        try {
            repository.update(student);
        } catch (EmptyResultDataAccessException e) {
            throw new RecordNotFoundException("Student record not found at id - " + id);
        }
        String body = "{ \"studentsUpdated\" : 1, \"Link\" : \"http://localhost:8080/api/student/" + id + "\" }";
        return restValidation.createResponse(body, HttpStatus.OK);
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

        return restValidation.createResponse("{ \"studentsDeleted\" : 1 }", HttpStatus.OK);
    }

    @GetMapping("student/semester")
    public List<Student> getStudentsBySemester(@RequestParam @NotBlank String semester){

        restValidation.validateSemester(semester);

        List<Student> studentList = repository.getStudentsBySemester(semester);

        if(studentList.size() == 0){
            throw new RecordNotFoundException("No students found enrolled during semester - " + semester);
        }

        return studentList;
    }

    @GetMapping("student/{id}/course")
    public List<Course> getStudentsCourses(@RequestParam @NotBlank String semester, @PathVariable int id){

        restValidation.validateSemester(semester);

        List<Course> studentCoursesList = repository.getStudentsCourses(id, semester);

        if(studentCoursesList.size() == 0){
            throw new RecordNotFoundException("No student at id - " + id +" or not enrolled in semester - " + semester);
        }

        return studentCoursesList;
    }
}
