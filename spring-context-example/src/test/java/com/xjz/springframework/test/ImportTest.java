package com.xjz.springframework.test;

import com.xjz.springframework.a.E;
import com.xjz.springframework.config.AppConfigV5;
import com.xjz.springframework.importannotation.Student;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author Williami
 * @description
 * @date 2021/8/24
 */
public class ImportTest {


	@DisplayName("Import注解将Java对象交给Spring管理")
	@Test
	public void importStudent() {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfigV5.class);
		Student bean = applicationContext.getBean(Student.class);
		// student =  Student{name='Michael'}
		System.out.println("student =  " + bean);
	}

}
