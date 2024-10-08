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
    
    private Integer order_request_qty;
    private Integer order_complete_qty;
    
    private String member_phoneno;
    private String member_name;
    private String recipient_name;     
    private String recipient_phoneno; 
    private String order_addr;
    private String order_addr_detail;
    
    private String retrieve_name;
    private String retrieve_addr;
    private String retrieve_addr_detail;
    private String retrieve_phoneNo;
    private String common_ent_pw;
    private String retrieve_common_cnt_pw;
    private String book_isbn;
    
}
