package com.ezen.bookstore.admin.reviews.dto;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class ReviewsDTO {
	private Integer review_num;
	private String member_id;
	private String book_isbn;
	private String book_name;
	private String review_content;
	private Integer review_rating;
	private Integer order_detail_num;
	
	private Timestamp review_write_date;
}

