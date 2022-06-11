package com.xjz.springframework;

import com.xjz.springframework.config.AppConfigVX;
import com.xjz.springframework.config.RootConfig;
import com.xjz.springframework.config.WebConfig;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * @author Williami
 * @description
 * @date 2022/1/5
 */
public class App {

	/**
	 * 通过应用程序来启动项目，不会执行SpringServletContainerInitializer的onStartup方法，进而也不会调用WebApplicationInitializer#onStartUp()方法
	 *
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		Server server = new Server(8080);
		ServletContextHandler handler = new ServletContextHandler();

		// 服务器根目录，类似于tomcat部署的项目。 完整的访问路径为ip:port/contextPath/realRequestMapping
		//ip:port/项目路径/api请求路径
		handler.setContextPath("/");

		// Spring MVC容器
		AnnotationConfigWebApplicationContext applicationContext = new AnnotationConfigWebApplicationContext();
//		applicationContext.register(WebConfig.class);
//		applicationContext.register(RootConfig.class);
		applicationContext.register(AppConfigVX.class);
		applicationContext.refresh();

		//相当于web.xml中配置的ContextLoaderListener
		handler.addEventListener(new ContextLoaderListener(applicationContext));

		// 创建并注入DispatcherServlet Servlet 3.0 SPI
		// springmvc拦截规则 相当于web.xml中配置的DispatcherServlet
		handler.addServlet(new ServletHolder(new DispatcherServlet(applicationContext)), "/*");

		server.setHandler(handler);
		server.start();
		server.join();

	}
}
