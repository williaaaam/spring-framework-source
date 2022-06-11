package com.xjz.springframework.bc;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

/**
 * @author Williami
 * @description
 * @date 2022/3/15
 */
@Component("D")
public class D {

	static {
		System.out.println("D static");
	}

	public D() {
		System.out.println("D constructor");
	}
}
