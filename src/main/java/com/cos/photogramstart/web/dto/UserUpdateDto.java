package com.cos.photogramstart.web.dto;

import javax.validation.constraints.NotBlank;

import com.cos.photogramstart.domain.user.User;

import lombok.Data;

@Data
public class UserUpdateDto {
	@NotBlank
	private String name; //필수 
	@NotBlank
	private String password;//필수 
	private String website;
	private String bio;
	private String phone;
	private String gender;
	
	//위험한 엔티티 (필수가 아닌 정보를 만들어서)
	public User toEntity() {
		return User.builder()
				.name(name) // 이름을 기재 안했으면 문제! validation 체크!!
				.password(password) //패스워드를 안넣었을 경우 공백으로 들어오게 되는데 > 이게 문제가 됨! validation체크!
				.website(website)
				.bio(bio)
				.phone(phone)
				.gender(gender)
				.build();
	}
}
