package com.ezen.bookstore.user.payment.dto;

import lombok.Data;

@Data
public class CompleteDetailDTO {
    private String book_isbn;
    private Integer order_detail_price;
    private Integer order_detail_qty;
}
