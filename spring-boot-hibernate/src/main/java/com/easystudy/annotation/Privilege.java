package com.easystudy.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 判断该请求是否要进行权限验证
 * @author Administrator
 *
 */

@Retention(RetentionPolicy.RUNTIME)

public @interface Privilege {
	public String value() default "true";
}
