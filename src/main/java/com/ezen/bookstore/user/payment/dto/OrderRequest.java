package com.ezen.bookstore.user.payment.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderRequest {
    private Integer order_num;
    private String order_addr;
    private String order_addr_detail;
    private String common_ent_pw;
    private String member_id;
    private String recipient_name;
    private String recipient_phoneno;
    private String paymentId;
    private List<Integer> cart_num;
    private List<CompleteDetailDTO> orderDetails;

}
