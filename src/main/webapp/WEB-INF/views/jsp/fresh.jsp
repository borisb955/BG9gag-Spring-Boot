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
			#parent{  width: 900px; height: 20px;}
			#child{width: 350px; float: right; }
			#namePoints{background-color: red; display: inline-flex; margin-top: 0px; padding-top: 0px; }
			.points{margin-right: 5px;}
			#tags{display: inline-flex;}
		</style>
	</head>
	<body>
	
		<c:choose>
		  <c:when test="${sessionScope.logged}">
		    <jsp:include page="headerLogged.jsp"></jsp:include>
		  </c:when>
		  <c:otherwise>
		    <jsp:include page="headerNotLogged.jsp"></jsp:include>
		  </c:otherwise>
		</c:choose>
		
		<div id="parent">
			<div id="child">
				<c:forEach items="${ posts }" var="post">
					<div>
						<h1><c:out value="${ post.description }">no description</c:out></h1><br>
						<img src="postpic/postUrl=${ post.postUrl }" width="50%" height="auto"> 
					</div>
		
					<div id="tags">
						<c:forEach items="${post.tags}" var="tag">
							<a href=""> #<c:out value="${ tag.tagName }"></c:out></a>
						</c:forEach>
					</div>
					<br>
					<div id="namePoints">
						<p class="points">points: <c:out value="${ post.points }"></c:out></p>
						<p>user: <c:out value="${ post.user.username }"></c:out></p>
					</div>
					<c:out value="${ post.dateTime }"></c:out>
					
					<a href="postWithComments/postId=${ post.postId }/userId=${post.user.id}">Comments</a>
				</c:forEach>
			</div>
		</div>
	
	</body>
</html>