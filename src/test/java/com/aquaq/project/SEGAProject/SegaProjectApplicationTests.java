package com.aquaq.project.SEGAProject;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class SegaProjectApplicationTests {

	@Autowired
	private ModelMapper modelMapper;

	@Test
	public void contextLoads() {
	}

	@Test
	public void modelMapperTest(){
		assertEquals(modelMapper.getClass(), ModelMapper.class);
	}

}
