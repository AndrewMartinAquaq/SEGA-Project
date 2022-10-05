package com.aquaq.project.SEGAProject.repository;

import com.aquaq.project.SEGAProject.entity.Course;
import com.aquaq.project.SEGAProject.entity.Student;
import org.springframework.jdbc.core.RowMapper;


import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseRowMapper implements RowMapper<Course> {

    @Override
    public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
        Course course = new Course();
        course.setId(rs.getInt("id"));
        course.setCourseName(rs.getString("course_name"));
        course.setCapacity(rs.getInt("capacity"));
        course.setCredit(rs.getInt("credit"));
        course.setSubject(rs.getString("subject"));
        course.setSemester(rs.getString("semester"));
        return course;
    }
}
