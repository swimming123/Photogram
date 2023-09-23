package com.cos.photogramstart.domain.image;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.PrePersist;
import javax.persistence.Transient;

import com.cos.photogramstart.domain.comment.Comment;
import com.cos.photogramstart.domain.likes.Likes;
import com.cos.photogramstart.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Image { //N, 1
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id; 
	// 해당 이미지를 설명하는 영역 (캡션)
	private String caption;
	// 사진을 전송받아서 그 사진을 서버의 특정 폴더에 저장 > DB에 저장된 경로를 insert함
	private String postImageUrl; 
	
	@JsonIgnoreProperties({"images"})
	@JoinColumn(name="userId")
	@ManyToOne(fetch=FetchType.EAGER) //이미지를 select하면 조인해서 User정보를 같이 들고 옴 
	private User user; // 1, 1
	
	//이미지 좋아요 
	@JsonIgnoreProperties({"image"})
	@OneToMany(mappedBy = "image")
	private List<Likes> likes;
		
	//댓글 
	@OrderBy("id DESC")
	@JsonIgnoreProperties({"image"})
	@OneToMany(mappedBy = "image")
	private List<Comment> comments;
	
	private LocalDateTime createDate;
	
	@Transient//DB에 컬럼이만들어지지 않는다.
	private boolean likeState;
	
	@Transient
	private int likeCount;
	
	@PrePersist // DB에 Insert 되기 직전에 실행
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}
//  오브젝트를 콘솔에 출력할 때 문제가 될 수 있어서 User 부분을 출력되지 않게 함 
//	@Override
//	public String toString() {
//		return "Image [id=" + id + ", caption=" + caption + ", postImageUrl=" + postImageUrl 
//				+ ", createDate=" + createDate + "]";
//	}
	
//	public String getPostImageUrl() {
//        // postImageUrl 값을 콘솔에 출력
//        System.out.println("Post Image URL: " + postImageUrl);
//        //Post Image URL: 9fa8cfca-1b78-4d4c-8896-7181222d7879_포토그램.png
//        return postImageUrl;
//    }

	
}
