package com.donwait.listener;

import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Timer;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.donwait.actionHandler.ImageActionHandler;
import com.donwait.log.ClearLogThread;
import com.donwait.log.ImgRecordHandler;
import com.donwait.model.User;
import com.donwait.net.RunWatcher;
import com.donwait.service.UserService;
import com.donwait.util.ConfigUtil;
import com.donwait.util.EncryptorUtil;
import com.donwait.util.PoolHelper;

@WebListener
public class StartupListener implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent arg0)  { 
	}
	public void contextInitialized(ServletContextEvent arg0)  { 
		System.out.println("------------------------开始初始化图片服务系统----------------------");
		// 获取应用程序
		ServletContext application = arg0.getServletContext();
		// 获取Spring上下文容器
		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(application);
		// 初始化配置
		startCleanThread(ctx);
		// 初始化消费线程
		PoolHelper.execute(new ImgRecordHandler(ctx));
		//系统重启安全开关，防止系统重启带来的数据库同步异常问题
		PoolHelper.execute(new RunWatcher());
		System.out.println("------------------------图片服务初始化结束----------------------");
	}
	
	private void startCleanThread(ApplicationContext ctx){
		// 读取配置文件
		Properties config = ConfigUtil.getProperties("db.properties");
		String enable = config.getProperty("clear.enable");
		int days = Integer.parseInt(config.getProperty("clear.days"));
		
		// 需要清理
		if(enable.trim().equalsIgnoreCase("true")){
			// 获取系统服务
			ImageActionHandler service = ctx.getBean(ImageActionHandler.class);
			// 每天点钟进行清理
			Calendar calendar = Calendar.getInstance();
		    calendar.set(Calendar.HOUR_OF_DAY, 1); 			
		    calendar.set(Calendar.MINUTE, 0);    		
		    calendar.set(Calendar.SECOND, 0);    
		    // 在1点进行清理并且24小时轮训清理（每天1点）
		    Date time = calendar.getTime(); 
		    // 1点后开机  
	        if (time.before(new Date())) {  
	            // 将发送时间设为明天1点  
	            calendar.add(Calendar.DATE, 1);  
	            //calendar.add(Calendar.SECOND, 10);  
	            time = calendar.getTime();  
	        }
	        
		    Timer timer = new Timer();
		    timer.scheduleAtFixedRate(new ClearLogThread(service, days) , time, 24* 60 * 60 * 1000);	
		}
	}
	
	@SuppressWarnings("unused")
	private void initUser(ApplicationContext ctx){
		UserService userService = ctx.getBean(UserService.class);
		if(null == userService)
			return;
		
		// 用户不存在则创建
		User admin = (User) ctx.getBean("admin");
		if(admin != null){
			User userTemp = userService.find(admin.getUserName());
			if(null == userTemp){
				admin.setPassword(EncryptorUtil.encryptPassword(admin.getPassword()));
				userService.add(admin);
			}
		}
	}
}
