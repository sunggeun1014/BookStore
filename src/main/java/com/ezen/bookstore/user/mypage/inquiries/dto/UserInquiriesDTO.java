package com.ezen.bookstore.user.mypage.inquiries.dto;

import java.sql.Timestamp;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class UserInquiriesDTO {
	Integer inquiry_num;
	String inquiry_title;
	String inquiry_content;
	String member_id;
	Timestamp inquiry_write_date;
	String inquiry_answer_status;
	String inquiry_type;
	String inquiries_original;
	String inquiries_changed;
	Integer order_num;
	Timestamp order_purchase_date;
	Integer order_detail_num;
	Integer order_detail_qty;
	Integer order_request_qty;
	String answer_content;
	Timestamp answer_write_date;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	Timestamp startDate;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	Timestamp endDate;
	
	String book_name;
	
	List<UserInquiriesBookDTO> books;
	
}
