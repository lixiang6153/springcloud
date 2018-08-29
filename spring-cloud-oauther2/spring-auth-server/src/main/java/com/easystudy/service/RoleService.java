package com.easystudy.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.easystudy.error.ReturnValue;
import com.easystudy.model.Role;
import com.easystudy.service.impl.RoleServiceImpl;

/**
 * 访问eureka中的user-service远程服务
 * @author Administrator
 *
 */
@FeignClient(name = "user-service",fallback = RoleServiceImpl.class)
public interface RoleService {
    @GetMapping("role/findRolesByUserId/{userId}")
    public ReturnValue<List<Role>> getRolesByUserId(@PathVariable("userId") Long userId);
}
