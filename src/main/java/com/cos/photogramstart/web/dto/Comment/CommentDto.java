package com.cos.photogramstart.web.dto.Comment;

import lombok.Data;

@Data
public class CommentDto {
	private String content;
	private int imageId;
	
	//toEntity가 필요 없다.
}
