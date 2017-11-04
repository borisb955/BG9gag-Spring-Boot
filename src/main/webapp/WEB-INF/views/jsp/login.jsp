<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
</head>
<body>
	<jsp:include page="headerNotLogged.jsp"></jsp:include>
	
	<form class="forma" action="logged" method="post">
		<input type="text" placeholder="<s:message code="email"></s:message>" name="email"><br>
		<input type="password" placeholder="<s:message code="password"></s:message>" name="pass"><br>
		<input type="submit">
	</form>
	
	<a href="/MyProject/forgottenPass"><s:message code="forgottenPass"></s:message></a>
</body>
</html>