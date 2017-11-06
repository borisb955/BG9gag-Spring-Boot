<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Insert title here</title>
		<link href="<c:url value="css/ooo.css" />" rel="stylesheet" type="text/css">
		<style type="text/css">
			.ulLeft a{color: black; font-family: verdana; font-size: 100%;  font-weight: bold;}
			.ulLeft a:hover{color: gray;}
		</style>
	</head>
	<body>
		<jsp:include page="headerLogged.jsp"></jsp:include>
		
		<div id="container">
			<div id="leftContainer">
				<ul class="ulLeft">
					<li><a href="/MyProject/settings/account"><s:message code="account"></s:message></a></li>
					<li><a href="/MyProject/settings/password"><s:message code="Password"></s:message></a></li>
					<li><a href="/MyProject/settings/profile"><s:message code="Profile"></s:message></a></li>
				</ul>
			</div>
	</body>
</html>