package com.ezen.bookstore.user.payment.service;

import com.ezen.bookstore.user.payment.dto.PaymentRequestDTO;
import com.ezen.bookstore.user.payment.dto.UserOrderDTO;
import com.ezen.bookstore.user.payment.dto.UserOrderDetailsDTO;

import java.io.IOException;
import java.util.List;

public interface PaymentService {

    public UserOrderDTO getOrder(int order_num, String member_id);
    public List<UserOrderDetailsDTO> getOrderDetail(int order_num);
    public String getToken (String apiKey) throws Exception;
    public String registerPayment (String paymentId, PaymentRequestDTO paymentRequestDTO) throws IOException, InterruptedException;
    public boolean verifyPayment (String paymentId, String token) throws Exception;

}
