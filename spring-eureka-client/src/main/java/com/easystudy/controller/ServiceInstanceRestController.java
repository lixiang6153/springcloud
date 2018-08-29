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

    @ApiOperation(value = "查询接口1", notes = "此接口描述xxxxxxxxxxxxx<br/>xxxxxxx<br>值得庆幸的是这儿支持html标签<hr/>", response = String.class)
    @RequestMapping("/service-instances/{applicationName}")
    public List<ServiceInstance> serviceInstancesByApplicationName(
            @PathVariable String applicationName) {
        return this.discoveryClient.getInstances(applicationName);
    }

    @ApiOperation(value = "查询接口2", notes = "此接口描述xxxxxxxxxxxxx<br/>xxxxxxx<br>值得庆幸的是这儿支持html标签<hr/>", response = String.class)
    @RequestMapping("/hello")
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
    @GetMapping("/get/all")
    public String sayhello2(@RequestParam(value="id", required=true, defaultValue="001") String id) {
        return "hello";
    }
}
