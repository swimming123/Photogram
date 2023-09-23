package com.cos.photogramstart.domain.user;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import com.cos.photogramstart.domain.image.Image;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity//디비에 테이블 생성 
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //번호 증가 전략이 데이터베이스 따라감 
	private int id; 
	
	@Column(length = 30, unique = true)
	private String username;
	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	private String name; // 이름
	private String website; // 자기 홈페이지
	private String bio; // 자기소개
	@Column(nullable = false)
	private String email;
	private String phone;
	private String gender;
	
	private String profileImageUrl;
	//private String provider; // 제공자 Google, Facebook, Naver
	
	private String role; // USER, ADMIN
	
	//나는 연관관계의 주인이 아님. 그러므로 테이블에 컬럼을 만들지 마! 
	//+ User를 Select 할 떄 해당 User id로 등록된 image들을 다 가져와!
	//Lazy = User 를 Select 할 때 해당 user id로 등록된 이미지들을 가져오지마. - 대신 getImages()함수의 image들이 호출될 때 가져와!
	//Eager = User를 Select 할 때 해당 User id 로 등록된 이미지들을 전부 Join 해서 가져와.
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY) 
	@JsonIgnoreProperties({"user"})
	private List<Image> images; //양방향매핑 
	
	private LocalDateTime createDate;
	
	@PrePersist // DB에 Insert 되기 직전에 실행
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}

	
	//@OneToMany(mappedBy = "user")
	//private List<Image> images;
	
	//@CreationTimestamp
	//private Timestamp createDate;

}
