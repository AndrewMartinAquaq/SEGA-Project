package com.aquaq.project.SEGAProject.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class EnrollValuesTest {

    @Test
    public void enrollValuesSettersTest(){
        int actualTotal = 10;
        int actualMax = 5;

        EnrollValues enrollValues = new EnrollValues();

        enrollValues.setTotal(actualTotal);
        enrollValues.setMax(actualMax);

        assertEquals(actualTotal, enrollValues.getTotal());
        assertEquals(actualMax, enrollValues.getMax());
    }

    @Test
    public void enrollValuesObjectTest(){
        int actualTotal = 10;
        int actualMax = 5;

        EnrollValues enrollValues = new EnrollValues(actualTotal, actualMax);

        assertEquals(actualTotal, enrollValues.getTotal());
        assertEquals(actualMax, enrollValues.getMax());
    }

}
