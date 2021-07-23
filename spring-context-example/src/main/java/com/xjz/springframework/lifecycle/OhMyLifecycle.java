package com.xjz.springframework.lifecycle;

import org.springframework.context.Lifecycle;
import org.springframework.stereotype.Component;

/**
 * @author Williami
 * @description
 * @date 2021/7/22
 */
@Component
public class OhMyLifecycle implements Lifecycle {


	boolean isRunning = false;

	@Override
	public void start() {
		isRunning = true;
		System.out.println("OhMyLifecycle start");
	}

	/**
	 * 如果start()没有将isRunning置为true,则stop不会执行
	 * <p>
	 * 当我们实现Lifecycle时必须显示调用容器start()或者stop方法
	 * <p>
	 * stop方法不一定能保证在我们之前介绍的销毁方法之前执行
	 */
	@Override
	public void stop() {
		isRunning = false;
		System.out.println("OhMyLifecycle stop");
	}

	@Override
	public boolean isRunning() {
		return isRunning;
	}
}
