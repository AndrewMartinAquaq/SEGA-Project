package com.aquaq.project.SEGAProject.entity;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


public class Course {

    private int id;
    private String courseName;
    private int capacity;
    private int credit;
    private String subject;
    private String semester;

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
