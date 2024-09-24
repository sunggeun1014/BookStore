package com.ezen.bookstore.user.members.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
public class EmailDTO {
	String to; //받는사람
	String subject; //제목
	String message;	 // 메일 내용
	String verifyCode; //인증번호
}
