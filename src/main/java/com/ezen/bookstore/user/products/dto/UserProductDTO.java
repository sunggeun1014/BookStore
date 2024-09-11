package com.ezen.bookstore.user.products.dto;

import java.sql.Timestamp;

import lombok.Data;


@Data
public class UserProductDTO {

    private String book_isbn;
    private String book_name;
    private Integer book_price;
    private String book_publisher; 
    private String book_author;
    private String book_intro;
    private String book_country_type;
    private String book_category;
    private String book_state;
    private Integer review_rating_cnt;
    private Double reviews_rating_avg;
    private String book_thumbnail_changed;
    private Timestamp book_publish_date;
    
    private String book_state;
    private Integer book_qty;
}
