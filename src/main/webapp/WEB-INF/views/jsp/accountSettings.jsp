<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Account Settings</title>
		<link href="<c:url value="/css/ooo.css" />" rel="stylesheet" type="text/css">
	</head>
	<body>
		<jsp:include page="sideBarSettings.jsp"></jsp:include>

			
			<div id="rightContainer">
				<h1 class="rightContainerHeader"><s:message code="account"></s:message></h1>
				<c:out value="${ error }"></c:out>
				
				<f:form commandName="user">
					<p><s:message code="changeUsername"></s:message></p>
					<f:input path="username"/>
					
					<p><s:message code="changeEmail"></s:message></p>
					<f:input path="email"/><br><br>
					
					<input type="submit" value="<s:message code="saveChanges"></s:message>">
				</f:form>	
			</div>
			
	</body>
</html>