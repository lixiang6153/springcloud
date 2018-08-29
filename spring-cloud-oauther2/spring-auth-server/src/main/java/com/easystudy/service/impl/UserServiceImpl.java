package com.easystudy.service.impl;
import org.springframework.stereotype.Service;

import com.easystudy.error.ErrorCode;
import com.easystudy.error.ReturnValue;
import com.easystudy.model.User;
import com.easystudy.service.UserService;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j			// 不想每次都写private final Logger logger = LoggerFactory.getLogger(XXX.class);
				// @Slf4j注入后找不到变量log，那就给IDE安装lombok插件
public class UserServiceImpl implements UserService {
	@Override
    public ReturnValue<User> findByUsername(String username) {
        log.info("调用{}失败","findByUsername");
        return new ReturnValue<User>(ErrorCode.ERROR_SERVER_ERROR, "调用findByUsername接口失败");
    }
}
