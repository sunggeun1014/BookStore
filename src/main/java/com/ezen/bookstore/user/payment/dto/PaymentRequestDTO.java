package com.ezen.bookstore.user.payment.dto;

import lombok.Data;

@Data
public class PaymentRequestDTO {
    private String paymentId;
    private String storeId;
    private String customerId;
    private String orderName;
    private Integer totalAmount;
}
