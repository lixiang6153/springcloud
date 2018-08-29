package com.easystudy.service;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.easystudy.error.ReturnValue;
import com.easystudy.model.RoleRights;
import com.easystudy.service.impl.RoleRightsServiceImpl;

@FeignClient(name = "user-service",fallback = RoleRightsServiceImpl.class)
public interface RoleRightsService {
    @GetMapping("roleRights/findByRoleId/{roleId}")
    public ReturnValue<List<RoleRights>> getRoleRights(@PathVariable("roleId") Long roleId);
}
