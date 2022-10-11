package com.aquaq.project.SEGAProject.entity;

public class EnrollValues {

    private int total;


    private int max;

    public EnrollValues(int total, int max) {
        this.total = total;
        this.max = max;
    }

    public EnrollValues() {
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }
}
