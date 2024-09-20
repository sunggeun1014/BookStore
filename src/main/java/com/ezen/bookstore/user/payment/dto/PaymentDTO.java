package com.ezen.bookstore.user.payment.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class PaymentDTO {
    private String paymentId;
    private String orderName;
    private Integer totalAmount;
    private String currency;
    private String payMethod;
    private String payment_status;
    private String order_num;
    private String member_id;
    private String book_isbn;
    private String transactionId; // 거래 ID
    private Timestamp createdAt; // 생성일시
    private Timestamp updatedAt; // 수정일시
}
