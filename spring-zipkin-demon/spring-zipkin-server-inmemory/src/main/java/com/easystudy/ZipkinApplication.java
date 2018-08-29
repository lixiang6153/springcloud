package com.easystudy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import zipkin.server.internal.EnableZipkinServer;


/**
 * eureka：   http://localhost:8671
 * zipkin：   http://localhost:8672
 * service： http://localhost:8673
 * consumer：http://localhost:8674
 * 
 * 启动：
 * 1、eureka--》service--》zipkin--》consumer
 * 2、访问consumer（通过feign访问service一个链路被zipkin监控。一条链路可以无限制长度，也即是可以多层嵌套）
 * 3、使用http://localhost:8672访问zipkin查看链路状况（生产环境不要使用mysql数据库存储，量大效率低下，建议使用sleuth）
 * @author Administrator
 * 
 * 经过测试得出结论：
 * （1）zipkin服务什么时候启动都没关系，配置了zipkin服务的终端在zipkin启动后会自动连接上去
 * （2）zipkin并不是每次请求接口都是会记录请求耗时的，是采样提取的，可以通过spring.sleuth.sampler.probability=0.5 指定抽样比例，配置才客户端，而不是服务器
 * （3）要记录客户端的接口请求，客户端依赖zipkin的客户端包即可，接口的服务提供这不需要依赖zipkin相关包，也就是说使用zipkin记录的终端才需要对应依赖。
 *
 */
@SpringBootApplication
@EnableZipkinServer
public class ZipkinApplication {
	public static void main(String[] args) {
        SpringApplication.run(ZipkinApplication.class, args);
    }
}
