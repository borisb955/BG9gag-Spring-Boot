<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Insert title here</title>
		<style type="text/css">
			.error{color: red;}
		</style>
	</head>
	<body>
		<jsp:include page="headerLogged.jsp"></jsp:include>
		
		<p class="error"><c:out value="${ error }"></c:out></p>
		<form action="upload" method="post" enctype="multipart/form-data">
			<!-- TODO: Style this -->
			<br>
			Upload File: <input type="file" name="failche">
			<p>Formats allowed: mp4, png, gif, jpg, jpeg, tiff, bmp</p>
		    Description: <input type="text" name="description" /><br><br>
		    Tags: <input type="text" placeholder="tags" name="tags"><br>
		    <input type="submit" />
		</form>
	</body>
</html>