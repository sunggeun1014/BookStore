package com.ezen.bookstore.user.mypage.orders.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class UserCustomerOrderWithDetailsDTO {
	
    private Integer order_num;
    private Timestamp order_purchase_date;
    private String book_thumbnail_changed;
    private String book_name;
    private Integer order_detail_qty;
    private Integer order_detail_price;
}
