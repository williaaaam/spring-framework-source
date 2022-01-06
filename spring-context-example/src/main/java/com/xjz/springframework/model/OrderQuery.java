package com.xjz.springframework.model;

/**
 * @author Williami
 * @description
 * @date 2022/1/6
 */
public class OrderQuery {

	String orderId;
	String userName;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
