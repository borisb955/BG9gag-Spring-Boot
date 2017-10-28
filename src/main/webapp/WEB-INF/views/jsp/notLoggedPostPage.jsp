<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><c:out value="${ postPostPage.description }"></c:out></title>
</head>
<body>

		<jsp:include page="headerNotLogged.jsp"></jsp:include>
		
		<div>
		<h1><c:out value="${ postPostPage.description }"></c:out></h1><br>
		<c:forEach items="${postPostPage.tags}" var="tag" >
						<a href=""> #<c:out value="${ tag.tagName }"></c:out></a>
		</c:forEach>
		<br>
		<img src="postpic/postUrl=${ postPostPage.postUrl }" width="50%" height=auto>
		<br>
		<span>Points:<c:out value="${ postPostPage.points }"></c:out></span>
		<h3>Comments:</h3>
		<c:if test="${ postPostPage.comments.size()>0 }">
		<c:forEach items="${ postPostPage.comments }" var="comment" >
		<h3><c:out value="${ comment.user.username }"></c:out>
		:DATE:<c:out value="${ comment.dateTime }"></c:out>
		:POINTS:<c:out value="${ comment.points }"></c:out>
		</h3>
				<h1><c:out value="${ comment.comment }"></c:out></h1>
		</c:forEach>
		</c:if>
		<c:if test="${ postPostPage.comments.size()==0 }">
		<h2>No comments yet...</h2>
		</c:if>
		</div>
			<form action="" method="post">
			<input type="submit" value="BACK"/>
			</form>
</body>
</html>