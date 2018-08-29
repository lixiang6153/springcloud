package com.easystudy.filter;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

public class ErrorFilter extends ZuulFilter {
	private static Logger log = LoggerFactory.getLogger(AccessFilter.class);
	@Override
	public boolean shouldFilter() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		RequestContext context = RequestContext.getCurrentContext();
		Throwable throwable = context.getThrowable();
		
		log.error("[ErrorFilter] error message: {}", throwable.getCause().getMessage());
		context.set("error.status_code", HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		context.set("error.exception", throwable.getCause());
		
		return null;
	}

	@Override
	public String filterType() {
		// TODO Auto-generated method stub
		return FilterConstants.ERROR_TYPE;
	}

	@Override
	public int filterOrder() {
		// TODO Auto-generated method stub
		return 10;
	}

}
