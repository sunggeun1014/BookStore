package com.ezen.bookstore.user.payment.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserCustomerOrdersDTO {
    private Integer order_num;
    private String order_addr;
    private String order_addr_detail;
    private String common_ent_pw;
    private String order_delivery_status;
    private String order_payment_status;
    private Timestamp order_purchase_date;
    private Timestamp order_modify_date;
    private String member_id;
    private String retrieve_addr;
    private String retrieve_addr_detail;
}