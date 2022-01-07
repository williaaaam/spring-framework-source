package com.xjz.springframework.controller;

import com.xjz.springframework.model.OrderQuery;
import com.xjz.springframework.service.PrintServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
public class HelloRest {

	@Autowired
	private PrintServer printServer;

	@Resource
	HttpServletRequest request;

	/**
	 * @param orderQuery 没有任何注解，默认采用ModelAttributeMethodProcessor参数解析器来解析请求
	 * @return
	 */
	@GetMapping(path = "hello", produces = "text/html;charset=UTF-8")
	public String sayHello(HttpServletRequest query, OrderQuery orderQuery) {
		//printServer.print();
		return "hello, " + orderQuery.hashCode() + " request = " + request.getClass().getName() + " 方法参数request=" + query.hashCode();
	}


	@GetMapping({"/", ""})
	public String index(String name) {
		return UUID.randomUUID().toString() + " " + name;
	}

}