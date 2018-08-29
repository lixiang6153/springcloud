<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    	<script type="text/javascript" src="js/jquery-2.2.2.js"></script>
  </head>
  
  <body>
    <h1>Hello World</h1>
    <a href="/SSHDemo/testAction/testGet?userName=ou">发送get请求</a>
    <br>
    <!-- ------------------------------------------------ -->
    <!-- 发送post请求,通过form -->
    <form action="/SSHDemo/testAction/testPost" method="post">
    	<input name="userName">
    	<button>测试post请求</button>
    </form>
  	<br>
  	<!-- ------------------------------------------------- -->
  	<button id="myAjax">通过ajax发送异步post请求</button>  
  	<script type="text/javascript">
  		$(function(){
  			$("#myAjax").click(function(){
  				$.post("/SSHDemo/testAction/testAjax",{userName:"你好"},function(data){
  					alert("收到返回结果"+data);
  				});
  			});
  		});
	</script>
  </body>
</html>
