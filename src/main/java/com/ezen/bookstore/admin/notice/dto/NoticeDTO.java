package com.ezen.bookstore.admin.notice.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class NoticeDTO {
	
	private Integer notice_num;
	private String notice_title;
	private String notice_content;
	private String manager_id;
	private String notice_visible;
	private Timestamp notice_write_date;
	
	
	private Integer notice_detail_num;
	private String notice_detail_original;
	private String notice_detail_changed;
}
