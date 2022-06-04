package com.xjz.springframework.listener;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author william
 * @title
 * @desc
 * @date 2022/6/2
 **/
public class Main {

	public static void main(String[] args) {

		AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();
		annotationConfigApplicationContext.register(Config.class);
		annotationConfigApplicationContext.refresh();

	}

}
