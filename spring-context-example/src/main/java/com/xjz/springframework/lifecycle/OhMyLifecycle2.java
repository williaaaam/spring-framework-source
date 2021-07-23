package com.xjz.springframework.lifecycle;

import org.springframework.context.Lifecycle;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

/**
 *
 * @author Williami
 * @description
 * @date 2021/7/22
 */
@Component
@DependsOn("ohMyLifecycle") // 依赖于ohMyLifecycle，则start stop执行顺序如下：
/**
 * OhMyLifecycle start
 * OhMyLifecycle2 start
 *
 * 阶段越往后，stop越先执行
 * OhMyLifecycle2 stop
 * OhMyLifecycle stop
 *
 * 可以看出先执行被依赖项的start方法，再执行自己的start和stop，最后执行被依赖项的stop方法
 */
public class OhMyLifecycle2 implements Lifecycle {


	boolean isRunning = false;

	@Override
	public void start() {
		isRunning = true;
		System.out.println("OhMyLifecycle2 start");
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
		System.out.println("OhMyLifecycle2 stop");
	}

	@Override
	public boolean isRunning() {
		return isRunning;
	}
}
