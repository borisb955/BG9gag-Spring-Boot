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
		#header{width: 98%; height: 26px; background: black; padding: 10px; text-align: left;}
		#logo img{margin-top:-10px; height: 45px; widght: 45px; float: left; border:}
		#loginReg{width: 180px; float: right; }
		#buttonsHeader{float: left; padding-left: 25px;display: inline-flex;}
		.header-links{color: gray; padding-left: 35px; text-decoration: none; font-weight: bold;}
		.upload-button{background: blue; cursor: pointer; border: none; font-size: 16px;}
		.button-links{color: white; text-decoration: none;}
		.username{color: white}
		.box{display: inline-flex;}
		a:hover{color: white}
		
		
			/* Dropdown Button */
		.dropimg {
		    background-color: white;
		    color: black;
		    font-size: 16px;
		    border: none;
		    width: 30px;
		    height: 30px;
		    cursor: pointer;
		}
		
		/* The container <div> - needed to position the dropdown content */
		.dropdown {
		    position: relative;
		    display: inline-block;
		}
		
		/* Dropdown Content (Hidden by Default) */
		.dropdown-content {
		    display: none;
		    position: absolute;
		    background-color: #f9f9f9;
		    min-width: 100px;
		    box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
		    z-index: 1;
		}
		
		/* Links inside the dropdown */
		.dropdown-content a {
		    color: black;
		    text-align: left;
		    padding: 5px 7px;
		    width: 120px;
		    text-decoration: none;
		    display: block;
		}
		
		/* Change color of dropdown links on hover */
		.dropdown-content a:hover {background-color: #f1f1f1}
		
		/* Show the dropdown menu on hover */
		.dropdown:hover .dropdown-content {
		    display: block;
		}
		
		
	</style>
</head>
		<div id="header">
		<div id="logo">
			<a href="/MyProject"><img src="https://upload.wikimedia.org/wikipedia/commons/9/97/9GAG_new_logo.svg" ></a>
		</div>
		<div id="buttonsHeader">
			<a class="header-links" href="/MyProject/posts/hot"><s:message code="hot"></s:message></a>
			<a class="header-links" href="/MyProject/posts/fresh"><s:message code="fresh"></s:message></a>
			<a class="header-links" href="/MyProject/posts/gifs">GIF</a>
			<a class="header-links" href="/MyProject/posts/video">Video</a>
			<a class="header-links" href="/MyProject/posts/videoYoutube">YTvideos</a>
			<form action="/MyProject/posts/search" method="get">
				<input type="text" placeholder="<s:message code="search..."></s:message>" name="search">
				<input type="submit" value="<s:message code="search"></s:message>">
			</form>
		</div>
		
		<a href="?language=bg">Български</a>
		<a href="?language=en">English</a>	
		
		<div id="loginReg">
		
		<div class="box">
			<div class="dropdown">
			  <img class="dropimg" src="/MyProject/myProfile/avatar">
				<div class="dropdown-content">
					<form action="/MyProject/myProfile" method="get">
						<input type="submit" value="<s:message code="myProfile"></s:message>"/>
					</form>
			    	<form action="/MyProject/settings/account" method="get">
			    		<input type="submit" value="<s:message code="settings"></s:message>"/>
			    	</form>
			    	<form action="/MyProject/logout" method="post">
			    		<input type="submit" value="<s:message code="logout"></s:message>"/>
			    	</form>
				</div>
			</div>

			<form action="/MyProject/upload" method="get">
	    		<input style="background-color: blue;" class="button-links" type="submit" value="+ <s:message code="upload"></s:message>"/>
	    	</form>
		</div>
	
		</div>
	</div>
</html>