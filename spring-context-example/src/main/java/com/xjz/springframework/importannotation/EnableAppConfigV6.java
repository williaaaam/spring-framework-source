package com.xjz.springframework.importannotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author Williami
 * @description
 * @date 2021/8/24
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(OhMyDeferredImportSelector.class)
public @interface EnableAppConfigV6 {

	String name() default "";

}
