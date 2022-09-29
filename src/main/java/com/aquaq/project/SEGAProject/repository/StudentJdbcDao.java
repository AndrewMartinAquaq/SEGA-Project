package com.aquaq.project.SEGAProject.repository;

import com.aquaq.project.SEGAProject.entity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class StudentJdbcDao {

    @Autowired
    public JdbcTemplate jdbcTemplate;

    class StudentRowMapper implements RowMapper<Student> {
        @Override
        public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
            Student student = new Student();
            student.setId(rs.getInt("id"));
            student.setFirstName(rs.getString("first_name"));
            student.setLastName(rs.getString("last_name"));
            student.setGraduationDate(rs.getString("grad_year"));
            return student;
        }
    }

    public List<Student> getAllStudents(){
        return jdbcTemplate.query("select * from Student",
               new StudentRowMapper());
    }

}
