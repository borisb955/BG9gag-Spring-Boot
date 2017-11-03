<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Insert title here</title>
	<style>
		body{background: white; margin: 0px;}
		#header{width: 98%; height: 26px; background: black; padding: 10px; text-align: left;}
		#logo img{margin-top:-10px; height: 45px; widght: 45px; float: left; border:}
		#loginReg{width: 140px; float: right; }
		#buttonsHeader{float: left; padding-left: 25px; display: inline-flex;}
		.header-links{color: gray; padding-left: 35px; text-decoration: none; font-weight: bold;}
		a:hover{color: white}
	</style>
</head>
	<body>
	<div id="header">
		<div id="logo">
			<a href="/MyProject"><img src="https://upload.wikimedia.org/wikipedia/commons/9/97/9GAG_new_logo.svg" ></a>
		</div>
		<div id="buttonsHeader">
			<a class="header-links" href="">Hot</a>
			<a class="header-links" href="/MyProject/posts/fresh">Fresh</a>
			<a class="header-links" href="/MyProject/posts/gifs">GIF</a>
			<form action="/MyProject/posts/search" method="get">
				<input type="text" placeholder="search..." name="search">
				<input type="submit" value="search">
			</form>
		</div>
		<div id="loginReg">
			

			<button><a href="/MyProject/login">Log in</a></button>
			<button><a href="/MyProject/register">Sign up</a></button>	

		</div>
	</div>
	</body>
</html>