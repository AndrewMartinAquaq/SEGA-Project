package com.aquaq.project.SEGAProject;

import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = SegaProjectApplication.class)
class SegaProjectApplicationTests {

	@Autowired
	private SegaProjectApplication segaProjectApplication;

	@Test
	public void contextLoads() {
	}

	@Test
	public void modelMapperTest(){
		assertEquals(segaProjectApplication.modelMapper().getClass(), ModelMapper.class);
	}

}
