<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib prefix="f" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
		
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Posts</title>
	<style type="text/css">
		#parent{  width: 900px; height: 20px;}
		#child{width: 350px; float: right; }
		#namePoints{background-color: red; display: inline-flex; margin-top: 0px; padding-top: 0px; margin-right: 5px; }
		.points{margin-right: 5px;}
		.video{width: 100%; height: auto;}
		#tags{display: inline-flex;}
		h1{
		text-align:center;
		}

			*{
			 font-family:-apple-system,BlinkMacSystemFont,Segoe UI,Helvetica Neue,Arial,Helvetica,Geneva,sans-serif;
			}
		.showRepliesButton{
			 background-color: #008CBA;	/* Blue */
			    border: none;
			    padding: 7px 16px;
			    text-align: center;
			    text-decoration: none;
			    font-family:-apple-system,BlinkMacSystemFont,Segoe UI,Helvetica Neue,Arial,Helvetica,Geneva,sans-serif;
			    color:white;
			    display: inline-block;
			    font-size: 16px;
			    border-radius:25px;
			}
			
			.likeButton{
			background-color: #008CBA;	/* Blue */
			    border: none;
			    padding: 7px 16px;
			    text-align: center;
			    text-decoration: none;
			    font-family:-apple-system,BlinkMacSystemFont,Segoe UI,Helvetica Neue,Arial,Helvetica,Geneva,sans-serif;
			    color:white;
			    display: inline-block;
			    font-size: 16px;
			    border-radius:25px;
			}
	</style>
</head>
	<body>
	
		<div id="parent">
			<div id="child">
				<c:forEach items="${ posts }" var="post">
					<div>
						<h1><c:out value="${ post.description }">no description</c:out></h1><br>
						
						<c:choose>
						  <c:when test="${post.video}">
						    <video class="video" controls>
							  <source src="/MyProject/postpic?pictureUrl=${post.postUrl}&userName=${post.user.username}" type="video/mp4">
							</video>
						  </c:when>
						  <c:otherwise>
						  	<img src="/MyProject/postpic?pictureUrl=${post.postUrl}&userName=${post.user.username}" width="100%" height="auto">
						  </c:otherwise>
						</c:choose>						
					</div>
		
					<div id="tags">
						<c:forEach items="${post.tags}" var="tag">
							<a href="/MyProject/posts/tag/tagName=${ tag.tagName }"> 
							#<c:out value="${ tag.tagName }"></c:out>
							</a>
						</c:forEach>
					</div>
					<br>
					<div id="namePoints" class="showRepliesButton">
						<p class="points"><s:message code="points"></s:message><span id=${ post.postId}><c:out value="${ post.points }"></span></c:out></p>
						<p><s:message code="user"></s:message><c:out value="${ post.user.username }"></c:out></p>
						<p><c:out value="${ post.dateTime }"></c:out></p>
					</div>
										
						<script type="text/javascript">
								function handleLike(postId){
									let button = document.getElementById("likeButton"+postId);
									if(button.innerHTML=="Like"){
										likePost(postId);
									}else if(button.innerHTML=="Unlike"){					
										unlikePost(postId);
									}								
								}
								function handleDislike(postId){
									let button = document.getElementById("dislikeButton"+postId);
									if(button.innerHTML=="Dislike"){
										dislikePost(postId);
									}else if(button.innerHTML="Undislike"){
										undislikePost(postId);
									}
								}
								function likePost(postId) {
										let request =new XMLHttpRequest();
										request.onreadystatechange = function() {			
											if(this.readyState == 4 && this.status == 200){
												let button = document.getElementById("likeButton"+postId);
												button.innerHTML="Unlike";
												button.style.backgroundColor  = "green";
												document.getElementById(""+postId).innerHTML=request.responseText;
												let button2 = document.getElementById("dislikeButton"+postId);
												if(button2.innerHTML=="Undislike"){
													button2.innerHTML="Dislike";
													button2.style.backgroundColor = "#008CBA";
												}
											}									
										}
										request.open("post","/MyProject/likePost?postId="+postId,true);
										request.send();				
								}
										  function unlikePost(postId) {
												let request =new XMLHttpRequest();	
												request.onreadystatechange = function() {
													if(this.readyState == 4 && this.status == 200){
														let button = document.getElementById("likeButton"+postId);
														button.innerHTML="Like";
														button.style.backgroundColor = "#008CBA";
														document.getElementById(""+postId).innerHTML=request.responseText;
													}												
												}
												request.open("post","/MyProject/unlikePost?postId="+postId,true);
												request.send();
												}
										  
										  function dislikePost(postId) {
												let request =new XMLHttpRequest();	
												request.onreadystatechange = function() {
													if(this.readyState == 4 && this.status == 200){
														let button = document.getElementById("dislikeButton"+postId);
														button.innerHTML="Undislike";
														button.style.backgroundColor  = "red";
														document.getElementById(""+postId).innerHTML=request.responseText;
														let button2 = document.getElementById("likeButton"+postId);
														if(button2.innerHTML=="Unlike"){
															button2.innerHTML="Like";
															button2.style.backgroundColor = "#008CBA";
														}
													}												
												}
												request.open("post","/MyProject/dislikePost?postId="+postId,true);
												request.send();
												}
										  function undislikePost(postId) {
												let request =new XMLHttpRequest();	
												request.onreadystatechange = function() {
													if(this.readyState == 4 && this.status == 200){
														let button = document.getElementById("dislikeButton"+postId);
														button.innerHTML="Dislike";
														button.style.backgroundColor  = "#008CBA";
														document.getElementById(""+postId).innerHTML=request.responseText;
													}												
												}
												request.open("post","/MyProject/undislikePost?postId="+postId,true);
												request.send();
												}
										  
										  
										  
										  
										  
							</script>
					
					<a href="/MyProject/postWithComments/postId=${ post.postId }/userId=${post.user.id}"><button class="showRepliesButton">Comments</button></a>
					
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
		<button id="likeButton${post.postId}"  style="background-color: green" onclick="handleLike(${post.postId})" class="showRepliesButton">Unlike</button>
		</c:if>
		<c:if test="${!isLiked}">
		<button id="likeButton${post.postId}" style="background-color: #008CBA" onclick="handleLike(${post.postId})"  class="showRepliesButton">Like</button>
		</c:if>
		<c:if test="${isDisliked}">
		<button id="dislikeButton${post.postId}"  style="background-color: red" onclick="handleDislike(${post.postId})"  class="showRepliesButton">Undislike</button>
		</c:if>
		<c:if test="${!isDisliked}">
		<button id="dislikeButton${post.postId}" style="background-color: #008CBA" onclick="handleDislike(${post.postId})"  class="showRepliesButton">Dislike</button>
		</c:if>
				<br>
				<br>
				</c:forEach>
			</div>
		</div>
	</body>
</html>