package com.ezen.bookstore.user.payment.dto;

import lombok.Data;

@Data
public class CompleteDetailDTO {
    private Integer order_detail_num;
    private Integer order_detail_qty;
    private Integer order_num;
    private String book_isbn;
    private Integer order_detail_price;
}
