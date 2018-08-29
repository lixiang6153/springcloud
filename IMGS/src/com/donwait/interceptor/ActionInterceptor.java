package com.donwait.interceptor;

import org.springframework.stereotype.Component;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 对请求进行拦截，并记录日志
 * @author Administrator
 *
 */
@Component
public class ActionInterceptor extends AbstractInterceptor{
	private static final long serialVersionUID = 1L;
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		// 只拦截action请求，不对资源进行拦截
		//BaseAction action = (BaseAction) invocation.getAction();
		// 拦截sql打印到控制台
		//action.getLogger().beginLog();
		String invoke = invocation.invoke();
		//List<String> sqls = action.getLogger().endLog();
		
		//for (String sql : sqls) {
		//	System.out.println(sql);
		//}
		return invoke;		
	}
	/**
	 * 解析sql语句，取得表名
	 * @param sql
	 * @return
	 */
	@SuppressWarnings("unused")
	private String parseSql(String sql) {
		String[] texts = sql.split(" ");
		if(sql.startsWith("insert")){
			return texts[2];
		}else if(sql.startsWith("update")){
			return texts[1];
		}else if(sql.startsWith("delete")){
			return texts[2];
		}
		return null;
	}	
}
