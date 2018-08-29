package cn.itsoruce.springboot.web.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import cn.itsoruce.springboot.domain.Employee;

/**
 * web的支持其实就是原来springMVC，只是简化了配置。
 * 
 * 一个Controller既要返回页面，又要返回Json，有如下两种方式：
 * 1)方式1-@Controller
 *  页面: 返回String
 *  json：@ResponseBody
 * 2)方式2-@RestController（建议使用）
 *  页面: ModelAndView
 *  json：什么都不加
 * @author Administrator
 *
 */
@RestController//@RestController=@Controller+@ResponseBody
@RequestMapping("/webTest2")
public class WebTestController2 {

	//跳转页面
	@RequestMapping("/jsp")
	public ModelAndView jsp(Model model) {
		model.addAttribute("hello", "hello springboot!");
		
		return new ModelAndView("webTest");
	}
	
	//返回json-字符串，对象，对象集合
	@RequestMapping("/json1")
	public String json1() {
		return  "yhptest";
	}
	//返回json-字符串，对象，对象集合
	@RequestMapping("/json2")
	public Employee json2() {
		return  new Employee(1L,"zs",new Date());
	}
	//返回json-字符串，对象，对象集合
	@RequestMapping("/json3")
	public List<Employee> json3() {
		return  Arrays.asList(new Employee(1L,"zs",new Date()),
				new Employee(2L,"ls",new Date()));
	}
}
