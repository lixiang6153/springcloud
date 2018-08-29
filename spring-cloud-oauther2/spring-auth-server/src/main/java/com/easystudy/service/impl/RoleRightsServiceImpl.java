package com.easystudy.service.impl;


import java.util.List;

import org.springframework.stereotype.Service;

import com.easystudy.error.ErrorCode;
import com.easystudy.error.ReturnValue;
import com.easystudy.model.RoleRights;
import com.easystudy.service.RoleRightsService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j			// 不想每次都写private final Logger logger = LoggerFactory.getLogger(XXX.class);
				// @Slf4j注入后找不到变量log，那就给IDE安装lombok插件
public class RoleRightsServiceImpl implements RoleRightsService {
	
	@Override
    public ReturnValue<List<RoleRights>> getRoleRights(Long roleId){
		log.error("远程调用获取角色权限失败:" + roleId);
        return new ReturnValue<List<RoleRights>>(ErrorCode.ERROR_SERVER_ERROR);
    }
}
