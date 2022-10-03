package com.aquaq.project.SEGAProject.entity;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

public class Student {

    private int id;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String graduationDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGraduationDate() {
        return graduationDate;
    }

    public void setGraduationDate(String graduationDate) {
        this.graduationDate = graduationDate;
    }

    public Student(){

    }

    public Student(int id, String firstName, String lastName, String graduationDate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.graduationDate = graduationDate;
    }
}
