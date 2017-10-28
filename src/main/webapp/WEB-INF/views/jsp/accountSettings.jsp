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
		#rightContainer{ margin-left: 200px;}
		.rightContainerHeader{font-size: 260%;}
	</style>
</head>
	<body>
		<jsp:include page="sideBarSettings.jsp"></jsp:include>

			
			<div id="rightContainer">
				<h1 class="rightContainerHeader">Account</h1>
				<f:errors path="user.*"/>
				
				<f:form commandName="user">
					<p>change username</p>
					<f:input path="username"/>
					
					<p>change email</p>
					<f:input path="email"/>
					
					<!-- TODO: compare from DB -->
					<p>hide upvotes</p>
					<input type="checkbox"  name="email"><br><br>
					
					<input type="submit" value="Save Changes">
				</f:form>	
			</div>
			
		</div>
	</body>
</html>