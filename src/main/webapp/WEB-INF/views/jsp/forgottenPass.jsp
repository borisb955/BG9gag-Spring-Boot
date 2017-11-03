<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Insert title here</title>
		<style type="text/css">
			.error{color: red;}
		</style>
	</head>
	<body>
	
		<f:form commandName="user">
			<p><s:message code="email"></s:message></p>
			<f:input path="email"/><br>
			<f:errors class="error" path="email"/>
			<input type="submit" value="Send new password">
		</f:form>	
		
	</body>
</html>