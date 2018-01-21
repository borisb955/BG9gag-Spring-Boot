<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 <%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Insert title here</title>
	<style>
		body{background: white; margin: 0px;}
		a:link {text-decoration: none;}
		a:hover{color: white;}
		#header{width: 98%; height: 37px; background: black; padding: 10px; text-align: left;}
		#logo img{margin-top:-10px; height: 45px; widght: 45px; float: left;}
		#loginReg{width: 165px; float: right; }
		#buttonsHeader{float: left; padding-left: 25px; display: inline-flex;}
		.header-links{color: gray; padding-left: 35px; text-decoration: none; font-weight: bold;}
		.languages{display: block; float:right;}
		.languages a:link{float: right; color: white;}
		.languages a:hover{float: right; color: white;}
		.search{margin-left: 200px;}
	</style>
</head>
	<body>
	<div id="header">
		<div id="logo">
			<a href="/BG9GAG"><img src="https://upload.wikimedia.org/wikipedia/commons/9/97/9GAG_new_logo.svg" ></a>
		</div>
		<div id="buttonsHeader">
			<a class="header-links" href="/BG9GAG/posts/hot"><s:message code="hot"></s:message></a>
			<a class="header-links" href="/BG9GAG/posts/fresh"><s:message code="fresh"></s:message></a>
			<a class="header-links" href="/BG9GAG/posts/gifs">GIF</a>
			<a class="header-links" href="/BG9GAG/posts/video">Videos</a>
			<a class="header-links" href="/BG9GAG/posts/videoYoutube">YTvideos</a>
			<form class="search" action="/BG9GAG/posts/search" method="get">
				<input type="text" placeholder="<s:message code="search..."></s:message>" name="search">
				<input type="submit" value="<s:message code="search"></s:message>">
			</form>
		</div>
		
		<div class="languages">
			<a href="?language=bg">Български</a><br>
			<a href="?language=en">English</a>
		</div>
		
		<div id="loginReg">
			<button><a href="/BG9GAG/login"><s:message code="logIn"></s:message></a></button>
			<button><a href="/BG9GAG/register"><s:message code="signUp"></s:message></a></button>	
		</div>
	</div>
	</body>
</html>