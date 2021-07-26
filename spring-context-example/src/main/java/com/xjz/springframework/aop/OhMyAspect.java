package com.xjz.springframework.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author william
 * @title
 * @desc
 * @date 2021/7/25
 **/
@Component // 如果在AppConfig中通过@Bean的方式创建Bean,此处就不再需要@Component注解了
@Aspect
public class OhMyAspect implements Ordered {

	/**
	 * 申明切点
	 * execution表达式的语法：
	 * execution(modifiers-pattern? ret-type-pattern declaring-type-pattern?name-pattern(param-pattern) throws-pattern?)
	 * 问号表示当前非必填项：
	 *
	 * modifiers-pattern（非必填）：方法的可见性，如public，protected；
	 * ret-type-pattern（必填）：方法的返回值类型，如int，void等；
	 * declaring-type-pattern（非必填）：方法所在类的全路径名，如com.spring.Aspect；
	 * name-pattern（必填）：方法名类型，如buisinessService()；
	 * param-pattern（必填）：方法的参数类型，如java.lang.String；
	 * throws-pattern（非必填）：方法抛出的异常类型，如java.lang.Exception；
	 * 原文链接：https://blog.csdn.net/qq_41907991/article/details/105500421
	 *
	 * 可以看出必填项：返回值、方法名和方法参数
	 *
	 */

	/**
	 * OhMyController包及其子包下的任意类
	 */
	@Pointcut("execution(public java.lang.String com.xjz.springframework.controller.OhMyController.eat(..))")
	private void executionOhMyController() {

	}

	@Pointcut("execution(public * *(..))")
	private void executionAllPublicMethodPointcut() {
	}


	@Pointcut("@annotation(com.xjz.springframework.annotation.OhMyAnnotation)")
	private void annotationPiintcut() {

	}

	/**
	 * 同时匹配两者
	 */
	@Pointcut("annotationPiintcut() && executionOhMyController()")
	private void annotationAndOhMyController() {

	}

	/**
	 * 满足其一即可
	 */
	@Pointcut("executionAllPublicMethodPointcut() || annotationPiintcut()")
	private void annotationPiintcutOrexecutionAllPublicMethodPointcut() {
	}

	/**
	 * 前置通知
	 * 如果before抛出异常，则业务代码不会执行
	 *
	 * <p>
	 * before抛出异常的话，目标方法和其他通知都不会执行
	 */
	@Before("executionOhMyController()")
	public void before(JoinPoint joinPoint) {
		/**
		 * 切面1 before joinPoint.getThis()获取代理对象= com.xjz.springframework.controller.OnMyController@467f77a5
		 * 切面1 before joinPoint.getTarget()获取目标对象= com.xjz.springframework.controller.OnMyController@467f77a5
		 * 切面1 before joinPoint.getSignature()获取目标方法签名= String com.xjz.springframework.controller.OnMyController.eat(String)
		 * 切面1 before joinPoint.getSignature()获取目标方法参数= [23333]
		 * 切面1 before
		 */

		System.out.println("切面1 before joinPoint.getThis()获取代理对象= " + joinPoint.getThis());
		System.out.println("切面1 before joinPoint.getTarget()获取目标对象= " + joinPoint.getTarget());
		System.out.println("切面1 before joinPoint.getSignature()获取目标方法签名= " + joinPoint.getSignature());
		/**
		 * 打印的是真是实际方法调用的参数值
		 */
		System.out.println("切面1 before joinPoint.getSignature()获取目标方法参数= " + Arrays.asList(joinPoint.getArgs()).toString());
		System.out.println("切面1 before");
		//throw new RuntimeException("Before 抛出运行时异常");

	}


	/**
	 * 无论什么情况下都会执行的方法
	 * 目的：资源释放
	 * 类似于Finally,不管代码有何异常都会自执行
	 */
	@After("executionOhMyController()")
	public void after() {
		System.out.println("切面1 after");
	}

	/**
	 * 后置通知
	 * 和before相对，在方法return后执行
	 * <p>
	 * 注解参数returing是将retVal绑定到切点上参数retVal上
	 */
	@AfterReturning(value = "executionOhMyController()", returning = "retVal")
	public void afterReturing(Object retVal) {
		System.out.println("切面1 afterReturing,返回值 = " + retVal);
	}

	@AfterThrowing(value = "executionOhMyController()", throwing = "e")
	public void afterThrowing(Throwable e) {
		System.out.println("切面1 afterThrowing" + e);
	}

	/**
	 * 可以包裹方法
	 * 可以根据一定的条件阻断业务模块的调用
	 * 可以更改目标方法返回值
	 * <p>
	 * 如果定义了环绕通知的方法，并且方法体没有自行目标方法，则其他通知和目标方法都不会执行
	 *
	 * @param joinPoint 可以调用业务员模块代码;ProceedingJoinPoint仅支持环绕通知
	 */
	//@Around("executionOhMyController()")
	public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

		System.out.println("切面1 环绕通知开始");
		// proceed(Object[] args) 可以改变当前执行方法的参数，然后用改变后的参数执行这个方法
		Object proceed = joinPoint.proceed(new String[]{"zhouxinjian"});
		System.out.println("切面1 环绕通知结束");
		//joinPoint.getThis();
		//joinPoint.getTarget();
		return "proceedingJoinPoint";
	}

	@Override
	public int getOrder() {
		return 10;
	}
}
