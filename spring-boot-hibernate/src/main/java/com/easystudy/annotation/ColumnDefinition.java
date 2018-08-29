package com.easystudy.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 描述字段的意义
 * @author Administrator
 *
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ColumnDefinition {
	/**
	 * 字段的意义
	 * @return
	 */
	public String value() default "未知字段";
	/**
	 * 字段的枚举值
	 * @return
	 */
	public String[] enems() default {};

}
