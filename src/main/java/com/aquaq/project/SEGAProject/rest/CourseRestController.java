package com.aquaq.project.SEGAProject.rest;

import com.aquaq.project.SEGAProject.dto.CourseDTO;
import com.aquaq.project.SEGAProject.entity.Course;
import com.aquaq.project.SEGAProject.repository.CourseJdbcDao;
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

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api")
public class CourseRestController {

    @Autowired
    CourseJdbcDao repository;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping("/course")
    public ResponseEntity<String> postCourse(@RequestBody @Valid CourseDTO courseDTO){
        Course course = this.modelMapper.map(courseDTO, Course.class);
            int id = repository.insert(course);
            String body =  "{ \"Courses\" : 1, \"Link\" : \"http://localhost:8080/api/course/" + id + "\" }";

            ResponseEntity<String> response = new ResponseEntity<String>(body, HttpStatus.CREATED);

            return response;
    }

    @PutMapping("/course/{id}")
    public ResponseEntity<String> putCourse(@RequestBody @Valid CourseDTO courseDTO, @PathVariable @NotBlank int id){
        Course course = this.modelMapper.map(courseDTO, Course.class);

        course.setId(id);

        try {
            repository.update(course);
        } catch (EmptyResultDataAccessException e) {
            throw new RecordNotFoundException("Course record not found at id - " + id);
        }
        String body = "{ \"coursesUpdated\" : 1, \"Link\" : \"http://localhost:8080/api/course/" + id + "\" }";
        ResponseEntity<String> response = new ResponseEntity<String>(body, HttpStatus.OK);

        return response;
    }

    @GetMapping("/course")
    public List<Course> getAllCourse(){
        return repository.getAllCourses();
    }

    @GetMapping("/course/name")
    public List<Course> getCourseByName(@RequestParam @NotBlank String name){
        List<Course> courseList = repository.getByCourse(name);

        if(courseList.size() == 0){
            throw new RecordNotFoundException("No course records found where name matches - " + name );
        }

        return courseList;
    }

    @GetMapping("/course/{id}")
    public Course getCourseById(@PathVariable @NotBlank  int id) {
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
        try {
            repository.deleteByID(id);
        } catch (EmptyResultDataAccessException e) {
            throw new RecordNotFoundException("Course record not found at id - " + id);
        }
        ResponseEntity<String> response = new ResponseEntity<String>("{ \"coursesDeleted\" : 1 }", HttpStatus.OK);

        return response;
    }

}
