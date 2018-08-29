package com.easystudy.json;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.easystudy.handlers.Student;

@RequestMapping("/testJson")
@Controller
public class TestJson {
	
	/**方法一
	 * 使用jackson返回Json
	 * 		(1)加入JackSon的Reqired包：jackson-annotations-2.2.1.jar、jackson-core-2.2.1.jar、jackson-databind-2.2.1.jar
	 *      SpringMVC自动加入Jackson对HttpMessageConverter转换器实现类MappingJackson2HttpMessageConverter
	 *      （多类型的转换如StringHttpOutputMessageConverter）对返回类型转换为json字符串
	 *      (2)加入注解：ResponseBody
	 * 注意：
	 * 		经过测试springframework5.5.0与jackson2.2不兼容
	 * 		其他版本兼容：spring 4.2+jackson 2.7 版本冲突,不兼容jackson2.7
	 * 		spring 4.1.6+jackson 2.7版本冲突 了解到jackson2.7必须使用更高的spring版本,将jackson降为2.6
	 * 本人测试：
	 * 		spring 4.3.3与jackson2.8.2兼容（网上说要2.7还是2.7.5以上版本才兼容）
	 * 
	 * 步骤：1、加入jackson的reqired相关jar包
	 * 	   2、实现请求方法并直接返回java对象或对象数组
	 * 	   3、方法加入注解ResponseBody
	 * 原理：
	 * 			<------						 <------ HttpInputMessage  <------ 请求报文
	 * 
	 * SpringMVC		HttpMessageConverter
	 * 
	 * 			------>						 ------> HttpOutputMessage ------> 响应报文
	 * 
	 * DispatcherServlet默认装配RequestMappingHandlerAdapter，而RequestMappingHandlerAdapter默认装配
	 * 如下HttpMessagConverter：
	 * ByteArrayHttpMessageConverter
	 * StringHttpMessageConverter
	 * ResourceHttpMessageConverter
	 * SourceHttpMessageConverter
	 * AllEncompassingFormHttpMessageConverter
	 * Jaxb2RootElementHttpMessageConverter
	 * 加入jackson之后，springMVC会自动装载MappingJackson2HttpMessageConverter转换器
	 */
	@ResponseBody
	@RequestMapping("getStudent")
	public Student getStudent(){
		System.out.println("get Student method invoked!");
		return new Student("001", "lixiangxiang");
	}
	
	
	/**
	 * 方法二：  使用HttpMessageConverter将请求信息转化并绑定到处理方法的入参
	 * 		或将响应结果转化为对应类型的响应信息
	 * SpringMVC提供了两种途径：
	 * -- 使用@RequestBody对入参进行标注，使用ResponseBody对方法进行标注
	 * -- 使用@HttpEntity<T>对入参进行标注，使用ResponseEntity<T>作为返回值
	 * Spring首先根据请求头或响应头的Accept属性选择匹配的HttMessageConverter
	 * 进而根据参数类型或泛型类型的过滤得到匹配的HttpMessageConverter，若找不到
	 * 可用的HttpMessageConverter将报错
	 * @RequestBody或@ResponseBody不需要成对出现
	 * 
	 * 如：请求参数： @RequestBody String id，这会根据请求类型参数为String则Spring将入参转为String传入进来【StringHttpMessageConverter】
	 * 			 @RequestBody byte[] 会用到ByteHttpMessageConverter-->如上传文件
	 * 如： 返回ResponseEntity<byte[]>进行下载，请求为HttpEntity<byte[]>上传
	 */
	
	@RequestMapping("/testHttpMessageConverter")
	@ResponseBody  // 会将String返回类型使用StringHttpMessageConverter转换为String返回
	public String testHttpMessageConverter(@RequestBody String body){
		System.out.println(body);
		return "helloword!" + new Date();
	}
	
	@RequestMapping("/testResponseEntity")
	public ResponseEntity<byte[]> testResponseEntity(HttpSession session) throws IOException{
		ServletContext context = session.getServletContext();
		InputStream input = context.getResourceAsStream("/files/test.txt");
		
		byte[] body = new byte[input.available()];
		input.read(body);
		
		// Content-Disposition下载文件的一个标识字段【设置为弹出下载模式，inline为显示】
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Disposition", "attachment;filename=test.txt");

		HttpStatus statusCode = HttpStatus.OK;
		
		ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(body, headers, statusCode);
		return response;
	}
}