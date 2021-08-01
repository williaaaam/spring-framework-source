package com.xjz.springframework.aop.v2;

import org.springframework.aop.support.DelegatingIntroductionInterceptor;

/**
 * @author william
 * @title
 * @desc
 * @date 2021/8/1
 **/
public class OhMyIntroductionAdvice extends DelegatingIntroductionInterceptor implements Runnable {

	private static final long serialVersionUID = 0L;

	@Override
	public void run() {
		System.out.println("Running !");
	}
}
