<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Insert title here</title>
		<style type="text/css">
			.error{
	    	color: red;
	    	}
	 		.login { 
	 		margin: 20px auto; 
	 		width: 300px; 
	 		} 
	 		.login-screen { 
	 		background-color: #FFF; 
	 		padding: 20px; 
	 		border-radius: 5px; 
	 		} 
			
			.app-title {
			text-align: center;
			color: #777;
			}
			
			.login-form {
			text-align: center;
			}
			.control-group {
			margin-bottom: 10px;
			}
			
			.control-group input{
			text-align: center;
			background-color: #ECF0F1;
			border: 2px solid transparent;
			border-radius: 3px;
			font-size: 16px;
			font-weight: 200;
			padding: 10px 0;
			width: 250px;
			transition: border .5s;
			}
			
	 		input:focus { 
	 		border: 3px solid #3498DB; 
	 		box-shadow: none; 
	 		border-color: black;
	 		} 
			
			.btn {
			  border: 2px solid transparent;
			  background: black;
			  color: #ffffff;
			  font-size: 16px;
			  line-height: 25px;
			  padding: 10px 0;
	    	  text-decoration: none;
			  text-shadow: none;
			  border-radius: 3px;
			  box-shadow: none;
			  transition: 0.25s;
			  display: block;
			  width: 250px;
			  margin: 0 auto;
			}
			
			.btn:hover {
			  background-color: #2980B9;
			}
		</style>
	</head>
	<body>
	<jsp:include page="headerNotLogged.jsp"></jsp:include>
		
<%-- 		<f:form commandName="user"> --%>
<!-- 			<p>username</p> -->
<%-- 			<f:input path="username"/><br> --%>
<%-- 			<f:errors class="error" path="username"/> --%>
			
<!-- 			<p>email</p> -->
<%-- 			<f:input path="email"/><br> --%>
<%-- 			<f:errors class="error" path="email"/> --%>
			
<!-- 			<p>password</p> -->
<%-- 			<f:password id="password" path="password"/><br> --%>
<%-- 			<f:errors class="error" path="password"/><br> --%>
			
<!-- 			<input type="submit" value="Register"> -->
<%-- 		</f:form>	 --%>

	<div class="login">
		<div class="login-screen">
			<div class="app-title">
				<h1>Sign up</h1>
			</div>
			
			<f:form commandName="user">

				<div class="login-form">
					<div class="control-group">
						<h4>Username</h4>
						<p class="login-field"><f:input path="username"/></p>
						<p class="error"><f:errors path="username"/></p>
						<label class="login-field-icon fui-user" for="login-name"></label>
					</div>
	
					<div class="control-group">
						<h4>Email</h4>
						<f:input path="email"/>
						<p class="error"><f:errors path="email"/></p>
						<label class="login-field-icon fui-lock" for="login-pass"></label>
					</div>
	
					<div class="control-group">
						<h4>Password</h4>
						<f:password id="password" path="password"/>
						<p class="error"><f:errors path="password"/></p>
						<label class="login-field-icon fui-lock" for="login-pass"></label>
					</div>
					
					<input class="btn btn-primary btn-large btn-block" type="submit">
				</div>
			
			</f:form>
		</div>
	</div>
	</body>
</html>