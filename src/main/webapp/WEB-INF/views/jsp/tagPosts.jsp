<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Insert title here</title>
	</head>
	<body>
		<c:choose>
		  <c:when test="${sessionScope.logged}">
		    <jsp:include page="headerLogged.jsp"></jsp:include>
		  </c:when>
		  <c:otherwise>
		    <jsp:include page="headerNotLogged.jsp"></jsp:include>
		  </c:otherwise>
		</c:choose>
		
		<jsp:include page="posts.jsp"></jsp:include>
	</body>
</html>