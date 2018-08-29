package com.easystudy.health;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Status;
import org.springframework.stereotype.Component;

import com.netflix.appinfo.HealthCheckHandler;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.InstanceInfo.InstanceStatus;

/**
 * 健康处理器：HealthHandler
 * 使用Spring-boot的Actuator组件可以实现服务的健康检测。
 * 通过健康指示器和健康处理器来实现，健康指示器用于修改服务状态，
 * 健康处理器用于将服务状态通知回给服务器。由于，健康指示器只是
 * 修改了服务的状态，并没有将服务状态通知给服务器，所以这里需要再
 * 通过健康处理器（HealthHandler）将服务状态通知服务器
 * @author Administrator
 *
 */
@Component
public class HealthHandler implements HealthCheckHandler{

	// 健康指示器
	@Autowired
    HealthIndication healthIndic;
	
	@Override
	public InstanceStatus getStatus(InstanceStatus currentStatus) {
		if(healthIndic.health().getStatus().equals(Status.UP)){
            return InstanceInfo.InstanceStatus.UP;
        }else {
            return InstanceInfo.InstanceStatus.DOWN;
        }
	}

}
