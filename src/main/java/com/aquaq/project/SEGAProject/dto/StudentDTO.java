package com.aquaq.project.SEGAProject.dto;

import javax.validation.constraints.NotBlank;

public class StudentDTO {
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank
    private String graduationDate;

    public StudentDTO() {
    }

    public StudentDTO(String firstName, String lastName, String graduationDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.graduationDate = graduationDate;
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
}
