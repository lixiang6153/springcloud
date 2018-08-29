package com.easystudy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Api(value = "API - test controller", description = "测试控制器详细接口")
@RestController
public class ServiceInstanceRestController {
	@Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping("/service-instances/{applicationName}")
    public List<ServiceInstance> serviceInstancesByApplicationName(
            @PathVariable String applicationName) {
        return this.discoveryClient.getInstances(applicationName);
    }

    // 使用该注解描述接口方法信息
    @ApiOperation(value="sayhello", notes="测试hello接口")
    public String sayhello() {
        return "hello";
    }
    
    // 使用该注解描述接口方法信息
    @ApiOperation(value="测试方法hello", notes="根据id获取项目组Sonar对应的Url信息")
    // 使用该注解描述方法参数信息，此处需要注意的是paramType参数，需要配置成path，否则在UI中访问接口方法时，会报错
    @ApiImplicitParams({
            			@ApiImplicitParam(name = "id", value = "测试id", required = true, dataType = "Long", paramType="path")
    					})
    @RequestMapping("/hello2")
    public String sayhello2(@RequestParam(value="id", required=true, defaultValue="001") String id) {
        return "hello";
    }
    
    /**
     * 获取服务列表
     * @GetMapping是一个组合注解，
     * 等同于@RequestMapping(value="/getService",method = RequestMethod.GET)
     * 同理PostMapping也是一个组合注解，是@RequestMapping(method = RequestMethod.POST)的缩写
     * @return
     */
    @RequestMapping("/getService")
    public String getService() {
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            List<ServiceInstance> serviceInstances = discoveryClient.getInstances(service);
            for (ServiceInstance instance : serviceInstances) {
                System.out.println("instance:" + instance.getServiceId());
            }
        }
        return "success";
    }
    
    /**
     * 获取元数据
     * @return
     */
    @GetMapping("/metedata")
    public String getMetedata(){
        System.out.println("获取元数据");
        List<ServiceInstance> serviceInstances = discoveryClient.getInstances("first-service-provider");

        for (ServiceInstance serviceInstance : serviceInstances) {
            String serverid = serviceInstance.getMetadata().get("serverid");
            System.out.println(serviceInstance.getPort() + "元数据："+ serverid);
        }

        return "";
    }
}
