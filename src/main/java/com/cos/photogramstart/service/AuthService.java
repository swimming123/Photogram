package com.cos.photogramstart.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service //Ioc + 트랜젝션 관리 	
public class AuthService {
	
	private final UserRepository userRepasitory;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Transactional //write (insert, update, delete)
	public User 회원가입(User user) {
		//회원가입 진행 
		String rawPassword = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);
		user.setPassword(encPassword);
		user.setRole("ROLE_USER"); //관리자는 ROLE_ADMIN
		User userEntity = userRepasitory.save(user);
		return userEntity;
	}
}
