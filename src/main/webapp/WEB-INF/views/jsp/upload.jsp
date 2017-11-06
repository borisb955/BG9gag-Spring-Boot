<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Insert title here</title>
		<style type="text/css">
			.error{
			color: red;
			}
			.success{
	    	color: green;
	    	font-weight: bold;
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
		<jsp:include page="headerLogged.jsp"></jsp:include>
		
<%-- 		<p class="error"><c:out value="${ error }"></c:out></p> --%>
<!-- 		<form action="upload" method="post" enctype="multipart/form-data"> -->
<!-- 			<!-- TODO: Style this --> -->
<!-- 			<br> -->
<!-- 			Upload File: <input type="file" name="failche"> -->
<!-- 			<p>Formats allowed: mp4, png, gif, jpg, jpeg, tiff, bmp</p> -->
<!-- 			YouTube: <input type="text" name="youtube" /><br><br> -->
<!-- 		    Description: <input type="text" name="description" /><br><br> -->
<!-- 		    Tags: <input type="text" placeholder="tags" name="tags"><br> -->
<!-- 		    <input type="submit" /> -->
<!-- 		</form> -->
		
		
		
	<div class="login">
		<div class="login-screen">
			<div class="app-title">
				<h1>UPLOAD</h1>
			</div>
			
			<p class="error"><c:out value="${ error }"></c:out></p>
			<p class="success"><c:out value="${ success }"></c:out></p>
			<form action="upload" method="post" enctype="multipart/form-data">

				<div class="login-form">
					<div class="control-group">
						<input type="file" name="failche">
						<label class="login-field-icon fui-user" for="login-name"></label>
					</div>
					<p>Formats allowed: mp4, png, gif, jpg, jpeg, tiff, bmp</p>
					<div class="control-group">
						<input type="text" placeholder="Youtube link" name="youtube" />
						<label class="login-field-icon fui-lock" for="login-pass"></label>
					</div>

					<div class="control-group">
						<input type="text" placeholder="description" name="description" />
						<label class="login-field-icon fui-lock" for="login-pass"></label>
					</div>
					
					<div class="control-group">
						<input type="text" placeholder="tags" name="tags" />
						<label class="login-field-icon fui-lock" for="login-pass"></label>
					</div>
					
					<input class="btn btn-primary btn-large btn-block" type="submit" value="Upload">
				</div>
			
			</form>
		</div>
	</div>
	</body>
</html>