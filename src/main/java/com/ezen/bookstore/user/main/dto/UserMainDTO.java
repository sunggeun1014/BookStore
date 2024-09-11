package com.ezen.bookstore.user.main.dto;

import java.util.Date;

import lombok.Data;

@Data
public class UserMainDTO {
	private String book_isbn;
	private String book_name;
	private String book_author;
	private String book_intro;
	private String book_thumbnail_changed;
	private Date book_publish_date;
	private Integer review_rating;
	private Integer order_detail_qty;
}
