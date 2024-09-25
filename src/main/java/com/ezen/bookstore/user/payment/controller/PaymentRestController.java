package com.ezen.bookstore.user.payment.controller;

import com.ezen.bookstore.user.payment.dto.CompleteOrderDTO;
import com.ezen.bookstore.user.payment.dto.PaymentRequestDTO;
import com.ezen.bookstore.user.payment.dto.UserOrderDetailsDTO;
import com.ezen.bookstore.user.payment.service.PaymentService;
import com.ezen.bookstore.user.payment.service.PaymentServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.SQLException;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user/payment")
@RestController
public class PaymentRestController {

    private final PaymentServiceImpl paymentService;

    @PostMapping("/request")
    public ResponseEntity<String> handlePaymentRequest(@RequestBody CompleteOrderDTO completeOrderDTO) {
        try {
            String paymentId = completeOrderDTO.getPaymentId();
            // 1. 토큰 받기
            String token = paymentService.getToken();

            // 2. 결제 사전 등록
//            paymentService.registerPayment(paymentId, completeOrderDTO);

            // 3. 결제검증
            boolean paymentSuccess = paymentService.verifyPayment(paymentId, token);

            if (paymentSuccess) {
                // 4. 결제 완료 처리
                log.info("결제 성공, 주문 삽입 시작");
                try {
                    paymentService.insertOrder(completeOrderDTO);
                } catch (SQLException e) {
                    log.error("SQL 오류 발생: {}", e.getMessage());
                } catch (Exception e) {
                    log.error("주문 삽입 중 오류 발생: {}", e.getMessage());
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("주문 삽입 중 오류 발생");
                }
                return ResponseEntity.ok("결제 완료 성공");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("결제 검증 실패");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 발생");
        }
    }
}
