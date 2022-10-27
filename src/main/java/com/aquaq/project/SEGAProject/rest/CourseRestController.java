package com.aquaq.project.SEGAProject.rest;

import com.aquaq.project.SEGAProject.dto.CourseDTO;
import com.aquaq.project.SEGAProject.entity.Course;
import com.aquaq.project.SEGAProject.repository.CourseJdbcDao;
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
public class CourseRestController {

    @Autowired
    CourseJdbcDao repository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    RestValidation restValidation;

    private static final Logger logger = LoggerFactory.getLogger(CourseRestController.class);

    @PostMapping("/course")
    public ResponseEntity<String> postCourse(@RequestBody @Valid CourseDTO courseDTO){
        logger.info("Course POST API Request");
        Course course = this.modelMapper.map(courseDTO, Course.class);

        restValidation.validateSemester(course.getSemester());

        int id = repository.insert(course);

        String body =  "{ \"Courses\" : 1, \"Link\" : \"http://localhost:8080/api/course/" + id + "\" }";

        return restValidation.createResponse(body, HttpStatus.CREATED);
    }

    @PutMapping("/course/{id}")
    public ResponseEntity<String> putCourse(@RequestBody @Valid CourseDTO courseDTO, @PathVariable @NotBlank int id){
        logger.info("Course PUT API Request");
        Course course = this.modelMapper.map(courseDTO, Course.class);
        course.setId(id);

        restValidation.validateSemester(course.getSemester());

        try {
            repository.update(course);
        } catch (EmptyResultDataAccessException e) {
            throw new RecordNotFoundException("Course record not found at id - " + id);
        }
        String body = "{ \"coursesUpdated\" : 1, \"Link\" : \"http://localhost:8080/api/course/" + id + "\" }";

        return restValidation.createResponse(body, HttpStatus.OK);
    }

    @GetMapping("/course")
    public List<Course> getAllCourse(@RequestParam(required = false) String subject){
        logger.info("All Course GET API Request");
        if(subject == null){
            return repository.getAllCourses();
        }
        else {
            return repository.getCourseBySubject(subject);
        }

    }

    @GetMapping("/course/name")
    public List<Course> getCourseByName(@RequestParam @NotBlank String name){
        logger.info("Course Name GET API Request");
        List<Course> courseList = repository.getCourseByName(name);

        if(courseList.size() == 0){
            throw new RecordNotFoundException("No course records found where name matches - " + name );
        }

        return courseList;
    }

    @GetMapping("/course/{id}")
    public Course getCourseById(@PathVariable @NotBlank  int id) {
        logger.info("Course Id GET API Request");
        Course course;
        try {
            course = repository.getById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new RecordNotFoundException("Course record not found at id - " + id);
        }
        return course;
    }

    @DeleteMapping("/course/{id}")
    public ResponseEntity<String> deleteCourseById(@PathVariable @NotBlank int id){
        logger.info("Course DELETE API Request");
        try {
            repository.deleteByID(id);
        } catch (EmptyResultDataAccessException e) {
            throw new RecordNotFoundException("Course record not found at id - " + id);
        }

        return restValidation.createResponse("{ \"coursesDeleted\" : 1 }", HttpStatus.OK);
    }
}
