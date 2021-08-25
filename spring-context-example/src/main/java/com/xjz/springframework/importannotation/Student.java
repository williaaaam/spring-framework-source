package com.xjz.springframework.importannotation;

/**
 * @author Williami
 * @description
 * @date 2021/8/24
 */
public class Student {

	private String name;

	public Student(String name) {
		this.name = name;
	}

	public Student() {
		System.out.println("Student 无参构造器初始化");
	}

	@Override
	public String toString() {
		return "Student{" +
				"name='" + name + '\'' +
				'}';
	}
}
