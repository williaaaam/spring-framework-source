package com.xjz.springframework.domain;

/**
 * The <init> method is used to initialize object instances.
 * Also, the JVM invokes the <clinit> method to initialize a class whenever necessary.
 *
 * @author Williami
 * @description
 * @date 2021/7/19
 */
public class Apple {

	private static final String jd = null; // <clinit>调用发生在运行时

	/**
	 * <init>
	 */
	private String name;

	private int gender;

	private String title;

	private String address;

	{
		// instance <init>
		System.out.println("instance initialization method");
	}

	// <clinit> class initialization method
	static {

	}

	public Apple() {
	}

	public Apple(String name) {
		this.name = name;
	}

	public Apple(String name, String address) {
		this.name = name;
		this.address = address;
	}

	@Override
	public String toString() {
		return "User{" +
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
	public void initMethod() {
		System.out.println("User invoke init method");
	}

	/**
	 * Bean销毁前调用
	 */
	public void destroyMethod() {
		System.out.println("User invoke Destroy method");
	}

}
