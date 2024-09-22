package com.ezen.bookstore.user.payment.service;

import com.ezen.bookstore.user.payment.dto.PaymentRequestDTO;
import com.ezen.bookstore.user.payment.dto.UserOrderDTO;
import com.ezen.bookstore.user.payment.dto.UserOrderDetailsDTO;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.Payment;

import java.io.IOException;
import java.net.http.HttpHeaders;
import java.util.List;

public interface PaymentService {

    public UserOrderDTO getOrder(int order_num, String member_id);
    public List<UserOrderDetailsDTO> getOrderDetail(int order_num);
    public PaymentRequestDTO processPayment(PaymentRequestDTO paymentRequestDTO) throws IamportResponseException;
    public void verifyPayment(String paymentId) throws Exception;

}
