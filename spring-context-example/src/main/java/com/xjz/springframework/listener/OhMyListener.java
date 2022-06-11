package com.xjz.springframework.listener;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.*;
import org.springframework.stereotype.Component;

/**
 * @author william
 * @title
 * @desc
 * @date 2022/6/2
 **/
@Component("ohMyListener")
public class OhMyListener {

	public OhMyListener(){
		System.out.println("");
	}

	/**
	 * 监听器适配器ApplicationListenerMethodAdapter反射调用 hook方法
	 * @param applicationEvent
	 */
	@EventListener
	public void onApplicationEvent(ApplicationEvent applicationEvent) {
		if (applicationEvent instanceof ContextRefreshedEvent) {
			System.out.println("容器刷新完成");
			return;
		}

		if (applicationEvent instanceof ContextStartedEvent) {
			System.out.println("容器开始启动");
			return;
		}

		if (applicationEvent instanceof ContextStoppedEvent) {
			System.out.println("容器停止");
			return;
		}

		if (applicationEvent instanceof ContextClosedEvent) {
			System.out.println("容器关系");
			return;
		}
	}
}
