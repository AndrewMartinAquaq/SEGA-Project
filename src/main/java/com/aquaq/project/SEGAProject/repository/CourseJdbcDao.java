package com.aquaq.project.SEGAProject.repository;

import com.aquaq.project.SEGAProject.entity.Course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Statement;
import java.util.List;

@Repository
public class CourseJdbcDao {

    @Autowired
    public JdbcTemplate jdbcTemplate;

    @Autowired
    public GeneratedKeyHolderFactory keyHolderFactory;

    public List<Course> getAllCourses(){
        return jdbcTemplate.query("select * from Course",
                new CourseRowMapper());
    }

    public Course getById(int id){
        return jdbcTemplate.queryForObject("select * from Course where id=?", new Object[]{id},
                new CourseRowMapper());
    }

    public List<Course> getByCourse(String name){
        return jdbcTemplate.query("select * from Course where course_name =? or subject=?", new Object[]{name, name},
                new CourseRowMapper());
    }

    public int insert(Course course){
        KeyHolder keyHolder = keyHolderFactory.newKeyHolder();
        String sql = "insert into course (course_name, capacity, credit, subject, semester)" +
                "values(\'" + course.getCourseName() +"\', \'"+ course.getCapacity() +"\', \'"+ course.getCredit()+
                "\', \'"+ course.getSubject() +"\', \'"+ course.getSemester() +"\')";
        jdbcTemplate.update(c -> c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS), keyHolder);

        return keyHolder.getKey().intValue();
    }

    public int update(Course course){
        String sql = "update course set course_name=?, capacity=?, credit=?, subject=?, semester=? "
                + "where id = ?";

        Object[] values = new Object[]{
                course.getSubject(),
                course.getCapacity(),
                course.getCredit(),
                course.getSubject(),
                course.getSemester(),
                course.getId()
        };

        int updated = jdbcTemplate.update(sql, values);

        if(updated == 0){
            throw new EmptyResultDataAccessException(1);
        }

        return course.getId();
    }
    public int deleteByID(int id) {

        int deleted = jdbcTemplate.update("delete from Course where id=?", new Object[]{id});


        if (deleted == 0) {
            throw new EmptyResultDataAccessException(1);
        }

        return deleted;
    }

}
