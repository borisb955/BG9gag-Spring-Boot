<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>  
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><c:out value="${ postPostPage.description }"></c:out></title>

		<style>
			.mainDiv{
			 text-align: center;
			  margin: auto;
			    width: 40%;		
			    padding: 10px;
			}
			
			.likeButton{
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
			
			.tag{
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
			hr.vertical
			{
			   width: 0px;
			   height: 100%; /* or height in PX */
			    display: inline;
			   margin-top: 100px;
			
			    margin-right: 4%;
			    margin-left: 4%;
			} 
			.infoPost{
		 background-color: black;	/* Blue */
			    border: none;
			    padding: 7px 16px;
			    text-align: center;
			    text-decoration: none;
			    font-family:-apple-system,BlinkMacSystemFont,Segoe UI,Helvetica Neue,Arial,Helvetica,Geneva,sans-serif;
			    color:white;
			    display: block;
			    font-size: 16px;
			    border-radius:25px;
		}
			
		.commentText{
			background-color: #e7e7e7; 
			color: black;
			padding: 3%;
			width:100%;	
			text-align: left;
			margin: auto;
			border-radius:25px;
			border: 5px solid black;
			}
			.likeButtonsCenter{	
			 text-align: center;
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
			
		</style>
</head>
<body>


		<jsp:include page="headerNotLogged.jsp"></jsp:include>
			<div class="mainDiv">
		<h1><c:out value="${  sessionScope.postPostPage.description }"></c:out></h1><br>
		<c:forEach items="${  sessionScope.postPostPage.tags}" var="tag" >
						<a href=""><span class="tag"><c:out value="${ tag.tagName }"></c:out></span></a>
		</c:forEach>
		<br>
		<img src="/MyProject/postpic?pictureUrl=${sessionScope.postPostPage.postUrl}&userName=${sessionScope.userPostPage.username}" width="50%" height="auto">
		<br>
		<span class="tag">Points:<c:out value="${ sessionScope.postPostPage.points }"></c:out></span>
		<button id="likeButtonPost${sessionScope.postPostPage.postId}" style="background-color: black" onclick="handleLike(${sessionScope.postPostPage.postId})" class="likeButton">Like</button>
		<button id="dislikeButtonPost${sessionScope.postPostPage.postId}" style="background-color: black" onclick="handleDislike(${sessionScope.postPostPage.postId})"class="likeButton">Dislike</button>
		<p style="display: none;"><c:out value="${ sessionScope.postPostPage.postId }"></c:out></p>
		<p style="display: none;"><c:out value="${ sessionScope.userPostPage.id }"></c:out></p>
		<hr>
		<span class="likeButton">Comments:</span>
		<br>
		<br>
	<script type="text/javascript">
		
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
		<c:forEach items="${  sessionScope.postPostPage.comments }" var="comment" >
			<div class="commentText">
		<div class="infoPost">
					<s:message code="user"></s:message> <c:out value="${ comment.user.username }"></c:out>				
					<hr class="vertical">				
					<c:out value="${ comment.dateTime }"></c:out>						 						 						 
					<hr  class="vertical">											
					<s:message code="points"></s:message><c:out value="${  comment.points }"></c:out>								
		</div>
			<c:out value="${ comment.comment }"></c:out> <br>
			<div class="likeButtonsCenter">
			<button id="likeButton${comment.comment_id}"
									style="background-color: black"
									onclick="handleLikeComment(${comment.comment_id})" class="likeButton">Like</button>
		<button id="dislikeButton${comment.comment_id}"
									style="background-color: black" class="likeButton"
									onclick="handleDislikeComment(${comment.comment_id})">Dislike</button>
		</div>
			</div>
				<c:if test="${ comment.childComments.size()!=0 }">
				<button class="showRepliesButton" id="showChildCommentsButton${ comment.comment_id }" onclick="showTextArea2('childComments${comment.comment_id}','showChildCommentsButton${comment.comment_id }')">View ${comment.childComments.size()} replies</button>
				<div id="childComments${ comment.comment_id }" style="display: none">
				<c:forEach items="${comment.childComments}" var="childComment">		
				<div class="commentText">
				<div class="infoPost">		
					<s:message code="user"></s:message> <c:out value="${ comment.user.username }"></c:out>				
					<hr class="vertical">				
					<c:out value="${ comment.dateTime }"></c:out>						 						 						 
					<hr  class="vertical">											
					<s:message code="points"></s:message><c:out value="${  comment.points }"></c:out>							
							</div>
							<c:out value="${ childComment.comment }"></c:out>
							<div class="likeButtonsCenter">
			<button id="likeButton${comment.comment_id}"
									style="background-color: black"
									onclick="handleLikeComment(${comment.comment_id})" class="likeButton">Like</button>
		<button id="dislikeButton${comment.comment_id}"
									style="background-color: black" class="likeButton"
									onclick="handleDislikeComment(${comment.comment_id})">Dislike</button>
		</div>
					</div>
				</c:forEach>
				</div>
				<br>
				<hr>
				<br>
			</c:if>
			<br>
			<br>
		</c:forEach>
		</c:if>
		<c:if test="${  sessionScope.postPostPage.comments.size()==0 }">
		<h2>No comments yet...</h2>
		</c:if>
		</div>
		
</body>
</html>