<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


<html>
<head>
<!-- <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css" integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb" crossorigin="anonymous"> -->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><c:out
		value="${  sessionScope.postPostPage.description }"></c:out></title>
<style type="text/css">
			.video {
				width: 100%;
				height: auto;
			}
			*{
			 font-family:-apple-system,BlinkMacSystemFont,Segoe UI,Helvetica Neue,Arial,Helvetica,Geneva,sans-serif;
			}
			
			#listMainComments{
				list-style-type: none;
				padding: 0;
				margin: 0;
			}
			#listChildComments{
			list-style-type: none;
			}
			#submitButton {
			    background-color: black;	/* Blue */
			    border: none;
			    padding: 15px 32px;
			    text-align: center;
			     color:white;
			    text-decoration: none;
			    font-family:-apple-system,BlinkMacSystemFont,Segoe UI,Helvetica Neue,Arial,Helvetica,Geneva,sans-serif;
			    display: inline-block;
			    font-size: 16px;
			    border-radius:25px;
			}
			.showRepliesButton{
			 background-color: black;	/* Blue */
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
			textarea{
				border-radius: 15px 50px 30px;
				border: 5px solid black;
			}
			.likeButton{
			background-color: black;	/* Blue */
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
			.dislikeButton{
			background-color: black;
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
			.mainDiv{
			 text-align: center;
			  margin: auto;
			    width: 70%;		
			    padding: 10px;
			}
			.tag{
			background-color: black;	/* Blue */
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
			.commentText{
			background-color: #e7e7e7; 
			color: black;
			padding: 3%;
			width:50%;	
			text-align: left;
			margin: auto;
			border-radius:25px;
			border: 5px solid black;
			}
			
</style>
</head>
<body>

	<jsp:include page="headerLogged.jsp"></jsp:include>
	<div class="smallerDiv">
	<div class="mainDiv">
		<h1>
			<c:out value="${  sessionScope.postPostPage.description }"></c:out>
		</h1>
		<br>
		<c:forEach items="${  sessionScope.postPostPage.tags}" var="tag">
			<a href=""><span class="tag"><c:out value="${ tag.tagName }"></c:out></span></a>
		</c:forEach>
		<br>


		<c:choose>
			<c:when test="${sessionScope.postPostPage.video}">
				<video class="video" controls> <source
					src="/MyProject/postpic?pictureUrl=${sessionScope.postPostPage.postUrl}&userName=${sessionScope.userPostPage.username}"
					type="video/mp4"></video>
			</c:when>
			<c:otherwise>
				<img
					src="/MyProject/postpic?pictureUrl=${sessionScope.postPostPage.postUrl}&userName=${sessionScope.userPostPage.username}"
					width="50%" height="auto">
			</c:otherwise>
		</c:choose>

		<br> <span class="tag">Points:<span id="post${sessionScope.postPostPage.postId }"><c:out
				value="${ sessionScope.postPostPage.points }"></c:out></span></span>

		<c:set var="isLiked" value="false" />
		<c:set var="isDisliked" value="false" />
		<c:forEach var="postLiked" items="${sessionScope.likedPosts}">
			<c:if test="${postLiked.postId eq sessionScope.postPostPage.postId }">
				<c:set var="isLiked" value="true" />
			</c:if>
		</c:forEach>
		<c:forEach var="postDisliked" items="${sessionScope.dislikedPosts}">
			<c:if
				test="${postDisliked.postId eq sessionScope.postPostPage.postId }">
				<c:set var="isDisliked" value="true"/>
			</c:if>
		</c:forEach>

		<c:if test="${isLiked}">
			<button id="likeButtonPost${sessionScope.postPostPage.postId}"
				style="background-color: green"
				onclick="handleLike(${sessionScope.postPostPage.postId})" class="likeButton">Unlike</button>
		</c:if>
		<c:if test="${!isLiked}">
			<button id="likeButtonPost${sessionScope.postPostPage.postId}"
				style="background-color: black"
				onclick="handleLike(${sessionScope.postPostPage.postId})" class="likeButton">Like</button>
		</c:if>
		<c:if test="${isDisliked}">
			<button id="dislikeButtonPost${sessionScope.postPostPage.postId}"
				style="background-color: red"
				onclick="handleDislike(${sessionScope.postPostPage.postId})" class="likeButton">Undislike</button>
		</c:if>
		<c:if test="${!isDisliked}">
			<button id="dislikeButtonPost${sessionScope.postPostPage.postId}"
				style="background-color: black"
				onclick="handleDislike(${sessionScope.postPostPage.postId})"class="likeButton">Dislike</button>
		</c:if>

		<p style="display: none;">
			<c:out value="${ sessionScope.postPostPage.postId }"></c:out>
		</p>
		<p style="display: none;">
			<c:out value="${ sessionScope.userPostPage.id }"></c:out>
		</p>
		<h3>
			<c:out value="${sessionScope.postPostPage.comments.size()}"></c:out>
			Comments
		</h3>
					<!-- 		MAINCOMMENTS -->
		<form method="POST" action="/MyProject/addComment">
			<textarea name="commentText" path="" id="commentText"
				rows="6" cols="50" maxlength="1000"
				style="resize: none;" placeholder="Comment here..." required></textarea>
			<input id="submitButton" type="submit" value="Comment" class="showRepliesButton"
				onclick="postComment()">
		</form>
		<script type="text/javascript">					
							
							function showTextArea(commentId) {
							    let x = document.getElementById(commentId);
							    if (x.style.display === "none") {
							        x.style.display = "block";
							    } else {
							        x.style.display = "none";
							    }
							}
									
							function showTextArea2(commentId,buttonId) {
							    let x = document.getElementById(commentId);
							    let button = document.getElementById(buttonId);
							    if (x.style.display === "none") {
							        x.style.display = "block";
							        button.style.display="none"
							    } else {
							        x.style.display = "none";
							    }
							}
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
												button.style.backgroundColor  = "green";
												document.getElementById("post"+postId).innerHTML=request.responseText;
												let button2 = document.getElementById("dislikeButtonPost"+postId);
												if(button2.innerHTML=="Undislike"){
													button2.innerHTML="Dislike";
													button2.style.backgroundColor = "black";
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
											button.style.backgroundColor  = "green";
											document.getElementById("comment"+commentId).innerHTML=request.responseText;
											let button2 = document.getElementById("dislikeButton"+commentId);
											if(button2.innerHTML=="Undislike"){
												button2.innerHTML="Dislike";
												button2.style.backgroundColor = "black";
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
														button.style.backgroundColor = "black";
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
														button.style.backgroundColor = "black";
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
														button.style.backgroundColor  = "red";
														document.getElementById("post"+postId).innerHTML=request.responseText;
														let button2 = document.getElementById("likeButtonPost"+postId);
														if(button2.innerHTML=="Unlike"){
															button2.innerHTML="Like";
															button2.style.backgroundColor = "black";
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
														button.style.backgroundColor  = "red";
														document.getElementById("comment"+commentId).innerHTML=request.responseText;
														let button2 = document.getElementById("likeButton"+commentId);
														if(button2.innerHTML=="Unlike"){
															button2.innerHTML="Like";
															button2.style.backgroundColor = "black";
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
														button.style.backgroundColor  = "black";
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
														button.style.backgroundColor  = "black";
														document.getElementById("comment"+commentId).innerHTML=request.responseText;
													}												
												}
												request.open("post","/MyProject/undislikeComment?commentId="+commentId,true);
												request.send();
												}			
							</script>
		<c:if test="${  sessionScope.postPostPage.comments.size()>0 }">
			<ul id="listMainComments">
				<c:forEach items="${  sessionScope.postPostPage.comments }" var="comment">
					<li id="listMainComment${comment.comment_id }">
						
							<br>
							<div class="commentText">
							<span class ="tag">
								<c:out value="${ comment.user.username }"></c:out>
								:DATE:
								<c:out value="${ comment.dateTime }"></c:out>
								:POINTS:<span id="comment${ comment.comment_id }"><c:out
										value="${ comment.points }"></c:out></span>
							</span>
							<button class="showRepliesButton" onclick="showTextArea(${comment.comment_id})">Reply</button>
							<div style="padding:2%">
								<c:out value="${ comment.comment }"></c:out>
							</div>

							<c:set var="isLiked" value="false" />
							<c:set var="isDisliked" value="false" />
							<c:forEach var="commentLiked"
								items="${sessionScope.likedComments}">
								<c:if test="${commentLiked.comment_id eq comment.comment_id }">
									<c:set var="isLiked" value="true" />
								</c:if>
							</c:forEach>
							<c:forEach var="commentDisliked"
								items="${sessionScope.dislikedComments}">
								<c:if
									test="${ commentDisliked.comment_id eq comment.comment_id }">
									<c:set var="isDisliked" value="true" />
								</c:if>
							</c:forEach>

							<c:if test="${isLiked}">
								<button id="likeButton${comment.comment_id}"
									style="background-color: green"
									onclick="handleLikeComment(${comment.comment_id})" class="likeButton">Unlike</button>
							</c:if>
							<c:if test="${!isLiked}">
								<button id="likeButton${comment.comment_id}"
									style="background-color: black"
									onclick="handleLikeComment(${comment.comment_id})" class="likeButton">Like</button>
							</c:if>
							<c:if test="${isDisliked}">
								<a
									href="/MyProject/undislikeComment?commentId=${  comment.comment_id }&userId=${ sessionScope.user.id }">Undislike</a>
								<button id="dislikeButton${comment.comment_id}" style="background-color: red" class="likeButton"
									onclick="handleDislikeComment(${comment.comment_id})">Undislike</button>
							</c:if>
							<c:if test="${!isDisliked}">
								<button id="dislikeButton${comment.comment_id}"
									style="background-color: black" class="likeButton"
									onclick="handleDislikeComment(${comment.comment_id})">Dislike</button>
							</c:if>
						
							<br>
							<br>
							<form method="POST" action="/MyProject/addChildComment"
								id="${ comment.comment_id}" style="display: none">
								<input type="text" value="${comment.comment_id}"
									name="parentCommentId" style="display: none"> <input
									type="text" value="${ comment.user.id}"
									name="parentCommentUserId" style="display: none">
								<textarea name="commentText" path="" id="commentText"
									 rows="6" cols="50" maxlength="1000"
									style="resize: none;" placeholder="Comment here..." required></textarea>
								<input type="submit" value="Comment" onclick="postComment()" class="showRepliesButton">
							</form>

						</div>
						
							<c:if test="${ comment.childComments.size()!=0 }">
	
								<button class="showRepliesButton" id="showChildCommentsButton${ comment.comment_id }"
									onclick="showTextArea2('childComments${comment.comment_id}','showChildCommentsButton${comment.comment_id }')">View
									${comment.childComments.size()} replies</button>
								<div id="childComments${ comment.comment_id }"
									style="display: none">
									<c:forEach items="${comment.childComments}" var="childComment">
									
											<div class="commentText">
												<span class="tag">
													<c:out value="${ childComment.user.username }"></c:out>
													:DATE:
													<c:out value="${ childComment.dateTime }"></c:out>
													:POINTS:<span id="comment${ childComment.comment_id }"><c:out
															value="${ childComment.points }"></c:out></span>
												</span>
												<button class="showRepliesButton" onclick="showTextArea(${childComment.comment_id})">Reply</button>
												<div style="padding:2%">
													<c:out value="${ childComment.comment }"></c:out>
												</div>

												<c:set var="isLiked" value="false" />
												<c:set var="isDisliked" value="false" />
												<c:forEach var="commentLiked"
													items="${sessionScope.likedComments}">
													<c:if
														test="${commentLiked.comment_id eq childComment.comment_id }">
														<c:set var="isLiked" value="true" />
													</c:if>
												</c:forEach>
												<c:forEach var="commentDisliked"
													items="${sessionScope.dislikedComments}">
													<c:if
														test="${ commentDisliked.comment_id eq childComment.comment_id }">
														<c:set var="isDisliked" value="true" />
													</c:if>
												</c:forEach>

												<c:if test="${isLiked}">
													<button id="likeButton${childComment.comment_id}"
														style="background-color: green" class="likeButton"
														onclick="handleLikeComment(${childComment.comment_id})">Unlike</button>
												</c:if>
												<c:if test="${!isLiked}">
													<button id="likeButton${childComment.comment_id}"
														style="background-color: black" class="likeButton"
														onclick="handleLikeComment(${childComment.comment_id})">Like</button>
												</c:if>
												<c:if test="${isDisliked}">
													<button id="dislikeButton${childComment.comment_id}"
														style="background-color: red" class="likeButton"
														onclick="handleDislikeComment(${childComment.comment_id})">Undislike</button>
												</c:if>
												<c:if test="${!isDisliked}">
													<button id="dislikeButton${childComment.comment_id}"
														style="background-color: black" class="likeButton"
														onclick="handleDislikeComment(${childComment.comment_id})">Dislike</button>
												</c:if>

													<br>
													<br>

												<form method="POST" action="/MyProject/addChildComment"
													id="${ childComment.comment_id }" style="display: none">
													<input type="text" value="${comment.comment_id}"
														name="parentCommentId" style="display: none"> <input
														type="text" value="${ childComment.user.id}"
														name="parentCommentUserId" style="display: none">
													<textarea name="commentText" path="" id="commentText"
														 rows="6" cols="50" maxlength="1000"
														style="resize: none;" placeholder="Comment here..." required></textarea>
													<input class="showRepliesButton" type="submit" value="Comment" onclick="postComment()">
												</form>
											</div>									
									</c:forEach>
								</div>
							</c:if>						
					</li>
				</c:forEach>
			</ul>
		</c:if>
		<c:if test="${  sessionScope.postPostPage.comments.size()==0 }">
			<h2>No comments yet...</h2>
		</c:if>
	</div>
</div>
</body>
</html>