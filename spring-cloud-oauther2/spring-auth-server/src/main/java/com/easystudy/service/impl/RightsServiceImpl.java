package com.easystudy.service.impl;

import org.springframework.stereotype.Service;

import com.easystudy.error.ErrorCode;
import com.easystudy.error.ReturnValue;
import com.easystudy.model.Rights;
import com.easystudy.service.RightsService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j			// 不想每次都写private final Logger logger = LoggerFactory.getLogger(XXX.class);
				// @Slf4j注入后找不到变量log，那就给IDE安装lombok插件
public class RightsServiceImpl implements RightsService {
	@Override
	public ReturnValue<Rights> findByRightId(Long rightId) {
        log.info("调用{}失败","findByRightId");
        return new ReturnValue<Rights>(ErrorCode.ERROR_SERVER_ERROR,"调用findByRightId失败");
    }
}
