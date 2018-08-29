package com.easystudy.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.easystudy.error.ErrorCode;
import com.easystudy.error.ReturnValue;
import com.easystudy.model.Role;
import com.easystudy.service.RoleService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j			// 不想每次都写private final Logger logger = LoggerFactory.getLogger(XXX.class);
				// @Slf4j注入后找不到变量log，那就给IDE安装lombok插件
public class RoleServiceImpl implements RoleService {
	@Override
    public ReturnValue<List<Role>> getRolesByUserId(Long userId) {
        log.info("调用{}失败","getRoleByUserId");
        return new ReturnValue<List<Role>>(ErrorCode.ERROR_SERVER_ERROR,"调用getRoleByUserId失败");
    }
}
