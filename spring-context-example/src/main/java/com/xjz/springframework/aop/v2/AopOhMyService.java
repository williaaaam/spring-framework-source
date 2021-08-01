package com.xjz.springframework.aop.v2;

/**
 * @author william
 * @title
 * @desc
 * @date 2021/8/1
 **/
public class AopOhMyService {

	@Override
	public String toString() {
		System.out.println("执行目标类toString方法");
		return "AopOhMyService";
	}

	public void testAop(){
		System.out.println("目标类testAop invoke");
	}

}
