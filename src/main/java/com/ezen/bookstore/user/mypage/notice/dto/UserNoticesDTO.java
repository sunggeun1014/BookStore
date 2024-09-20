package com.ezen.bookstore.user.mypage.notice.dto;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class UserNoticesDTO {
	Integer notice_num;
	String notice_title;
	String notice_content;
	String manager_id;
	String notice_visible;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	Timestamp notice_write_date;
	Timestamp notice_start_date;
	Timestamp notice_end_date;
}
