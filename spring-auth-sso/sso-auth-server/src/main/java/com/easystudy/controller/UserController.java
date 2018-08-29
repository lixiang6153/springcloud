package com.easystudy.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
	
	@RequestMapping("/user/me")
    public Principal user(Principal principal) {
		System.out.println("认证服务提供用户信息：" + principal);
        return principal;
    }
}
