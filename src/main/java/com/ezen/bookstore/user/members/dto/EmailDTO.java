package com.ezen.bookstore.user.members.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmailDTO {
	private String to; //받는사람
	private String subject; //제목
	private String message;	 // 메일 내용
	private String verifyCode; //인증번호
}
