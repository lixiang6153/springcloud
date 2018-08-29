package com.easystudy.handlers;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/springMVC")
/**
 * 不仅可以通过属性名指定需要放到回话中的属性外(value)，还可以通过属性模型的对象类型指定那些类型属性需要放到回话中（types）
 * 这里可以是student和school对象可以放入，其他值为字符串类型对象也可以放入
 * 注意：该注解只能放在类上面不能放在方法上面
 */
@SessionAttributes(value={"student", "school"}, types={String.class})
@Controller
public class SpringMvcTest {
	private final String SUCCESS = "success";

	@RequestMapping("/helloeworld")
	public String testRequestMapping(){
		System.out.println("testRequestMapping");
		return SUCCESS;
	}
	
	@RequestMapping(value="/testMethod",method=RequestMethod.POST)
	public String testMethod(){
		System.out.println("testMethod");
		return SUCCESS;
	}
	
	//支持通配符：@RequestMapping(value="/testAntPath/*/abc")
	@RequestMapping(value="/testParamsAndHeaders", params={"name", "age != 5"})
	public String testParamsAndHeaders(){
		System.out.println("testParamsAndHeaders");
		
		return SUCCESS;
		
	}
	
	// 请求头匹配
	@RequestMapping(value="testRequestHeaders", headers={"Accept-Language=zh-CN;q=0"})
	public String testRequestHeaders(){
		return SUCCESS;
	}
	
	// ant风格的通配符(?:一个字符   *：任意字符    **：多层路径
	// /user/*/createUser 匹配 /user/aaa/createUser
	// /user/bbb/createUser 匹配具体的URL
	// /user/**/createUser匹配/user/createUser或/user/aaa/createUser或/user/aaa/bbb/createUser
	// /user/createUser?? 匹配/user/createUserXX
	@RequestMapping("/testWild/*/abc")
	public String testWild(){
		System.out.println("testWild");
		return SUCCESS;
	}
	
	@RequestMapping("/testPathVariable/{id}")
	public String testPathVaraiable(@PathVariable("id") Integer id){
		System.out.println("testPathVaraiable:" + id);
		return SUCCESS;
	}
	
	@RequestMapping(value="/testRest/{id}", method=RequestMethod.DELETE)
	@ResponseBody
	public String testRest(@PathVariable Integer id){
		System.out.println("DELETE");
		return SUCCESS;
	}
	
	// 获取请求头,@RequestHeader参数指定对应的参数名
	@RequestMapping("requestHeader")
	public String testRequestHeader(@RequestHeader("custom") String value){
		System.out.println("自定义头："+value);
		return SUCCESS;
	}
	
	// 获取请求参数
	@RequestMapping("requestParam")
	public String testRequestParam(@RequestParam(value="id", required=true, defaultValue="001") String id, 
			@RequestParam(value="name", required=false) String name){
		System.out.println("id:" + id + " name:" + name);
		return SUCCESS;
	}
	
	@RequestMapping("requestParam2")
	public String testRequestParam2(Student student){
		System.out.println(student);
		return SUCCESS;
	}
	
	// 传递
	@RequestMapping("requestParam3")
	public String testRequestParam3(List<Student> students){
		System.out.println("student:" + students );
		return SUCCESS;
	}
	
	// 获取请求cookie值
	@RequestMapping("testCookieValue")
	public String testCookieValue(@CookieValue(value="JSESSIONID", required=true, defaultValue="") String sessionId){
		System.out.println("testCookieValue:" + sessionId);
		return SUCCESS;
	}
	
	// 传递原生的HttpServletRequest和HttpServletResponse
	@RequestMapping("testServletAPI")
	public String testServletAPI(HttpServletRequest request, HttpServletResponse response){
		System.out.println("request:" + request + " response:"+response);
		return SUCCESS;
	}
	
	
	/**
	 * springMVC输出模型数据的四种方法：
	 * 1、返回ModalAndView，通过该对象添加模型数据
	 * 2、Map及Model：入参为org.springframework.ui.Model、ModelMap或java.util.Map,方法返回时Map中数据会自动添加到数据模型中
	 * 3、@SessionAttributes将模型中的某个属性值暂存到HttpSession中
	 * 4、@ModelAttribute：方法入参标注该注解后，入参会被放到数据模型中
	 */
	// 方法一：返回ModelAndView，参数为转发的视图名
	// springMVC吧ModelAndView中数据一个个放到request域（request请求域）中
	@RequestMapping("testModelAndView")
	public ModelAndView testModelAndView(){
		String viewName = SUCCESS;
		ModelAndView mv = new ModelAndView(viewName);
		mv.addObject("time", new Date());
		return mv;
	}
	
