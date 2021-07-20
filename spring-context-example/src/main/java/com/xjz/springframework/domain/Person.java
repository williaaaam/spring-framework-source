package com.xjz.springframework.domain;

/**
 * @author Williami
 * @description
 * @date 2021/7/20
 */
public class Person {

	private String name;

	private int gender;

	private String title;

	private String address;

	@Override
	public String toString() {
		return "Person{" +
				"name='" + name + '\'' +
				", gender=" + gender +
				", title='" + title + '\'' +
				", address='" + address + '\'' +
				'}';
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * Bean属性注入后开始调用
	 */
	public void initMethod(){
		System.out.println("Person invoke init method");
	}

	/**
	 * Bean销毁前调用
	 */
	public void destroyMethod(){
		System.out.println("Person invoke Destroy method");
	}

}
