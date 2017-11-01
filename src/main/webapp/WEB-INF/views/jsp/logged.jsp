
<%@page import="java.util.HashSet"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>9gag logged</title>
</head>
	<body>
		<jsp:include page="headerLogged.jsp"></jsp:include>
<<<<<<< HEAD

		<div id="parent">
			<div id="child">
				<c:forEach items="${ allPosts }" var="post" >
				
				<div>
					<h1><c:out value="${ post.description }">no description</c:out></h1><br>
				<img src="postpic?pictureUrl=${post.postUrl}&userName=${post.user.username}" width="50%" height="auto">
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
				
				<a href="postWithComments/postId=${ post.postId }/userId=${post.user.id}">Comments</a>
				<a href="/MyProject/likePost?postId=${ post.postId }&userId=${sessionScope.user.id}">Like</a>
				<a href="/MyProject/unlikePost?postId=${ post.postId }&userId=${sessionScope.user.id}">Unlike</a>
				<a href="/MyProject/dislikePost?postId=${ post.postId }&userId=${sessionScope.user.id }">Dislike</a>
				<a href="/MyProject/undislikePost?postId=${ post.postId }&userId=${sessionScope.user.id }">Undislike</a>
					<c:if test="">
					<a href="/MyProject/likePost?postId=${ post.postId }&userId=${sessionScope.user.id}">Like</a>
					</c:if>
					<c:if test="">
					<a href="/MyProject/dislikePost?postId=${ post.postId }&userId=${sessionScope.user.id }">Dislike</a>
					</c:if>
				</c:forEach>
			</div>
		</div>
		<jsp:include page="posts.jsp"></jsp:include>
	</body>
</html>