package com.ezen.bookstore.user.products.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;

@Data
public class UserReviewDTO {
    private String book_isbn;
    private String member_id;
    private String review_content;
    private Integer review_rating;
    private Integer review_count;
    private String review_percentage;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Timestamp review_write_date;
}
