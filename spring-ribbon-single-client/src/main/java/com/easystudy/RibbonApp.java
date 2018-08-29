package com.easystudy;

import java.net.URI;

import com.netflix.client.ClientFactory;
import com.netflix.client.http.HttpRequest;
import com.netflix.client.http.HttpResponse;
import com.netflix.config.ConfigurationManager;
import com.netflix.loadbalancer.RoundRobinRule;
import com.netflix.niws.client.http.RestClient;

@SuppressWarnings("deprecation")
public class RibbonApp {

	public static void main(String[] args) throws Exception{
		// 载入配置文件
		ConfigurationManager.loadPropertiesFromResources("sample-client.properties"); 
		// 获取本客户端要负载的所有服务器列表
	    System.out.println(ConfigurationManager.getConfigInstance().getProperty("sample-client.ribbon.listOfServers")); 
	    
	    // 修改负载均衡规则--默认是轮训
	    // 1、RoundRobinRule	这条规则简单地通过循环法选择服务器。它通常用作默认规则
	    // 2、BestAvailableRule	选择一个最小的并发请求的server
	    // 3、BestAvailableRule	选择一个最小的并发请求的server
	    // 4、AvailabilityFilteringRule	过滤掉那些因为一直连接失败的被标记为circuit tripped的后端server，并过滤掉那些高并发的的后端服务
	    // 5、WeightedResponseTimeRule	根据响应时间分配一个weight，响应时间越长，weight越小，被选中的可能性越低。
	    // 6、RetryRule	对选定的负载均衡策略机上重试机制。
	    // 7、RandomRule	随机选择一个server
	    // 8、ZoneAvoidanceRule	复合判断server所在区域的性能和server的可用性选择server
	    ConfigurationManager.getConfigInstance().setProperty(
                "sample-client.ribbon.NFLoadBalancerRuleClassName", RoundRobinRule.class.getName());
	    System.out.println("Rule:" + ConfigurationManager.getConfigInstance().getProperty("sample-client.ribbon.NFLoadBalancerRuleClassName"));
	    
	    // 使用自定义规则选择
	    //ConfigurationManager.getConfigInstance().setProperty(
        //        "my-client.ribbon.NFLoadBalancerRuleClassName", Myrule.class.getName()); //自定义负载均衡规则
	    
	    // 创建新的请求负载
	    RestClient client = (RestClient)ClientFactory.getNamedClient("sample-client"); 
	    HttpRequest request = HttpRequest.newBuilder().uri(new URI("/hello")).build(); 
	    
	    // 借助ribbon封装的 httpClient来发送请求（访问站点首页），循环4次是希望打印出负载均衡的效果-一次请求负载4次，测试打印用
	    for(int i = 0; i < 4; i ++) { 
	      HttpResponse response = client.executeWithLoadBalancer(request); 
	      System.out.println("负载路径:" + response.getRequestedURI() + " 响应状态:" + response.getStatus()); 
	    }
	    
	    /*
	    ConfigurationManager.getConfigInstance().setProperty(
                "my-client.ribbon.listOfServers", "localhost:1001,localhost:1002");  //设置服务列表
        ConfigurationManager.getConfigInstance().setProperty(
                "my-client.ribbon.NFLoadBalancerRuleClassName", AvailabilityFilteringRule.class.getName()); //设置负载均衡规则

        RestClient client = (RestClient) ClientFactory.getNamedClient("my-client");
        HttpRequest request = HttpRequest.newBuilder().uri("/hello").build();
        for(int i = 0; i < 10; i++) {
            HttpResponse response = client.executeWithLoadBalancer(request);
            String json = response.getEntity(String.class);
            System.out.println(json);
        }
	    */
	}

}
