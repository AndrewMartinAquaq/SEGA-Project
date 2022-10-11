package com.aquaq.project.SEGAProject.dto;

public class EnrollDTO {

    private int studentId;

    private int courseId;

    public EnrollDTO(int studentId, int courseId) {
        this.studentId = studentId;
        this.courseId = courseId;
    }

    public EnrollDTO(){

    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
