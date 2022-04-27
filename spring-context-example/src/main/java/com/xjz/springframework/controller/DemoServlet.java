package com.xjz.springframework.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HttpServletBean;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Controller实现：继承Servlet接口 + xml配置文件:配置DemoController类与URL的对应关系
 *
 * @author william
 * @title
 * @desc
 * @date 2022/4/27
 **/
@Component("/controller/servlet")   // 注意此处需要以/开头，表示使用BeanNameURLHandlerMapping 类型处理
public class DemoServlet extends HttpServletBean {

	public static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.getWriter().write("extends HttpServlet");
	}

}
