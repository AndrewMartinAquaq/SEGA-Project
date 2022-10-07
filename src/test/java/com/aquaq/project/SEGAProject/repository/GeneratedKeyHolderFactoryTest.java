package com.aquaq.project.SEGAProject.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GeneratedKeyHolderFactoryTest {

    private GeneratedKeyHolderFactory generatedKeyHolderFactory = new GeneratedKeyHolderFactory();

    @Test
    public void NewKeyHolderTest(){
        KeyHolder keyHolder = generatedKeyHolderFactory.newKeyHolder();
        assertTrue(keyHolder != null);
        assertEquals(keyHolder.getClass(), GeneratedKeyHolder.class);
    }
}
