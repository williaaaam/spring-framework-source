package com.xjz.springframework.bc;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author Williami
 * @description
 * @date 2021/8/19
 */
@Component
public class A {
	public A() {
		System.out.println("A 构造器");
	}

	@Async
	public void async() {
		System.out.println("测试执行异步方法开始");
		try {
			TimeUnit.SECONDS.sleep(20);
		} catch (InterruptedException e) {
			System.out.println(e);
		}
		System.out.println("测试异步方法结束");
	}
}
