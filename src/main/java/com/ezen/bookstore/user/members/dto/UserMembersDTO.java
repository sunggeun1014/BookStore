package com.ezen.bookstore.user.members.dto;

import java.sql.Timestamp;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class UserMembersDTO {
	String member_id;
	private String member_pw;
	private String member_name;
	private String member_email;
	private String member_phoneNo;
	private String member_addr;
	private String member_detail_addr;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	Timestamp member_date;
	String naver_login_cd;
	String kakao_login_cd;	

}
