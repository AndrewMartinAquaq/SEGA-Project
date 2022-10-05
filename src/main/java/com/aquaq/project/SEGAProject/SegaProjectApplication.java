package com.aquaq.project.SEGAProject;

import com.aquaq.project.SEGAProject.repository.StudentJdbcDao;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication()
public class SegaProjectApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SegaProjectApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	}

	@Bean
	public ModelMapper modelMapper() {
		ModelMapper modelMapper = new ModelMapper();
		return modelMapper;
	}
}
