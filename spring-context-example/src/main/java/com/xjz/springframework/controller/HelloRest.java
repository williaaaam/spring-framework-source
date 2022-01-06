package com.xjz.springframework.controller;

import com.xjz.springframework.service.PrintServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@RestController
public class HelloRest {

    @Autowired
    private PrintServer printServer;

    @GetMapping(path = "hello", produces="text/html;charset=UTF-8")
    public String sayHello(HttpServletRequest request) {
        printServer.print();
        return "hello, " + request.getParameter("name");
    }


    @GetMapping({"/", ""})
    public String index() {
        return UUID.randomUUID().toString();
    }
}