	// 方法二：传入Map作为入参，SpringMVC会将所有放入Map的属性存到数据模型中
	@RequestMapping("testMap")
	public String testMap(Map<String, String> map){
		System.out.println("testMap");
		map.put("name", "lixx");
		map.put("age", "30");
		map.put("sex", "female");
		return SUCCESS;
	}
	
	// 方法3：传入Model作为入参，SpringMVC会将所有放入Model的属性存到数据模型中
	@RequestMapping("testModel")
	public String testModel(Model model){
		System.out.println("testMap");
		model.addAttribute("name", "lixx");
		model.addAttribute("age", "30");
		model.addAttribute("sex", "female");
		return SUCCESS;
	}
	
	// 方法4：将sessionAttributes标注到类的注解中
	// 传入Model或Map作为入参，将属性存放到Map的同时存放到Session中
	@RequestMapping("testSessionAttributes")
	public String testSessionAttributes(Map<String, Object> map){
		System.out.println("testSessionAttributes");
		Student student = new Student("001", "lixx");
		map.put("student", student);
		
		School school = new School("中国石油大学", "华东");
		map.put("school", school);
		
		map.put("hh", "types is String class");// type类型也可以放入
		
		return SUCCESS;
	}
	
	// 请求含有请求参数：id
	// @ModelAttribute标记的方法会在每一个目标方法执行之前被SpringMVC调用
	// 常用于某些对象部分属性可以修改，部分属性不可以修改（从数据库查询）情况下
	@ModelAttribute
	public void getStudent(@RequestParam(value="id", required=false) String id,
			Map<String, Object> map){
		System.out.println("查询数据库...");
		if(id != null){
			Student student = new Student(id, "test", "cls001", "test classes");
			map.put("student", student);
		}		
	}
	
	// 方法五：ModelAttributes 
	// 被ModelAttributes标注的方法在请求前都会被调用，查询的参数放到request请求域
	// 如果被请求的url方法入参与map的key值对应，这默认被取出后再用请求参数修改作为最新传入参数值
	// SpringMVC确定目标方法PoJo类型入参的过程：
	// 1、执行ModelAttributes方法，从数据库中取出数据，吧对象放入到Map请求域中，key为student
	// 2、SpringMVC从Map中取出Student对象，并把表单的请求参数赋值给Student对象的对应属性
	// 3、SpringMVC把上述对象传入到目标方法的参数
	// 注意：在ModelAttributes修饰的方法中，放入到Map时的键需要和目标方法入参的第一个字母的小写字符串一致，如这里map的key为student，入参名也是student
	// 若implicitModel中不存在key对应对象，这检查当前Handler是否使用SessionAttributes注解修饰
	// 1、如果使用了该注解且SessionAttributes注解的value属性值中包含了key，这回从HttpSession中获取key对应的值作为入参传入，否则跑出异常
	// 2、如果Handler没有使用注解SessionAttributes，则通过反射机制来创建Pojo类型的对象作为入参传入
	// 3、Spring会把key和value保存的implicitModel中，进而会保存到request中
	@RequestMapping("/testModelAttributes")
	public String testModelAttributes(Student student){
		System.out.println("修改：" + student);		
		return SUCCESS;
	}
	
	// 注意：如果键名字与传入参数不一致的情况下，为了可以使用ModelAttribute标注的方法中的键对应的值可以使用ModelAttribute注解标注入参！
	// 如Map中存放的是student，这里的参数为abc,不一致！或者说map中存放abc，这里参数为student，都可以使用@ModelAttribute标注对应需要对应的key名称
	@RequestMapping("/testModelAttributes2")
	public String testModelAttributes2(@ModelAttribute(value="student") Student abc){
		System.out.println("修改：" + abc);		
		return SUCCESS;
	}
	
	//
	@RequestMapping("testHelloView")
	public String testHelloView(){
		System.out.println("视图转发到自定义视图");
		return "helloView"; // 类名第一个字母小写
	}
	
	// 重定向
	// 如果返回字符串中含有redirect: 或 forward: 前缀这springmvc会重定向或转发到对应页面
	@RequestMapping("testRedirect")
	public String testRedirect(){
		System.out.println("testRedirect");
		return "redirect:/index.jsp";	// 重定向到根目录下的index.jsp
		//return "redirect:/url或handler";	//可转向或重定向到对应的requestMapping
	}
	
	@RequestMapping("testForward")
	public String testForword(){
		System.out.println("testForward");
		return "forward:/index.jsp";	// 转发到根目录下的index.jsp
	}
}
