<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@taglib uri = "http://www.springframework.org/tags/form" prefix = "form"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><c:out value="${  sessionScope.postPostPage.description }"></c:out></title>
</head>
<body>
				<jsp:include page="headerLogged.jsp"></jsp:include>
		<div>
		<h1><c:out value="${  sessionScope.postPostPage.description }"></c:out></h1><br>
		<c:forEach items="${  sessionScope.postPostPage.tags}" var="tag" >
						<a href=""> #<c:out value="${ tag.tagName }"></c:out></a>
		</c:forEach>
		<br>
		<img src="/MyProject/postpic?pictureUrl=${sessionScope.postPostPage.postUrl}&userName=${sessionScope.userPostPage.username}" width="50%" height="auto">
		<br>
		<span>Points:<c:out value="${ sessionScope.postPostPage.points }"></c:out></span>
		
		<c:set var="isLiked" value="false" />
		<c:set var="isDisliked" value="false" />
		<c:forEach var="postLiked" items="${sessionScope.likedPosts}">
		<c:if test="${postLiked.postId eq sessionScope.postPostPage.postId }">
			<c:set var="isLiked" value="true" />
		</c:if>
		</c:forEach>
		<c:forEach var="postDisliked" items="${sessionScope.dislikedPosts}">
		<c:if test="${postDisliked.postId eq sessionScope.postPostPage.postId }">
			<c:set var="isDisliked" value="true" />
		</c:if>
		</c:forEach>
		
		<c:if test="${isLiked}">
		<a href="/MyProject/unlikePost?postId=${  sessionScope.postPostPage.postId }&userId=${sessionScope.user.id}">Unlike</a>
		</c:if>
		<c:if test="${!isLiked}">
		<a href="/MyProject/likePost?postId=${ sessionScope.postPostPage.postId }&userId=${sessionScope.user.id}">Like</a>
		</c:if>
		<c:if test="${isDisliked}">
		<a href="/MyProject/undislikePost?postId=${  sessionScope.postPostPage.postId }&userId=${sessionScope.user.id }">Undislike</a>
		</c:if>
		<c:if test="${!isDisliked}">
			<a href="/MyProject/dislikePost?postId=${  sessionScope.postPostPage.postId }&userId=${sessionScope.user.id }">Dislike</a>
		</c:if>
		
		<!--<button id="likeButton" value="Like" style="background-color: green" onclick="handleLike()">Like</button> -->
		<p style="display: none;"><c:out value="${ sessionScope.postPostPage.postId }"></c:out></p>
		<p style="display: none;"><c:out value="${ sessionScope.userPostPage.id }"></c:out></p>
		<h3>Comments:</h3>
				<form method = "POST" action = "/MyProject/addComment">
				<textarea name="commentText" path="" id="commentText" class="commentText" rows="6" cols="50" maxlength="1000" style="resize:none;" placeholder="Comment here..."></textarea>
				<input type="submit"  value="Comment" onclick="postComment()">
				</form>
							<script type="text/javascript">
								function handleLike(){
									let button = document.getElementById("likeButton");
									if(button.innerHTML=="Like"){
										likePost();
									}else{
										unlikePost();
									}
									
								}
									function likePost() {
										let request =new XMLHttpRequest();
										request.onreadystatechange = function() {			
											if(this.readyState == 4 && this.status == 200){
												let button = document.getElementById("likeButton");
												button.innerHTML="Dislike";
												button.style.backgroundColor  = "red";						
											}
										
										}
										request.open("post","/MyProject/like",true);
										request.send();				
										  }
										  function unlikePost() {
												let request =new XMLHttpRequest();	
												request.onreadystatechange = function() {
													if(this.readyState == 4 && this.status == 200){
														let button = document.getElementById("likeButton");
														button.innerHTML="Like";
														button.style.backgroundColor  = "green";
													}
												
												}
												request.open("post","/MyProject/unlike",true);
												request.send();
												  }
										 
												  
										
							</script>
		<c:if test="${  sessionScope.postPostPage.comments.size()>0 }">
		<c:forEach items="${  sessionScope.postPostPage.comments }" var="comment" >
			<div>
				<h3><c:out value="${ comment.user.username }"></c:out>
				:DATE:<c:out value="${ comment.dateTime }"></c:out>
				:POINTS:<c:out value="${ comment.points }"></c:out></h3>
				
				
		<c:set var="isLiked" value="false" />
		<c:set var="isDisliked" value="false" />
		<c:forEach var="commentLiked" items="${sessionScope.likedComments}">
		<c:if test="${commentLiked.comment_id eq comment.comment_id }">
			<c:set var="isLiked" value="true" />
		</c:if>
		</c:forEach>
		<c:forEach var="commentDisliked" items="${sessionScope.dislikedComments}">
		<c:if test="${ commentDisliked.comment_id eq comment.comment_id }">
			<c:set var="isDisliked" value="true" />
		</c:if>
		</c:forEach>
		
		<c:if test="${isLiked}">
		<a href="/MyProject/unlikeComment?commentId=${  comment.comment_id }&userId=${ sessionScope.user.id }">Unlike</a>
		</c:if>
		<c:if test="${!isLiked}">
			<a href="/MyProject/likeComment?commentId=${ comment.comment_id }&userId=${ sessionScope.user.id }">Like</a>
		</c:if>
		<c:if test="${isDisliked}">
		<a href="/MyProject/undislikeComment?commentId=${  comment.comment_id }&userId=${ sessionScope.user.id }">Undislike</a>	
		</c:if>
		<c:if test="${!isDisliked}">
			<a href="/MyProject/dislikeComment?commentId=${  comment.comment_id }&userId=${ sessionScope.user.id }">Dislike</a>	
		</c:if>																	
				<h2><c:out value="${ comment.comment }"></c:out></h2>
				
				<form method = "POST" action = "/MyProject/addChildComment">
				<input type="text" value="${comment.comment_id}" name="parentCommentId" style="display:none">
				<input type="text" value="${ comment.user.id}" name="parentCommentUserId" style="display:none">
				<textarea name="commentText" path="" id="commentText" class="commentText" rows="6" cols="50" maxlength="1000" style="resize:none;" placeholder="Comment here..."></textarea>
				<input type="submit"  value="Comment" onclick="postComment()">
				</form>
				</div>
				<c:forEach items="${comment.childComments}" var="childComment">
							<div>							
								<h5><c:out value="${ childComment.user.username }"></c:out>
								:DATE:<c:out value="${ childComment.dateTime }"></c:out>
								:POINTS:<c:out value="${ childComment.points }"></c:out></h5>
								
								
								
								<c:set var="isLiked" value="false" />
								<c:set var="isDisliked" value="false" />
								<c:forEach var="commentLiked" items="${sessionScope.likedComments}">
								<c:if test="${commentLiked.comment_id eq childComment.comment_id }">
									<c:set var="isLiked" value="true" />
								</c:if>
								</c:forEach>
								<c:forEach var="commentDisliked" items="${sessionScope.dislikedComments}">
								<c:if test="${ commentDisliked.comment_id eq childComment.comment_id }">
									<c:set var="isDisliked" value="true" />
								</c:if>
								</c:forEach>
								
								<c:if test="${isLiked}">
								<a href="/MyProject/unlikeComment?commentId=${  childComment.comment_id }&userId=${ sessionScope.user.id }">Unlike</a>
								</c:if>
								<c:if test="${!isLiked}">
									<a href="/MyProject/likeComment?commentId=${ childComment.comment_id }&userId=${ sessionScope.user.id }">Like</a>
								</c:if>
								<c:if test="${isDisliked}">
								<a href="/MyProject/undislikeComment?commentId=${  childComment.comment_id }&userId=${ sessionScope.user.id }">Undislike</a>	
								</c:if>
								<c:if test="${!isDisliked}">
									<a href="/MyProject/dislikeComment?commentId=${  childComment.comment_id }&userId=${ sessionScope.user.id }">Dislike</a>	
								</c:if>
										
								<h5><c:out value="${ childComment.comment }"></c:out></h5>
								
								<form method = "POST" action = "/MyProject/addChildComment">
								<input type="text" value="${comment.comment_id}" name="parentCommentId" style="display:none">
								<input type="text" value="${ childComment.user.id}" name="parentCommentUserId" style="display:none">
								<textarea name="commentText" path="" id="commentText" class="commentText" rows="6" cols="50" maxlength="1000" style="resize:none;" placeholder="Comment here..."></textarea>
								<input type="submit"  value="Comment" onclick="postComment()">
								</form>
							</div>							
				</c:forEach>
				
		</c:forEach>
		</c:if>
		<c:if test="${  sessionScope.postPostPage.comments.size()==0 }">
		<h2>No comments yet...</h2>
		</c:if>
		</div>
		
</body>
</html>