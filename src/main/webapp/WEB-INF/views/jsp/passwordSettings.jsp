<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Insert title here</title>
		<style type="text/css">
			#rightContainer{ margin-left: 200px;}
			.rightContainerHeader{font-size: 260%;}
		</style>
	</head>
	
	<body>
			<jsp:include page="sideBarSettings.jsp"></jsp:include>
			
			<div id="rightContainer">
				<h1 class="rightContainerHeader"><s:message code="Password"></s:message></h1>
				<c:out value="${error}"></c:out>
				
				<form action="password" method="post">
					<p><s:message code="newPass"></s:message></p>
					<input type="password" placeholder="<s:message code="newPass"></s:message>" name="pass1">
					<p><s:message code="reNewPass"></s:message></p>
					<input type="password" placeholder="<s:message code="reNewPass"></s:message>" name="pass2">
					
					<input type="submit">
				</form>
			</div>
	</body>
</html>