package com.easystudy.service.impl;

import org.springframework.stereotype.Service;
import com.easystudy.service.HelloService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j			// 不想每次都写private final Logger logger = LoggerFactory.getLogger(XXX.class);
				// @Slf4j注入后找不到变量log，那就给IDE安装lombok插件
public class HelloServiceImpl implements HelloService {
	@Override
	public String hello() {
		log.info("调用{}失败","hello");
		return "调用hello失败";
	}
}
