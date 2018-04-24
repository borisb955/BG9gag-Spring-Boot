<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Profile Settings</title>
		<style type="text/css">
			#container{width: 800px; margin: 40px auto 0 200px; position: relative; display: inline-flex; }
			#rightContainer{ margin-left: 200px;}
			#leftContainer{ padding-left: 0px; background-color: red;}
			.par{font-size: 120%;}
			.rightContainerHeader{font-size: 260%;}
			.ulLeft{list-style-type: none; position: absolute;}
			.errors{color: red;}
		</style>
	<body>
		<jsp:include page="sideBarSettings.jsp"></jsp:include>
			
			<div id="rightContainer">
				<h1 class="rightContainerHeader"><s:message code="Profile"></s:message></h1>
				<f:errors class="errors" path="profile.*"/>
				<p class="errors"><c:out value="${ error }"></c:out></p>
				
				<f:form commandName="profile" enctype="multipart/form-data">
						<p class="par"><s:message code="upAvatar"></s:message></p>
						<input type="file" name="failche">
						
						<p class="par"><s:message code="yourName"></s:message></p>
						<f:input path="fullName"/>
						
						<p class="par"><s:message code="Gender"></s:message></p>
						<f:input path="gender"/>
						
						<p class="par"><s:message code="Birthday"></s:message></p>
						<f:input path="dateOfBirth"/>
						
						<p class="par"><s:message code="Description"></s:message></p>
						<f:input path="info"/>
						<br><br>
						<input type="submit" value="Save Changes">
				</f:form>	
			</div>
			
		
	</body>
</html>