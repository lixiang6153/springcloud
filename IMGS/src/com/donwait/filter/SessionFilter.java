package com.donwait.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import com.donwait.constant.Constants;
import com.donwait.error.ErrorCode;

public class SessionFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}
	/**
	 * 该过滤拦截ajax失效
	 */
	// 防止未登录访问任意页面或在有效的时间内过期自动跳转到登录页面
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession();
      
        // 登陆url
        String url = httpRequest.getRequestURI();
        String path = url.substring(url.lastIndexOf("/"));
        
        String basePath = httpRequest.getScheme()+"://"+httpRequest.getServerName()+":"+httpRequest.getServerPort()+  httpRequest.getContextPath() +"/";
        
        // session过期或非法访问页面
        if (-1 == path.indexOf("/login.jsp") && 
        	-1 != path.indexOf(".jsp") &&
        	null == session.getAttribute(Constants.USER_SESSION)) 
        {
        	JSONObject jsonObject = new JSONObject();
			try {
				jsonObject.put("description", ErrorCode.ERROR_NOT_LOGIN.getDescription());
				jsonObject.put("error", ErrorCode.ERROR_NOT_LOGIN.getError());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			PrintWriter writer = response.getWriter();
			writer.print(jsonObject.toString());
			writer.flush();
			
			String loginUrl = basePath + "html/login.jsp";
        	httpResponse.sendRedirect(loginUrl);
			
			//request.setCharacterEncoding("UTF-8");
			//request.getRequestDispatcher("/DFS/html/login.jsp");
        	System.out.println("非法访问, 重定向登录");
        } 
        else 
        {
            chain.doFilter(request, response);
        }        
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
	}

}
