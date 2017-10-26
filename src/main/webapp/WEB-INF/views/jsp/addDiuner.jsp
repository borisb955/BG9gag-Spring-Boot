<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<f:form commandName="diunerche">
		Hlebche:<f:input path="hlebche"/>
		Sosche:<f:input path="sosche"/>
		Gramaj:<f:input path="gramaj"/>
		Kartofki:<f:input path="kartofki"/>
		Meso:<f:input path="meso"/>
		<input type="submit" value="Save Diuner">
	</f:form>
</body>
</html>