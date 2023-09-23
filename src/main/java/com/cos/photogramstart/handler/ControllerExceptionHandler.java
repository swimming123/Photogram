package com.cos.photogramstart.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.util.Script;
import com.cos.photogramstart.web.dto.CMRespDto;

@RestController
@ControllerAdvice
public class ControllerExceptionHandler {
	
	@ExceptionHandler(CustomValidationException.class)
	public String validationException(CustomValidationException e) {
	//CMRespDto<?> validationException(CustomValidationException e) { //? 으로 선언한 이유 : 뭘로 리턴할 수 알 수 없을 때 (e.getErrorMap자리 )
		
		//CMRespDto, script 비교 
		// 클라이언트에게 응답할때는 스크립트가 좋음
		// ajax, android > CMRespDto가 낫다 
		
		if(e.getErrorMap() == null) {
			return Script.back(e.getMessage());
		}else {
			return Script.back(e.getErrorMap().toString());
			// 스크립트응답 : Script.back(e.getErrorMap().toString());
					//new CMRespDto<Map<String,String>>(-1, e.getMessage(), e.getErrorMap());
		}
	}
	
	@ExceptionHandler(CustomException.class)
	public String exception(CustomException e) {
		return Script.back(e.getMessage());
	}
	
	@ExceptionHandler(CustomApiException.class)
	public ResponseEntity<?> apiException(CustomApiException e) { 
		return new ResponseEntity<>(new CMRespDto<>(-1, e.getMessage(), null),HttpStatus.BAD_REQUEST);
	}
}
