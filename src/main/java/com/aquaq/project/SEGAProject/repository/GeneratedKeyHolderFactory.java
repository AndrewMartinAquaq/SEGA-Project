package com.aquaq.project.SEGAProject.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

@Component
public class GeneratedKeyHolderFactory {

    private static final Logger logger = LoggerFactory.getLogger(GeneratedKeyHolderFactory.class);

    public KeyHolder newKeyHolder(){
        logger.info("Creating new Generated Key Holder");
        return new GeneratedKeyHolder();
    }

}
