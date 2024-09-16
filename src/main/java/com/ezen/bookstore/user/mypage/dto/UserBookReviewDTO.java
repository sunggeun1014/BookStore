package com.ezen.bookstore.user.mypage.dto;

import java.sql.Timestamp;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class UserBookReviewDTO {
	String book_isbn;
	String book_name;
	String book_author;
	
	String member_id;
	
	Timestamp order_purchase_date;
	
	Long review_num;
	Integer order_detail_num;
	String review_content;
	Integer review_rating;
	Timestamp review_write_date;	
	
	Timestamp startDate;
	Timestamp endDate;
}
