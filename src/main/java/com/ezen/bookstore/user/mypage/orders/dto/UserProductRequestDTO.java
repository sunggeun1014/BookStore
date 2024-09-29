package com.ezen.bookstore.user.mypage.orders.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class UserProductRequestDTO {
	private Integer request_num;
	private String request_status;
	private Integer request_complete_qty;
	private Integer order_detail_num;
	
	private Integer order_num;
	private String book_name;
	private String book_isbn;
	private String book_thumbnail_changed;
	private Integer order_detail_price;
	private Timestamp order_purchase_date;
}