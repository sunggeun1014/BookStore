package com.ezen.bookstore.user.payment.dto;

import lombok.Data;

@Data
public class UserCustomerOrderDetailsDTO {
    private Integer order_detail_num;
    private Integer order_detail_qty;
    private String order_detail_status;
    private Integer order_num;
    private String book_isbn;
    private Integer order_detail_price;
    private Integer order_request_qty;
}
