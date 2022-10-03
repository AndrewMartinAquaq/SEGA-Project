package com.aquaq.project.SEGAProject.repository;

import com.aquaq.project.SEGAProject.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;

@Repository
public class StudentJdbcDao {

    @Autowired
    public JdbcTemplate jdbcTemplate;

    @Autowired
    public GeneratedKeyHolderFactory keyHolderFactory;


    public List<Student> getAllStudents(){
        return jdbcTemplate.query("select * from Student",
               new StudentRowMapper());
    }

    public Student getById(int id){
        return jdbcTemplate.queryForObject("select * from Student where id=?", new Object[]{id},
                new StudentRowMapper());
    }

    public List<Student> getByName(String name){
       return jdbcTemplate.query("select * from Student where first_name =? or last_name=?", new Object[]{name, name},
                new StudentRowMapper());
    }

    public int insert(Student student){
        KeyHolder keyHolder = keyHolderFactory.newKeyHolder();
        String sql = "insert into student (first_name, last_name, grad_year)" +
                "values(\'" + student.getFirstName() +"\', \'"+ student.getLastName() +"\', \'"+student.getGraduationDate()+"\')";
        jdbcTemplate.update(c -> c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS), keyHolder);

        return keyHolder.getKey().intValue();
    }

    public int update(Student student){
        String sql = "update student set first_name = ?, last_name = ?, grad_year = ? "
                + "where id = ?";

        Object[] values = new Object[]{
                student.getFirstName(),
                student.getLastName(),
                student.getGraduationDate(),
                student.getId()};

        int updated = jdbcTemplate.update(sql, values);

        if(updated == 0){
            throw new EmptyResultDataAccessException(1);
        }

        return student.getId();
    }
    public int deleteByID(int id) {

        int deleted = jdbcTemplate.update("delete from Student where id=?", new Object[]{id});


        if (deleted == 0) {
            throw new EmptyResultDataAccessException(1);
        }

        return deleted;
    }

}
