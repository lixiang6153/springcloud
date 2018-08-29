package com.donwait.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.StrutsStatics;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.donwait.action.BaseAction;
import com.donwait.error.ErrorCode;
import com.donwait.util.Constant;
import com.donwait.util.ReturnValue;
import com.donwait.util.ServletContextUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class TokenAndSessionInterceptor extends AbstractInterceptor{
	private static final long serialVersionUID = 1L;
	@SuppressWarnings("unused")
	private static ApplicationContext context;
	static{
		context=new ClassPathXmlApplicationContext("classpath:applicationContext.xml");

	}
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		// 只拦截action请求，不对资源进行拦截
		ActionContext actioncontext = invocation.getInvocationContext();
		BaseAction action = (BaseAction) invocation.getAction();

		HttpServletRequest  request = (HttpServletRequest) actioncontext.get(StrutsStatics.HTTP_REQUEST);  
		String requestURI = request.getRequestURI();
		//运行状态检测
		boolean isRun = (boolean) ServletContextUtil.getServletContext().getAttribute(Constant.SYS_RUN);
		if(!isRun && !requestURI.contains("/api/user/close")){
			action.setAjaxResponse(new ReturnValue(ErrorCode.ERROR_SERVER_ERROR,"系统即将关闭，拒绝服务"));
			return "success";
		}
		return invocation.invoke();
	}
}
