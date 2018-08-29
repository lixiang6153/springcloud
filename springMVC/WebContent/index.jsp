<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Insert title here</title>
		<script type="text/javascript" src="scripts/jquery.min.js"></script>
		<script type="text/javascript">
			$(function(){
				$("#testJson1").click(function (){
					var url = this.href;
					var args = {};
					$.post(url, args, function(data){
						var id = data.id;
						var name = data.name;
						alert(id + ":" + name);
					});
				});
			})
		</script>
	</head>
	<body>
		<a href="helloworld">Hello World！</a><br/>
		<a href="springMVC/helloeworld">springMVC/helloworld</a><br/>
		<a href="springMVC/testMethod">testMethod</a><br/>
		
		<form action="springMVC/testMethod" method="POST">
			<input type="submit" value="提交"></input>
		</form>
		
		<a href="springMVC/testParamsAndHeaders?name=lixx&age=10">testMethod</a><br/>
		<a href="springMVC/testPathVariable/102">testPathVariable</a><br/>
		
		<form action="springMVC/testRest/1" method="post">
			<input type="hidden" name="_method" value="DELETE"/>
			<input type="submit" value="删除"></input>
		</form>
		
		<form action="springMVC/testRest/1" method="post">
			<input type="hidden" name="_method" value="PUT"/>
			<input type="submit" value="更新"></input>
		</form>
		
		<br />
		<a href="springMVC/testWild/xxx/abc">testWild</a><br/>
		
		<br/>
		requestParam:
		<form action="springMVC/requestParam" method="post">
			id:<input type="text" name="id"/>
			name:<input type="text" name="name"/>
			<input type="submit" value="提交"></input>
		</form>
		
		<br/>
		<form action="springMVC/requestParam2" method="post">
			id:<input type="text" name="id"/>
			name:<input type="text" name="name"/>
			classes:<input type="text" name="cls.id"/>
			<input type="submit" value="提交"></input>
		</form>
		
		
		<br/>
		<a href="springMVC/testCookieValue">testCookieValue</a>
		<br/>
		<a href="springMVC/testServletAPI">testServletAPI</a>
		<br/>
		<a href="springMVC/testModelAndView">testModelAndView</a>
		<br/>
		<a href="springMVC/testMap">testMap</a>
		<br/>
		<a href="springMVC/testModel">testModel</a>
		<br/>
		<a href="springMVC/testSessionAttributes">testSessionAttributes</a>
		
		<br/><br/>
		testSessionAttributes：
		<form action="springMVC/testModelAttributes" method="post">
			id:<input type="text" name="id"/>
			name:<input type="text" name="name"/>
			<input type="submit" value="提交"></input>
		</form>
		<form action="springMVC/testModelAttributes2" method="post">
			id:<input type="text" name="id"/>
			name:<input type="text" name="name" value="张三"/>
			<input type="submit" value="提交"></input>
		</form>
		
		<br/>
		<a href="springMVC/testHelloView">testHelloView</a>
		<br/>
		<a href="springMVC/testRedirect">testRedirect</a>
		<br/>
		<a href="springMVC/testForward">testForward</a>
		<br/>
		自定义数据转换格式化：
		<form action="conversion/testConversion" method="post">
			学生信息[id-name-classid-classname]:<input type="text" name="student"/>
			<input type="submit" value="提交"></input>
		</form>
		
		<br/>
		webDataBinder初始化InitBinder注解：
		<form action="springMVC/testModelAttributes2" method="post">
			id:<input type="text" name="id"/>
			name:<input type="text" name="name" value="张三"/>
			<input type="submit" value="提交"></input>
		</form>
		
		<br/>
		<a href="testJson/getStudent" id="testJson">testJson/getStudent</a>
		<br/>
		
		<form action="testJson/testHttpMessageConverter" method="POST" enctype="multipart/form-data">
			File: <input type="file" name="file"/>
			Name: <input type="text" name="desc"/>
			<input type="submit" value="Submit"/>
		</form>
		
		<br/>
		<a href="testJson/testResponseEntity" id="testJson">下载文件test.txt</a>
		
		<!--
			源于资源国际化：
			1、在页面上能够根据浏览器语言设置文本、时间、数字进行本地化处理
			2、可以在bean中获取国际化资源文件Locale对应的消息
			3、可以通过超链接切换Locale，而不再依赖于浏览器的语言设置
			
			解决：
			1、使用JSTL的fmt标签
			2、在Bean中注入ResourceBunldeMessageSource，使用其对应的getMessage
			3、配置LocalResolver和LocalChangeInterceptor
		 -->
		 <br/>
		<fmt:message key="i18n.username"></fmt:message>
		<br/>
		<a href="i18n">i18n page</a>
		<br/>
		<a href="i18n2">i18n2 page</a>
		
		<br/>
		超链接切换语言：
		<br/>
		<a href="switchLocale?locale=en_US" >英文</a>
		<br/>
		<a href="switchLocale?locale=zh_CN" >中文</a>
		<br/><br/>
		
		文件上传MultipartResolver接口实现类完成：
		<!-- 方法请求必须是post，否则报不是一个multipart请求 -->
		<form action="testFileUpload" enctype="multipart/form-data" method="post">
			File: <input type="file" name="file"/>
			Name: <input type="text" name="desc"/>
			<input type="submit" value="Submit"/>
		</form>
	</body>
</html>