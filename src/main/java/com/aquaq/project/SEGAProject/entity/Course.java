package com.aquaq.project.SEGAProject.entity;

import javax.validation.constraints.NotBlank;


public class Course {

    int id;
    @NotBlank
    String courseName;
    @NotBlank
    int capacity;
    @NotBlank
    int credit;
    @NotBlank
    String subject;
    @NotBlank
    String semester;

    public Course(int id, String courseName, int capacity, int credit, String subject, String semester) {
        this.id = id;
        this.courseName = courseName;
        this.capacity = capacity;
        this.credit = credit;
        this.subject = subject;
        this.semester = semester;
    }

    public Course() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }
}
