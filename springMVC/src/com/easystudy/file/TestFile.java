package com.easystudy.file;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class TestFile {
	
	/**
	 * 文件上传：Spring通过接口MultipartResolver的实现类CommonsMultipartResolver（struts2的commons-fileupload组件（必须添加对应jar包以及依赖jar包commons-ioxx.jar）
	 * SpringMVC默认没有装配MultipartResolver，因此默认情况下不能处理文件上传工作，如果想使用
	 * Spring的文件上传功能，需在上下文中配置MultipartResolver
	 * 在对应的请求方法上使用MultipartFile参数
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/testFileUpload")
	public String upload(@RequestParam("desc") String desc, @RequestParam("file") MultipartFile file) throws IOException{
		System.out.println("desc:" + desc);
		System.out.println("OriginalFileName" + file.getOriginalFilename());
		System.out.println("InputStream" + file.getInputStream());
		return "success";
	}
}
