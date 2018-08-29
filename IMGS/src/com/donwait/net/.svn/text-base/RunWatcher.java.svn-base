package com.donwait.net;

import org.apache.log4j.Logger;

import com.donwait.util.Constant;
import com.donwait.util.RedisUtil;
import com.donwait.util.ServletContextUtil;

/**
 * 对redis中的sys_run读取，判断系统是否进入重启状态
 * @author Administrator
 *
 */
public class RunWatcher implements Runnable{
	public static Logger logger = Logger.getLogger(RunWatcher.class);
	@Override
	public void run() {
		logger.info("System run wathcer start ...");
		while(true){
			try {
				if(RedisUtil.exists(Constant.SYS_RUN)){//如果存在,则进入关机就绪
					ServletContextUtil.getServletContext().setAttribute(Constant.SYS_RUN, false);
				}else{//如果不存在，则正常服务
					ServletContextUtil.getServletContext().setAttribute(Constant.SYS_RUN, true);
				}
				Thread.sleep(10000);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(e.getMessage());
			}
		}
		
	}

}
