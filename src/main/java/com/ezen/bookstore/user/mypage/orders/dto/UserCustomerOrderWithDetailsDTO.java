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
    private String order_detail_status;
    private String order_delivery_status;
    private Integer order_price_total;
    private Integer order_qty_total;
    private Integer order_detail_num;
    
    private Integer cancellationAmount;
    private Integer refundAmount;
    
    private String recipient_name;
    private String recipient_phoneno;
    private String order_addr;
    private String order_addr_detail;
}
