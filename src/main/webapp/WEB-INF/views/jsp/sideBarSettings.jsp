<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>

	<style type="text/css">
		#container{width: 800px; margin: 40px auto 0 200px; position: relative; display: inline-flex; }
		#leftContainer{ padding-left: 0px; background-color: red;}
		.ulLeft{list-style-type: none; position: absolute;}
	</style>
</head>
	<body>
		<jsp:include page="headerLogged.jsp"></jsp:include>
		
		<div id="container">
			<div id="leftContainer">
				<ul class="ulLeft">
					<li><a href="/MyProject/settings/account">Account</a></li>
					<li><a href="/MyProject/settings/password">Password</a></li>
					<li><a href="/MyProject/settings/profile">Profile</a></li>
				</ul>
			</div>
	</body>
</html>