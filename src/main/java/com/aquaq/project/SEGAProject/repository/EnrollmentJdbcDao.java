package com.aquaq.project.SEGAProject.repository;

import com.aquaq.project.SEGAProject.entity.EnrollValues;
import com.aquaq.project.SEGAProject.rest.exceptions.InvalidInputException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EnrollmentJdbcDao {
    @Autowired
    public JdbcTemplate jdbcTemplate;

    private static final Logger logger = LoggerFactory.getLogger(EnrollmentJdbcDao.class);

    public int enrollInCourse(int studentId, int courseId){
        logger.info("Enrolling student at Id " + studentId + " in course at Id " + courseId + " from database");

        checkIfRecordsExist(studentId, courseId);
        checkCourseCapacity(courseId);
        checkStudentCredit(studentId, courseId);

        return jdbcTemplate.update("insert into Enrollment(student_id, course_id) values(?, ?);",
                new Object[]{studentId, courseId});
    }

    public int unEnrollFromCourse(int studentId, int courseId){
        logger.info("UnEnrolling student at Id " + studentId + " from course at Id " + courseId + " in database");
        String deleteSql = "delete from Enrollment where student_id = ? and course_id = ?";
        Object[] deleteValues = new Object[] {studentId, courseId};

        int deleted = jdbcTemplate.update(deleteSql, deleteValues);

        if (deleted == 0) {
            throw new EmptyResultDataAccessException(1);
        }

        return deleted;
    }

    private void checkStudentCredit(int studentId, int courseId) {
        logger.info("Validating student credit is not over 20");
        String maxCreditSql = "SELECT sum(COURSE.CREDIT) total, COURSE.CREDIT AS MAX " +
                "  FROM STUDENT " +
                "LEFT OUTER JOIN ENROLLMENT " +
                "  ON STUDENT.ID  = ENROLLMENT .STUDENT_ID " +
                "LEFT OUTER JOIN COURSE " +
                "  ON ENROLLMENT.COURSE_ID  = COURSE.ID " +
                "WHERE STUDENT.ID = ? AND " +
                "COURSE.SEMESTER = (SELECT semester from COURSE where id = ?) " +
                "GROUP BY COURSE.CREDIT";
        try {
            EnrollValues studentCreditCapacity = jdbcTemplate.queryForObject(maxCreditSql, new Object[]{studentId, courseId},
                    new BeanPropertyRowMapper<EnrollValues>(EnrollValues.class));
            if (studentCreditCapacity.getMax() + studentCreditCapacity.getTotal() > 20) {
                throw new InvalidInputException("Students cant have more than 20 credits per semester: " + studentCreditCapacity.getMax()
                        + " + " + studentCreditCapacity.getTotal() + " is greater than 20" );
            }
        }
        catch (EmptyResultDataAccessException e){}
    }

    private void checkCourseCapacity(int courseId) {
        logger.info("Validating course is not at capacity");
        String courseCapSql ="SELECT count(STUDENT.ID) TOTAL, COURSE.CAPACITY AS MAX" +
                "  FROM COURSE " +
                "LEFT OUTER JOIN ENROLLMENT " +
                "  ON COURSE.ID  = ENROLLMENT.COURSE_ID " +
                "LEFT OUTER JOIN STUDENT " +
                "  ON ENROLLMENT.STUDENT_ID  = STUDENT.ID " +
                "WHERE COURSE.ID = ? " +
                "GROUP BY COURSE.CAPACITY";
        EnrollValues courseCapacity = jdbcTemplate.queryForObject(courseCapSql, new Object[]{courseId},
                new BeanPropertyRowMapper<EnrollValues>(EnrollValues.class));
        if(courseCapacity.getTotal() + 1 > courseCapacity.getMax()){
            throw new InvalidInputException("Course capacity met: max students - " + courseCapacity.getMax() + " total students - " + courseCapacity.getTotal() );
        }
    }

    private void checkIfRecordsExist(int studentId, int courseId){
        logger.info("Validating that both student and course both exist");
        String studentSql = "SELECT * FROM STUDENT WHERE ID = ?";
        String courseSql = "SELECT * FROM COURSE WHERE ID = ?";

        Boolean studentExists = jdbcTemplate.query(studentSql, new Object[]{studentId}, new StudentRowMapper()).isEmpty();
        Boolean courseExists = jdbcTemplate.query(courseSql, new Object[]{courseId}, new CourseRowMapper()).isEmpty();

        if(studentExists || courseExists) {
            throw new EmptyResultDataAccessException(1);
        }

    }
}
