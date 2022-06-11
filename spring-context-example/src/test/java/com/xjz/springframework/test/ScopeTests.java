package com.xjz.springframework.test;

import com.xjz.springframework.config.ohnyproxy.ProxyBeanMethodConfig;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.concurrent.TimeUnit;

/**
 * @author william
 * @title
 * @desc
 * @date 2022/5/2
 **/
public class ScopeTests {

	@Test
	public void testScope() throws InterruptedException {
		AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();
		annotationConfigApplicationContext.register(ProxyBeanMethodConfig.class);
		// 启动Spring上下文
		annotationConfigApplicationContext.refresh();
		System.out.println("关闭Spring应用上下文");
		// 关闭Spring应用上下文
		annotationConfigApplicationContext.close();
		// 强制GC
		System.gc();
		TimeUnit.SECONDS.sleep(5);

	}
}
