package cn.itsoruce.springboot.web.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.itsoruce.springboot.domain.Employee;

/**
 * web的支持其实就是原来springMVC，只是简化了配置。
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/webTest")
public class WebTestController {

	//跳转页面
	@RequestMapping("/jsp")
	public String jsp(Model model) {
		model.addAttribute("hello", "hello springboot!");
		
		return "/webTest";
	}
	
	//返回json-字符串，对象，对象集合
	@RequestMapping("/json1")
	@ResponseBody
	public String json1() {
		return  "yhptest";
	}
	//返回json-字符串，对象，对象集合
	@RequestMapping("/json2")
	@ResponseBody
	public Employee json2() {
		return  new Employee(1L,"zs",new Date());
	}
	//返回json-字符串，对象，对象集合
	@RequestMapping("/json3")
	@ResponseBody
	public List<Employee> json3() {
		return  Arrays.asList(new Employee(1L,"zs",new Date()),
				new Employee(2L,"ls",new Date()));
	}
}
