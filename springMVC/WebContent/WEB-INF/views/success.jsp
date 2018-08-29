<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
		Hello World! this is success page.
		<br/>
		time: ${requestScope.time}
		<br/><br/>
		name:${requestScope.name };
		<br/>
		age:${requestScope.age };
		<br/>
		sex:${requestScope.sex };
		
		<br/><br/>
		request student:${requestScope.student };
		<br/>
		request school:${requestScope.school };
		<br/>
		request hh:${requestScope.hh };
		
		<br/>
		session student:${sessionScope.student };
		<br/>
		session school:${sessionScope.school };
		<br/>
		session hh:${sessionScope.hh };
		
		<br/>
		i18n:
		<br/>
		<fmt:message key="i18n.username"></fmt:message>
		<br/>
		<fmt:message key="i18n.password"></fmt:message>
</body>
</html>