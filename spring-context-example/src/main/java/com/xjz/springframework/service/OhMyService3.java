package com.xjz.springframework.service;

import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 *
 * @author Williami
 * @description
 * @date 2021/7/21
 */
@Component
@Lazy // 延迟初始化 如果其他非懒初始化Bean显示注入OhMyService3，则OhMyService3也会在容器初始化时进行实例化
//@DependsOn("ohMyService2")
public class OhMyService3 {

	{
		System.out.println("invoke OhMyService3 构造器");
	}

}
