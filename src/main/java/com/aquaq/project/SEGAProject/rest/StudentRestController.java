package com.aquaq.project.SEGAProject.rest;

import com.aquaq.project.SEGAProject.dto.StudentDTO;
import com.aquaq.project.SEGAProject.entity.Course;
import com.aquaq.project.SEGAProject.entity.Student;
import com.aquaq.project.SEGAProject.repository.CourseJdbcDao;
import com.aquaq.project.SEGAProject.repository.StudentJdbcDao;
import com.aquaq.project.SEGAProject.rest.exceptions.RecordNotFoundException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    StudentJdbcDao studentRepository;

    @Autowired
    CourseJdbcDao courseRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    RestValidation restValidation;

    private static final Logger logger = LoggerFactory.getLogger(StudentRestController.class);

    @PostMapping("/student")
    public ResponseEntity<String> postStudent(@RequestBody @Valid StudentDTO studentDTO){
            logger.info("Student POST API Request");
            Student student = this.modelMapper.map(studentDTO, Student.class);

            restValidation.validateGradDate(student.getGraduationDate());

            int id = studentRepository.insert(student);
            String body = "{ \"studentsAdded\" : 1, \"Link\"  : \"http://localhost:8080/api/student/" + id + "\" }";

            return restValidation.createResponse(body, HttpStatus.CREATED);
    }

    @PutMapping("/student/{id}")
    @ResponseBody()
    public ResponseEntity<String> putStudent(@RequestBody @Valid StudentDTO studentDTO, @PathVariable @NotBlank int id){
        logger.info("Student PUT API Request");
        Student student = this.modelMapper.map(studentDTO, Student.class);
        student.setId(id);

        restValidation.validateGradDate(student.getGraduationDate());

        try {
            studentRepository.update(student);
        } catch (EmptyResultDataAccessException e) {
            throw new RecordNotFoundException("Student record not found at id - " + id);
        }
        String body = "{ \"studentsUpdated\" : 1, \"Link\" : \"http://localhost:8080/api/student/" + id + "\" }";
        return restValidation.createResponse(body, HttpStatus.OK);
    }

    @GetMapping("/student")
    public List<Student> getAllStudents(){
        logger.info("All Students GET API Request");
        return studentRepository.getAllStudents();
    }

    @GetMapping("/student/name")
    public List<Student> getStudentByName(@RequestParam @NotBlank String name){
        logger.info("Student name GET API Request");
        List<Student> studentList = studentRepository.getByName(name);

        if(studentList.size() == 0){
            throw new RecordNotFoundException("No student records found where name matches - " + name );
        }

        return studentList;
    }

    @GetMapping("/student/{id}")
    public Student getStudentById(@PathVariable @NotBlank  int id) {
        logger.info("Student Id GET API Request");
        Student student;
        try {
            student = studentRepository.getById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new RecordNotFoundException("Student record not found at id - " + id);
        }
        return student;
    }

    @DeleteMapping("/student/{id}")
    public ResponseEntity<String> deleteStudentById(@PathVariable @NotBlank int id){
        logger.info("Student DELETE API Request");
        try {
            studentRepository.deleteByID(id);
        } catch (EmptyResultDataAccessException e) {
            throw new RecordNotFoundException("Student record not found at id - " + id);
        }

        return restValidation.createResponse("{ \"studentsDeleted\" : 1 }", HttpStatus.OK);
    }

    @GetMapping("student/semester")
    public List<Student> getStudentsBySemester(@RequestParam @NotBlank String semester){
        logger.info("Student Semester GET API Request");

        restValidation.validateSemester(semester);

        List<Student> studentList = studentRepository.getStudentsBySemester(semester);

        if(studentList.size() == 0){
            throw new RecordNotFoundException("No students found enrolled during semester - " + semester);
        }

        return studentList;
    }

    @GetMapping("student/{id}/course")
    public List<Course> getStudentsCourses(@RequestParam(required = false) @NotBlank String semester, @PathVariable int id){
        logger.info("Student Course GET API Request");
        if(semester != null){
            restValidation.validateSemester(semester);
        }

        List<Course> studentCoursesList =courseRepository.getStudentsCourses(id, semester);

        if(studentCoursesList.size() == 0){
            throw new RecordNotFoundException("No student at id - " + id +" or not enrolled in semester - " + semester);
        }

        return studentCoursesList;
    }
}
