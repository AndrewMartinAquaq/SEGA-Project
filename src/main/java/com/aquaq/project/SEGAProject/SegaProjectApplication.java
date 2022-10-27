package com.aquaq.project.SEGAProject;

import org.modelmapper.ModelMapper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication()
public class SegaProjectApplication  {

	private static final Logger logger = LoggerFactory.getLogger(SegaProjectApplication.class);

	public static void main(String[] args) {
		logger.info("Starting application");
		SpringApplication.run(SegaProjectApplication.class, args);
		logger.info("Application started");
	}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		logger.info("modelMapper instance created");
		return modelMapper;
	}
}
