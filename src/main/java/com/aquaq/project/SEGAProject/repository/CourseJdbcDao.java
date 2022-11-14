package com.aquaq.project.SEGAProject.repository;

import com.aquaq.project.SEGAProject.entity.Course;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class CourseJdbcDao {

    @Autowired
    public JdbcTemplate jdbcTemplate;

    @Autowired
    public GeneratedKeyHolderFactory keyHolderFactory;

    private static final Logger logger = LoggerFactory.getLogger(CourseJdbcDao.class);

    public List<Course> getAllCourses(){
        logger.info("Getting all courses from database");
        return jdbcTemplate.query("select * from Course",
                new CourseRowMapper());
    }

    public List<Course> getCourseBySubject(String subject){
        logger.info("Getting all courses with subject of " + subject + " from database");
        return jdbcTemplate.query("select * from Course where subject=?", new Object[]{subject},
                new CourseRowMapper());
    }

    public Course getById(int id){
        logger.info("Getting course with Id of " + id + " from database");
        return jdbcTemplate.queryForObject("select * from Course where id=?", new Object[]{id},
                new CourseRowMapper());
    }

    public List<Course> getCourseByName(String name){
        logger.info("Getting courses with name " + name + " from database");
        return jdbcTemplate.query("select * from Course where course_name =? or subject=?", new Object[]{name, name},
                new CourseRowMapper());
    }

    public int insert(Course course){
        logger.info("Inserting new course into database");
        KeyHolder keyHolder = keyHolderFactory.newKeyHolder();

        String sql = "insert into course (course_name, capacity, credit, subject, semester)" +
              "values(?, ?, ?, ?, ?)";

        jdbcTemplate.update(c -> { PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, course.getCourseName());
            ps.setString(2, Integer.toString(course.getCapacity()));
            ps.setString(3, Integer.toString(course.getCredit()));
            ps.setString(4, course.getSubject());
            ps.setString(5, course.getSemester());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }

    public int update(Course course){
        logger.info("Update course at Id " + course.getId() + " in database");
        String sql = "update course set course_name=?, capacity=?, credit=?, subject=?, semester=? "
                + "where id = ?";

        Object[] values = new Object[]{
                course.getCourseName(),
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
        logger.info("Deleting course at Id " + id + " from database");
        int deleted = jdbcTemplate.update("delete from Course where id=?", new Object[]{id});


        if (deleted == 0) {
            throw new EmptyResultDataAccessException(1);
        }

        return deleted;
    }

    public List<Course> getStudentsCourses(int id, String semester) {
        logger.info("Getting courses student at Id " + id + " from database");
        String sql = "SELECT COURSE.* " +
                "  FROM COURSE " +
                "LEFT OUTER JOIN ENROLLMENT " +
                "  ON COURSE .ID  = ENROLLMENT.COURSE_ID " +
                "LEFT OUTER JOIN STUDENT " +
                "  ON ENROLLMENT.STUDENT_ID = STUDENT.ID " +
                " WHERE STUDENT.ID = ?";

        Object[] data;

        if(semester == null){
            data =  new Object[]{ id };
        }
        else{
            data =  new Object[]{ id,  semester };
            sql = sql.concat(" AND COURSE.SEMESTER = ?");
        }

        return jdbcTemplate.query(sql, data, new CourseRowMapper());
    }

}
