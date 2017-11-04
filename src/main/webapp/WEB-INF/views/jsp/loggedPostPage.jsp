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
		Points:<span id="post${sessionScope.postPostPage.postId }"><c:out value="${ sessionScope.postPostPage.points }"></c:out></span>
		
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
		<button id="likeButtonPost${sessionScope.postPostPage.postId}"  style="background-color: green" onclick="handleLike(${sessionScope.postPostPage.postId})">Unlike</button>
		</c:if>
		<c:if test="${!isLiked}">
		<button id="likeButtonPost${sessionScope.postPostPage.postId}" style="background-color: green" onclick="handleLike(${sessionScope.postPostPage.postId})">Like</button>
		</c:if>
		<c:if test="${isDisliked}">
		<button id="dislikeButtonPost${sessionScope.postPostPage.postId}"  style="background-color: green" onclick="handleDislike(${sessionScope.postPostPage.postId})">Undislike</button>
		</c:if>
		<c:if test="${!isDisliked}">
			<button id="dislikeButtonPost${sessionScope.postPostPage.postId}" style="background-color: red" onclick="handleDislike(${sessionScope.postPostPage.postId})">Dislike</button>
		</c:if>
		
		<p style="display: none;"><c:out value="${ sessionScope.postPostPage.postId }"></c:out></p>
		<p style="display: none;"><c:out value="${ sessionScope.userPostPage.id }"></c:out></p>
		<h3>Comments:</h3>
				<form method = "POST" action = "/MyProject/addComment">
				<textarea name="commentText" path="" id="commentText" class="commentText" rows="6" cols="50" maxlength="1000" style="resize:none;" placeholder="Comment here..."></textarea>
				<input type="submit"  value="Comment" onclick="postComment()">
				</form>
							<script type="text/javascript">
								function handleLike(postId){
									let button = document.getElementById("likeButtonPost"+postId);
									if(button.innerHTML=="Like"){
										likePost(postId);
									}else if(button.innerHTML=="Unlike"){					
										unlikePost(postId);
									}								
								}
								function handleDislike(postId){
									let button = document.getElementById("dislikeButtonPost"+postId);
									if(button.innerHTML=="Dislike"){
										dislikePost(postId);
									}else if(button.innerHTML="Undislike"){
										undislikePost(postId);
									}
								}
								function handleLikeComment(commentId){
									let button = document.getElementById("likeButton"+commentId);
									if(button.innerHTML=="Like"){
										likeComment(commentId);
									}else if(button.innerHTML=="Unlike"){					
										unlikeComment(commentId);
									}								
								}
								function handleDislikeComment(commentId){
									let button = document.getElementById("dislikeButton"+commentId);
									if(button.innerHTML=="Dislike"){
										dislikeComment(commentId);
									}else if(button.innerHTML="Undislike"){
										undislikeComment(commentId);
									}
								}
								function likePost(postId) {
										let request =new XMLHttpRequest();
										request.onreadystatechange = function() {			
											if(this.readyState == 4 && this.status == 200){
												let button = document.getElementById("likeButtonPost"+postId);
												button.innerHTML="Unlike";
												button.style.backgroundColor  = "red";
												document.getElementById("post"+postId).innerHTML=request.responseText;
												let button2 = document.getElementById("dislikeButtonPost"+postId);
												if(button2.innerHTML=="Undislike"){
													button2.innerHTML="Dislike";
													button2.style.backgroundColor = "red";
												}
											}									
										}
										request.open("post","/MyProject/likePost?postId="+postId,true);
										request.send();				
								}
								function likeComment(commentId) {
									let request =new XMLHttpRequest();
									request.onreadystatechange = function() {			
										if(this.readyState == 4 && this.status == 200){
											let button = document.getElementById("likeButton"+commentId);
											button.innerHTML="Unlike";
											button.style.backgroundColor  = "red";
											document.getElementById("comment"+commentId).innerHTML=request.responseText;
											let button2 = document.getElementById("dislikeButton"+commentId);
											if(button2.innerHTML=="Undislike"){
												button2.innerHTML="Dislike";
												button2.style.backgroundColor = "red";
											}
										}									
									}
									request.open("post","/MyProject/likeComment?commentId="+commentId,true);
									request.send();				
							}
										  function unlikePost(postId) {
												let request =new XMLHttpRequest();	
												request.onreadystatechange = function() {
													if(this.readyState == 4 && this.status == 200){
														let button = document.getElementById("likeButtonPost"+postId);
														button.innerHTML="Like";
														button.style.backgroundColor = "green";
														document.getElementById("post"+postId).innerHTML=request.responseText;
													}												
												}
												request.open("post","/MyProject/unlikePost?postId="+postId,true);
												request.send();
												}
										  function unlikeComment(commentId) {
												let request =new XMLHttpRequest();	
												request.onreadystatechange = function() {
													if(this.readyState == 4 && this.status == 200){
														let button = document.getElementById("likeButton"+commentId);
														button.innerHTML="Like";
														button.style.backgroundColor = "green";
														document.getElementById("comment"+commentId).innerHTML=request.responseText;
													}												
												}
												request.open("post","/MyProject/unlikeComment?commentId="+commentId,true);
												request.send();
												}
										  
										  function dislikePost(postId) {
												let request =new XMLHttpRequest();	
												request.onreadystatechange = function() {
													if(this.readyState == 4 && this.status == 200){
														let button = document.getElementById("dislikeButtonPost"+postId);
														button.innerHTML="Undislike";
														button.style.backgroundColor  = "green";
														document.getElementById("post"+postId).innerHTML=request.responseText;
														let button2 = document.getElementById("likeButtonPost"+postId);
														if(button2.innerHTML=="Unlike"){
															button2.innerHTML="Like";
															button2.style.backgroundColor = "green";
														}
													}												
												}
												request.open("post","/MyProject/dislikePost?postId="+postId,true);
												request.send();
												}
										  function dislikeComment(commentId) {
												let request =new XMLHttpRequest();	
												request.onreadystatechange = function() {
													if(this.readyState == 4 && this.status == 200){
														let button = document.getElementById("dislikeButton"+commentId);
														button.innerHTML="Undislike";
														button.style.backgroundColor  = "green";
														document.getElementById("comment"+commentId).innerHTML=request.responseText;
														let button2 = document.getElementById("likeButton"+commentId);
														if(button2.innerHTML=="Unlike"){
															button2.innerHTML="Like";
															button2.style.backgroundColor = "green";
														}
													}												
												}
												request.open("post","/MyProject/dislikeComment?commentId="+commentId,true);
												request.send();
												}
										  function undislikePost(postId) {
												let request =new XMLHttpRequest();	
												request.onreadystatechange = function() {
													if(this.readyState == 4 && this.status == 200){
														let button = document.getElementById("dislikeButtonPost"+postId);
														button.innerHTML="Dislike";
														button.style.backgroundColor  = "red";
														document.getElementById("post"+postId).innerHTML=request.responseText;
													}												
												}
												request.open("post","/MyProject/undislikePost?postId="+postId,true);
												request.send();
												}		
										  function undislikeComment(commentId) {
												let request =new XMLHttpRequest();	
												request.onreadystatechange = function() {
													if(this.readyState == 4 && this.status == 200){
														let button = document.getElementById("dislikeButton"+commentId);
														button.innerHTML="Dislike";
														button.style.backgroundColor  = "red";
														document.getElementById("comment"+commentId).innerHTML=request.responseText;
													}												
												}
												request.open("post","/MyProject/undislikeComment?commentId="+commentId,true);
												request.send();
												}			
							</script>
		<c:if test="${  sessionScope.postPostPage.comments.size()>0 }">
		<c:forEach items="${  sessionScope.postPostPage.comments }" var="comment" >
			<div>
				<h3><c:out value="${ comment.user.username }"></c:out>
				:DATE:<c:out value="${ comment.dateTime }"></c:out>
				:POINTS:<span id="comment${ comment.comment_id }"><c:out value="${ comment.points }"></c:out></span></h3>
				
				
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
		<button id="likeButton${comment.comment_id}"  style="background-color: green" onclick="handleLikeComment(${comment.comment_id})">Unlike</button>
		</c:if>
		<c:if test="${!isLiked}">
			<button id="likeButton${comment.comment_id}" style="background-color: green" onclick="handleLikeComment(${comment.comment_id})">Like</button>
		</c:if>
		<c:if test="${isDisliked}">
		<a href="/MyProject/undislikeComment?commentId=${  comment.comment_id }&userId=${ sessionScope.user.id }">Undislike</a>	
		<button id="dislikeButton${comment.comment_id}"  style="background-color: green" onclick="handleDislikeComment(${comment.comment_id})">Undislike</button>
		</c:if>
		<c:if test="${!isDisliked}">
			<button id="dislikeButton${comment.comment_id}" style="background-color: red" onclick="handleDislikeComment(${comment.comment_id})">Dislike</button>	
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
								:POINTS:<span id="comment${ childComment.comment_id }"><c:out value="${ childComment.points }"></c:out></span></h5>
								
								
								
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
									<button id="likeButton${childComment.comment_id}"  style="background-color: green" onclick="handleLikeComment(${childComment.comment_id})">Unlike</button>
								</c:if>
								<c:if test="${!isLiked}">
									<button id="likeButton${childComment.comment_id}" style="background-color: green" onclick="handleLikeComment(${childComment.comment_id})">Like</button>
								</c:if>
								<c:if test="${isDisliked}">
								<button id="dislikeButton${childComment.comment_id}"  style="background-color: green" onclick="handleDislikeComment(${childComment.comment_id})">Undislike</button>	
								</c:if>
								<c:if test="${!isDisliked}">
								<button id="dislikeButton${childComment.comment_id}" style="background-color: red" onclick="handleDislikeComment(${childComment.comment_id})">Dislike</button>
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