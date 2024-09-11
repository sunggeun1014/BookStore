package com.ezen.bookstore.user.members.dto;

import java.sql.Timestamp;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class UserMembersDTO {
	String member_id;
	String member_pw;
	String member_name;
	String member_email;
	String member_phoneNo;
	String member_addr;
	String member_detail_addr;
	
	Timestamp member_date;
	String naver_login_cd;
	String kakao_login_cd;	

}
