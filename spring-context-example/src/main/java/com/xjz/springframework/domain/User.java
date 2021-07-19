package com.xjz.springframework.domain;

/**
 * @author Williami
 * @description
 * @date 2021/7/19
 */
public class User {

	private String name;

	private int gender;

	private String title;


	public User(String name, int gender, String title) {
		this.name = name;
		this.gender = gender;
		this.title = title;
	}

	public User() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
