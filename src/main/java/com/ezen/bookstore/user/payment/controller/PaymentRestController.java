package com.ezen.bookstore.user.payment.controller;

import com.ezen.bookstore.user.payment.dto.PaymentRequestDTO;
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

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user/payment")
@RestController
public class PaymentRestController {

    private final PaymentServiceImpl paymentService;

    @Value("${import.v2.api}")
    private String apiSecret;


    @PostMapping("/request")
    public ResponseEntity<String> handlePaymentRequest(@RequestBody PaymentRequestDTO paymentRequestDTO) {
        try {
            String paymentId = paymentRequestDTO.getPaymentId();
            // 1. 토큰 받기
            String token = paymentService.getToken();

            // 2. 결제 사전 등록
            paymentService.registerPayment(paymentId, paymentRequestDTO);

            // 3. 결제검증
            boolean paymentSuccess = paymentService.verifyPayment(paymentRequestDTO.getPaymentId(), token);

            if (paymentSuccess) {
                // 4. 결제 완료 처리
                return ResponseEntity.ok("결제 완료 성공");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("결제 검증 실패");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 발생");
        }
    }
}
