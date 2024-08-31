package com.ezen.bookstore.admin.inquiries.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class InquiriesDTO {
	private Integer inquiry_num; 
	private String inquiry_title;
	private String inquiry_content;
	private String member_id;
	private Timestamp inquiry_write_date;
	private String inquiry_answer_status;
	private String inquiry_type;
	private Integer order_num;
	private String inquiries_original;
	private String inquiries_changed;
	
	private String answer_content;
	private Timestamp answer_write_date;
	private String manager_id;
}
