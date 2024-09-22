package com.ezen.bookstore.user.payment.dto;

import lombok.Data;

@Data
public class PaymentRequestDTO {
    private String paymentId;
    private String merchant_uid;
    private String orderName;
    private Integer amount;
    private String status;
}
