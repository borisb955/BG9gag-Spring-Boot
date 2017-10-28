<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Insert title here</title>
	<style type="text/css">
		#container{width: 800px; margin: 40px auto 0 200px; position: relative; display: inline-flex; }
		#leftContainer{ padding-left: 0px; background-color: red;}
		#rightContainer{ margin-left: 200px;}
		.ulLeft{list-style-type: none; position: absolute;}
		.rightContainerHeader{font-size: 260%;}
	</style>
</head>
	<body>
		<jsp:include page="sideBarSettings.jsp"></jsp:include>
			
			<div id="rightContainer">
				<h1 class="rightContainerHeader">Profile</h1>
				<f:errors path="profile.*"/>
				
				<f:form commandName="profile">
						<p>Your Name</p>
						<f:input path="fullName"/>
						
						<p>Gender</p>
						<f:input path="gender"/>
						
						<p>Birthday</p>
						<f:input path="dateOfBirth"/>
						
						<p>Description</p>
						<f:input path="info"/>
						
						<input type="submit" value="Save Changes">
				</f:form>	
			</div>
			
		</div>
	</body>
</html>