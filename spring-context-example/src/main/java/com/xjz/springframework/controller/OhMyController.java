package com.xjz.springframework.controller;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author william
 * @title
 * @desc
 * @date 2021/7/25
 **/
@Component
public class OhMyController {

	public void run() {

	}

	public String eat(String name) {
		System.out.println(this + " 目标方法执行, arg name= " + name);
		if ("giegie".equals(name)) {
			/**
			 * 切面1 环绕通知开始
			 * 切面1 before joinPoint.getThis()获取代理对象= com.xjz.springframework.controller.OnMyController@308a6984
			 * 切面1 before joinPoint.getTarget()获取目标对象= com.xjz.springframework.controller.OnMyController@308a6984
			 * 切面1 before joinPoint.getSignature()获取目标方法签名= String com.xjz.springframework.controller.OnMyController.eat(String)
			 * 切面1 before
			 * com.xjz.springframework.controller.OnMyController@308a6984 目标方法执行, arg name= zhouxinjian
			 * 切面1 afterThrowingjava.lang.RuntimeException: 让我们黑起来号码？
			 * 切面1 after
			 *
			 *
			 * java.lang.RuntimeException: 让我们黑起来号码？
			 */
			//throw new RuntimeException("让我们黑起来好吗？");
		}
		return "eat_" + name;
	}

	public void sleep() {

	}
}
