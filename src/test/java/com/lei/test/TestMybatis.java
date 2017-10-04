package com.lei.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.lei.service.INovelService;


public class TestMybatis {
	
	@Test
	public void test2() {
		ApplicationContext ac = new ClassPathXmlApplicationContext(new String[] {"classpath:spring.xml","classpath:spring-mybatis.xml"});
		INovelService novelSaveToDB = (INovelService) ac.getBean("novelService");
		novelSaveToDB.save();
	}
	

	@Test
	public void testAToZ() {
		for (int i = 65; i <= 90; i++) {
			System.out.println(String.valueOf((char)i));
		}
	}
}
