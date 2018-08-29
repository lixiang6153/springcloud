package com.easystudy.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * 数据校验注解,输入数据校验的具体内容
 * @author 欧文发
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomValidator {

    String type() default "java.lang.String";//数据类型
    String fieldName() default "";//字段名称
    String[] fieldValue() default {}; //默认值
    String message() default "";//返回提示信息
}