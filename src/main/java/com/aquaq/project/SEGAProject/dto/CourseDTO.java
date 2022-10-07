package com.aquaq.project.SEGAProject.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CourseDTO {

    @NotBlank
    private String courseName;
    @NotNull
    @Min(value=0)
    private int capacity;
    @NotNull
    @Min(value=0)
    private int credit;
    @NotBlank
    private String subject;
    @NotBlank
    private String semester;

    public CourseDTO() {
    }

    public CourseDTO(String courseName, int capacity, int credit, String subject, String semester) {
        this.courseName = courseName;
        this.capacity = capacity;
        this.credit = credit;
        this.subject = subject;
        this.semester = semester;
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
