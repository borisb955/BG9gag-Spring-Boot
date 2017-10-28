<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Insert title here</title>
		<style type="text/css">
			#rightContainer{ margin-left: 200px;}
			.rightContainerHeader{font-size: 260%;}
		</style>
	</head>
	
	<body>
			<jsp:include page="sideBarSettings.jsp"></jsp:include>
			
			<div id="rightContainer">
				<h1 class="rightContainerHeader">Password</h1>
				<c:out value="${error}"></c:out>
				
				<form action="password" method="post">
					<p>new password</p>
					<input type="password" placeholder="new-password" name="pass1">
					<p>re-type new-password</p>
					<input type="password" placeholder="re-type new-password" name="pass2">
					
					<input type="submit">
				</form>
			</div>
			
		
	</body>
</html>