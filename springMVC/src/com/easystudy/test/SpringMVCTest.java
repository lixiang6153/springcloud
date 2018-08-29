package com.easystudy.test;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.easystudy.handlers.Student;

@Controller
@RequestMapping("conversion")
public class SpringMVCTest {
	
	@RequestMapping(value="/testConversion", method=RequestMethod.POST)
	public String testConversion(@RequestParam(value="student", required=true) Student student){
		System.out.println("学生对象：" + student);
		return "success";
	}
	
	// bing数据失败情况
	@RequestMapping(value="/testConversion2", method=RequestMethod.POST)
	public String testConversion2(@RequestParam(value="student") Student student, BindingResult result){
		System.out.println("学生对象：" + student);
		if(result.getErrorCount() > 0){
			System.out.println("数据biding出错！");
			for(FieldError error : result.getFieldErrors()){
				System.out.println(error.getField() + ":" + error.getDefaultMessage());
			}
		}
		return "success";
	}
	
	// InitBinder可以对webDataBinder进行初始化[表单字段到javaBean字段的绑定]
	// @InitBinder标注方法不能有返回值
	/*关闭，以免影响赋值 
	@InitBinder
	public void initBinder(WebDataBinder bind){
		bind.setDisallowedFields("name");// 设置name不进行转化，表单含有的字段被忽略
	}
	*/
}
