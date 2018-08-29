package com.easystudy.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class SecondInterceptor implements HandlerInterceptor {

	/*
	 * 返回true和false
	 * 如果返回false后续的拦截器就不会被调用，且目标方法也不会被调用
	 * @see org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 * 可以考虑做权限、日志、事物等
	 */
	@Override
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2) throws Exception {
		System.out.println("SecondInterceptor[preHandle]");
		return true;
	}

	/**
	 * 调用目标方法之后，但在渲染视图之前
	 * 可以对请求域中的属性或视图做出修改。
	 */
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		System.out.println("SecondInterceptor[postHandle]");
	}
	
	/**
	 * 渲染视图之后被调用
	 * 释放资源
	 */
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		System.out.println("SecondInterceptor[afterCompletion]");
	}
}
