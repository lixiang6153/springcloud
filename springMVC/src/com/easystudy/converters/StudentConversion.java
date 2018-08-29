package com.easystudy.converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.easystudy.handlers.Student;

// 从String转为Student
@Component
public class StudentConversion implements Converter<String, Student> {

	@Override
	public Student convert(String arg0) {
		if(null != arg0){
			String[] arr = arg0.split("-"); 
			if(arr.length == 4){
				Student student = new Student(arr[0], arr[1], arr[2], arr[3]);
				System.out.println("使用学生自定义转换器...");
				return student;
			}
		}
		return null;
	}
}
