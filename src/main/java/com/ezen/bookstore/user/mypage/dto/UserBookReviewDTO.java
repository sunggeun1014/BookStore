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
	
	Timestamp order_purchase_date;
	
	String review_content;
	Integer review_rating;
	Timestamp review_write_date;
}
