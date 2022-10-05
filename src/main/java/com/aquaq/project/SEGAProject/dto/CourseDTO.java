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
}
