package com.easystudy.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.easystudy.error.ReturnValue;
import com.easystudy.model.Rights;
import com.easystudy.service.impl.RightsServiceImpl;

/**
 * 访问eureka中的user-service远程服务
 * @author Administrator
 *
 */
@FeignClient(name = "user-service",fallback = RightsServiceImpl.class)
public interface RightsService {
    @GetMapping("rights/findByRightId/{rightId}")
    public ReturnValue<Rights> findByRightId(@PathVariable("rightId") Long rightId);
}
