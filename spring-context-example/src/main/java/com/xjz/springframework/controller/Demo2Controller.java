package com.xjz.springframework.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 实现Handler有四种方式：
 * 1. @Controller注解
 * 2. implements HttpRequestHandler
 * 3. implements Controller
 * 4. extends HttpServlet(Spring WEB MVC 不再推荐我们使用原生的Servlet来处理请求，在DispatcherServlet.properties可以看出这一点；所以，需要手动注册SimpleServletHandlerAdapter 这个适配器)
 * @author william
 * @title
 * @desc
 * @date 2022/4/27
 **/
@Controller
public class Demo2Controller {

	public Demo2Controller(){
		System.out.println();
	}

	@RequestMapping("/controller/annotation")
	@ResponseBody
	public String order(HttpServletRequest request, HttpServletResponse response) {
		return "@Controller";
	}
}
