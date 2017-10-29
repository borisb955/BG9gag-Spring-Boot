<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
	<body>
		<jsp:include page="headerLogged.jsp"></jsp:include>
		
		<form action="upload" method="post" enctype="multipart/form-data">
			<!-- TODO: Style this -->
			<br>
			Upload File: <input type="file" name="failche"><br><br>
		    Description: <input type="text" name="description" /><br><br>
		    Tag1: <input type="text" placeholder="tag1" name="tag1">
		    Tag2: <input type="text" placeholder="tag2" name="tag2">
		    Tag3: <input type="text" placeholder="tag3" name="tag3"><br>
		    <input type="submit" />
		</form>
	</body>
</html>