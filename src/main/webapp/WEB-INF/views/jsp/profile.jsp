<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>My Profile</title>
	
	<style type="text/css">
		#myHeader{height: 200px; width: 100%; background-color: #2d2d2d; margin-top:0px; display: block;}
		.username{font-size: 200%;  text-align: center; color: white; margin: 10px;}
		.funnyCollection{font-size: 110%; text-align: center; color: #b5b5b5; margin:-22px; margin:0;}
		.img{width: 100px; height: 100px; display: block; margin: 0 630px; }
		#navigation{border-bottom: 3px ridge; width: 100%; margin-top: 10px;}
		.navButtons{display: inline-flex; margin-left: 70px; }
	</style>
</head>
	<body>
		<jsp:include page="headerLogged.jsp"></jsp:include>
		
		<div id="myHeader">
			<img class="img" src="/BG9GAG/myProfile/avatar">
			
<!-- 			Display username if the user hasn't set his full name -->
			<c:choose>
			    <c:when test="${empty sessionScope.user.profile.fullName}">
			    	<p class="username"><c:out value="${ sessionScope.user.username }"></c:out></p>
			    </c:when>
			    <c:otherwise>
			       <p class="username"><c:out value="${ sessionScope.user.profile.fullName }"></c:out></p>
			    </c:otherwise>
			</c:choose>
			
<!-- 			Display particular text if the user hasn't set info -->
			<c:choose>
			    <c:when test="${empty sessionScope.user.profile.info}">
			    	<p class="funnyCollection">My funny collection</p>
			    </c:when>
			    <c:otherwise>
			       <p class="funnyCollection"> <c:out value="${ sessionScope.user.profile.info }"></c:out> </p>
			    </c:otherwise>
			</c:choose>

		</div>
		
		<div id="navigation">
			<div class="navButtons">
				<form action="/BG9GAG/myProfile/posts">
					<input type="submit" value="<s:message code="POSTS"></s:message>">
				</form>
				<form action="/BG9GAG/myProfile/myLikedPosts">
					<input type="submit" value="<s:message code="UPVOTES"></s:message>">
				</form>
				<form action="/BG9GAG/myProfile/myCommentedPosts">
					<input type="submit" value="<s:message code="COMMENTS"></s:message>">
				</form>
			</div>
		</div>
	</body>
</html>