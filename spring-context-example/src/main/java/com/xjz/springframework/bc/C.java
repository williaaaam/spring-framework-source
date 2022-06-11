package com.xjz.springframework.bc;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

/**
 * @author Williami
 * @description
 * @date 2022/3/15
 */
@Component("C")
@DependsOn(value = "D")
public class C {

	static {// clint
		System.out.println("C static");
	}

	public C() {
		System.out.println("C Constructor");
	}
}
