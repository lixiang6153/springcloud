package com.donwait.filter;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;


public class CrossFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	 public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletResponse response= (HttpServletResponse) servletResponse;
		// String origin= servletRequest.getRemoteHost()+":"+servletRequest.getRemotePort();
		// 允许来自所有域的跨域请求访问,如果指定特定域如http://my.domain.cn:8080
		// 服务器端 Access-Control-Allow-Credentials = true时，参数Access-Control-Allow-Origin 的值不能为 '*' 
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Headers", "Authentication");
		/* response.setHeader("Access-Control-Allow-Methods","POST,GET,OPTIONS,DELETE");
		response.setHeader("Access-Control-Max-Age","3600");
		// 通过设置 withCredentials: true ，发送Ajax时，Request header中便会带上 Cookie 信息,服务端对应允许如下
		response.setHeader("Access-Control-Allow-Credentials","true");*/
		filterChain.doFilter(servletRequest,servletResponse);
   }

	@Override
	public void init(FilterConfig config) throws ServletException {
	}

}
