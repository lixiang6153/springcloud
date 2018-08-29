package com.easystudy.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.easystudy.error.ReturnValue;
import com.easystudy.model.User;
import com.easystudy.service.impl.UserServiceImpl;

/**
 * @FeignClient指定调用user-service服务的对应接口
 * @author Administrator
 */
@FeignClient(name = "user-service",fallback = UserServiceImpl.class)
public interface UserService {
    @GetMapping("user/findByUserName/{username}")
    public ReturnValue<User> findByUsername(@PathVariable("username") String username);
}