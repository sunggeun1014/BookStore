package com.ezen.bookstore.user.payment.dto;

import lombok.Data;

@Data
public class UserOrderDetailsDTO {
    private String book_thumbnail_changed;
    private String book_name;
    private String book_isbn;
    private Integer order_detail_price;
    private Integer order_detail_qty;
    private String order_detail_status;
    private Integer order_num;
}
