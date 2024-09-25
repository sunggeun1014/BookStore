package com.ezen.bookstore.user.payment.service;

import com.ezen.bookstore.user.payment.dto.CompleteOrderDTO;
import com.ezen.bookstore.user.payment.dto.PaymentRequestDTO;
import com.ezen.bookstore.user.payment.dto.UserOrderDTO;
import com.ezen.bookstore.user.payment.dto.UserOrderDetailsDTO;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface PaymentService {

    public UserOrderDTO getOrder(int order_num, String member_id);
    public List<UserOrderDetailsDTO> getOrderDetail(int order_num);
    public String getToken () throws Exception;
    public String registerPayment (String paymentId, CompleteOrderDTO completeOrderDTO) throws IOException, InterruptedException;
    public boolean verifyPayment (String paymentId, String token) throws Exception;
    public void insertOrder (CompleteOrderDTO completeOrderDTO) throws SQLException;

}
