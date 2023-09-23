/**
	2. 스토리 페이지
	(1) 스토리 로드하기
	(2) 스토리 스크롤 페이징하기
	(3) 좋아요, 안좋아요
	(4) 댓글쓰기
	(5) 댓글삭제
 */

// (1) 스토리 로드하기
//(0) 현재 로그인 한 사용자 아이디 
let principalId = $("#principalId").val();

//alert(principalId);

let page = 0;

function storyLoad() {
 $.ajax({
	 url:`api/image?page=${page}`,
	 dataType:"Json"
 }).done(res=>{
	 console.log(res);
	 res.data.content.forEach((image)=>{
		 let storyItem = getStoryItem(image);
		 $("#storyList").append(storyItem);
	 });
 }).fail(error=>{
	 console.log("오류",error);
 });
}

storyLoad();

function getStoryItem(image) {
  let item = `
    <div class="story-list__item">
      <div class="sl__item__header">
        <div>
          <img class="profile-image" src="/upload/${image.user.profileImageUrl}" onerror="this.src='/images/person.jpeg'" />
        </div>
        <div>${image.user.username}</div>
      </div>

      <div class="sl__item__img">
        <img src="/upload/${image.postImageUrl}" />
      </div>

      <div class="sl__item__contents">
        <div class="sl__item__contents__icon">
          <button>
            ${image.likeState
              ? `<i class="fas fa-heart active" id="storyLikeIcon-${image.id}" onclick="toggleLike(${image.id})"></i>`
              : `<i class="far fa-heart" id="storyLikeIcon-${image.id}" onclick="toggleLike(${image.id})"></i>`
            }
          </button>
        </div>

        <span class="like"><b id="storyLikeCount-${image.id}">${image.likeCount}</b> likes</span>

        <div class="sl__item__contents__content">
          <p>${image.caption}</p>
        </div>
        
        <div id="storyCommentList-${image.id}">
          ${generateCommentList(image.comments, image.id)}
        </div>
        
        <div class="sl__item__input">
          <input type="text" placeholder="댓글 달기..." id="storyCommentInput-${image.id}" />
          <button type="button" onClick="addComment(${image.id})">게시</button>
        </div>
        
      </div>
    </div>
  `;

  return item;
}

function generateCommentList(comments, imageId) {
  let commentList = '';
  comments.forEach((comment) => {
    commentList += `
      <div class="sl__item__contents__comment" id="storyCommentItem-${comment.id}">
        <p><b>${comment.user.username} :</b> ${comment.content}</p>
        ${comment.user.id === principalId ? `<button><i class="fas fa-times"></i></button>` : ''}
      </div>
    `;
  });
  return commentList;
}



// (2) 스토리 스크롤 페이징하기
$(window).scroll(() => {
	// console.log("윈도우 scrollTop", $(window).scrollTop());
	// console.log("문서의 높이", $(document).height());
	// console.log("윈도우 높이", $(window).height());
	
	let checkNum = $(window).scrollTop()-($(document).height()-$(window).height());
	console.log(checkNum);
	if(checkNum<1 && checkNum>-1){
		page++;
		storyLoad();
	}
});


// (3) 좋아요, 안좋아요
function toggleLike(imageId) {
	let likeIcon = $(`#storyLikeIcon-${imageId}`);
	
	if (likeIcon.hasClass("far")) { //좋아요하겠다.
		$.ajax({
			type:"post",
			url:`/api/image/${imageId}/likes`,
			dataType:"json"
		}).done(res=>{
			
			let likeCountStr = $(`#storyLikeCount-${imageId}`).text();
			let likeCount = Number(likeCountStr)+1;
			//console.log("좋아요카운트 증가 :", likeCountStr);
			$(`#storyLikeCount-${imageId}`).text(likeCount);
			
			likeIcon.addClass("fas");
			likeIcon.addClass("active");
			likeIcon.removeClass("far");
		}).fail(error=>{
			console.log("오류", error)
		});
		
		
	} else { // 좋아요취소.
		$.ajax({
			type:"delete",
			url:`/api/image/${imageId}/likes`,
			dataType:"json"
		}).done(res=>{
			
			let likeCountStr = $(`#storyLikeCount-${imageId}`).text();
			let likeCount = Number(likeCountStr)-1;
			$(`#storyLikeCount-${imageId}`).text(likeCount);
			
			likeIcon.removeClass("fas");
			likeIcon.removeClass("active");
			likeIcon.addClass("far");
		}).fail(error=>{
			console.log("오류", error)
		});
		
		
	}
}

// (4) 댓글쓰기
function addComment(imageId) {

	let commentInput = $(`#storyCommentInput-${imageId}`);
	let commentList = $(`#storyCommentList-${imageId}`);

	let data = {
		imageId:imageId,
		content: commentInput.val()
	}

	if (data.content === "" || data.content === null) {
	  alert("댓글을 작성해주세요!");
	  return;
	}

	
	$.ajax({
		type:"post",
		url:"/api/comment",
		data:JSON.stringify(data),
		contentType:"application/json;charset=utf-8",
		dataType:"json"
	}).done(res=>{
		//console.log("success!!",res);
		
		let comment = res.data;
		
		let content = `		  
		
			<div class="sl__item__contents__comment" id="storyCommentItem-${comment.id}"> 
			    <p>
			      <b>${comment.user.username} :</b>
			      ${comment.content}
			    </p>
			    <button><i class="fas fa-times"></i></button>
			</div>
		`;
		commentList.prepend(content);//append : 뒤에다가 붙이고 prepend : 앞에다가 붙임 
	}).fail(error=>{
		console.log("오류333",error);
	});

	commentInput.val("");  //인풋필드를 깨끗하게 비워준다.
}

// (5) 댓글 삭제
function deleteComment(commentId) {
	$.ajax({
		type:"delete",
		url:`/api/comment/${commentId}`,
		dataType:"Json"
	}).done(res=>{
		console.log("성공",res);
		$(`#storyCommnetItem-${commentId}`).remove();
	}).fail(error=>{
		console.log("실패",error);
	});
}







