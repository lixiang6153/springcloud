package cn.itsoruce.springboot.web.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.itsoruce.springboot.domian.User;
import cn.itsoruce.springboot.service.IUserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private IUserService userService;

	@RequestMapping("/add")
	public Map<String, Object> add(User user) {
		Map<String, Object> result = new HashMap<>();
		try {
			userService.add(user);
			result.put("success", true);
			result.put("message", "操作成功！");
			return result;
		} catch (Exception e) {

			e.printStackTrace();
			result.put("success", false);
			result.put("message", "操作失败！");
			return result;
		}
	}
}
