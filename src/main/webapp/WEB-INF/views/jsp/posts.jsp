<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Posts</title>
	<style type="text/css">
		#parent{  width: 900px; height: 20px;}
		#child{width: 350px; float: right; }
		#namePoints{background-color: red; display: inline-flex; margin-top: 0px; padding-top: 0px; margin-right: 5px; }
		.points{margin-right: 5px;}
		#tags{display: inline-flex;}
		h1{
		text-align:center;
		}
	</style>
</head>
	<body>
	
		<div id="parent">
			<div id="child">
				<c:forEach items="${ posts }" var="post">
					<div>
						<h1><c:out value="${ post.description }">no description</c:out></h1><br>
						<img src="/MyProject/postpic?pictureUrl=${post.postUrl}&userName=${post.user.username}" width="100%" height="auto">					
						</div>
		
					<div id="tags">
						<c:forEach items="${post.tags}" var="tag">
							<a href="/MyProject/posts/tag/tagName=${ tag.tagName }"> 
							#<c:out value="${ tag.tagName }"></c:out>
							</a>
						</c:forEach>
					</div>
					<br>
					<div id="namePoints">
						<p class="points">points: <c:out value="${ post.points }"></c:out></p>
						<p>user: <c:out value="${ post.user.username }"></c:out></p>
						<p><c:out value="${ post.dateTime }"></c:out></p>
					</div>
					
					
					<a href="/MyProject/postWithComments/postId=${ post.postId }/userId=${post.user.id}">Comments</a>
					
		<c:set var="isLiked" value="false" />
		<c:set var="isDisliked" value="false" />
		<c:forEach var="postLiked" items="${sessionScope.likedPosts}">
		<c:if test="${postLiked.postId eq post.postId }">
			<c:set var="isLiked" value="true" />
		</c:if>
		</c:forEach>
		<c:forEach var="postDisliked" items="${sessionScope.dislikedPosts}">
		<c:if test="${postDisliked.postId eq post.postId }">
			<c:set var="isDisliked" value="true" />
		</c:if>
		</c:forEach>
		
		<c:if test="${isLiked}">
		<a href="/MyProject/unlikePost?postId=${  post.postId }&userId=${sessionScope.user.id}">Unlike</a>
		</c:if>
		<c:if test="${!isLiked}">
		<a href="/MyProject/likePost?postId=${ post.postId }&userId=${sessionScope.user.id}">Like</a>
		</c:if>
		<c:if test="${isDisliked}">
		<a href="/MyProject/undislikePost?postId=${  post.postId }&userId=${sessionScope.user.id }">Undislike</a>
		</c:if>
		<c:if test="${!isDisliked}">
			<a href="/MyProject/dislikePost?postId=${  post.postId }&userId=${sessionScope.user.id }">Dislike</a>
		</c:if>
				<br>
				<br>
				</c:forEach>
			</div>
		</div>
	</body>
</html>