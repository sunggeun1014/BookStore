package com.ezen.bookstore.user.payment.controller;

import com.ezen.bookstore.user.cart.service.UserCartService;
import com.ezen.bookstore.user.payment.dto.*;
import com.ezen.bookstore.user.payment.service.PaymentServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user/payment")
@RestController
public class PaymentRestController {

    private final PaymentServiceImpl paymentService;
    private final UserCartService userCartService;

    @PostMapping("/request")
    public ResponseEntity<String> handlePaymentRequest(@RequestBody OrderRequest orderRequest) {
        try {
            CompleteOrderDTO order = new CompleteOrderDTO();
            order.setOrder_num(orderRequest.getOrder_num());
            order.setOrder_addr(orderRequest.getOrder_addr());
            order.setOrder_addr_detail(orderRequest.getOrder_addr_detail());
            order.setCommon_ent_pw(orderRequest.getCommon_ent_pw());
            order.setMember_id(orderRequest.getMember_id());
            order.setRecipient_name(orderRequest.getRecipient_name());
            order.setRecipient_phoneno(orderRequest.getRecipient_phoneno());
            order.setPaymentId(orderRequest.getPaymentId());

            List<CompleteDetailDTO> details = orderRequest.getOrderDetails();

            String paymentId = order.getPaymentId();
            // 1. 토큰 받기
            String token = paymentService.getToken();

            // 2. 결제 사전 등록
//            paymentService.registerPayment(paymentId, completeOrderDTO);

            // 3. 결제검증
            boolean paymentSuccess = paymentService.verifyPayment(paymentId, token);

            Map<String, Object> response = new HashMap<>();

            // ObjectMapper를 사용하여 Map을 JSON 문자열로 변환
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = null;

            if (paymentSuccess) {
                // 4. 결제 완료 처리
                try {
                    if (details.isEmpty()) {
                        paymentService.cancelPayment(paymentId);
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("주문상세못찾음");
                    }

                    paymentService.insertOrder(order, details);
                    response.put("order_num", order.getOrder_num());
                    objectMapper.writeValueAsString(response);
                    jsonResponse = objectMapper.writeValueAsString(response);
                    
                    // 주문, 결제 완료 후 장바구니 삭제
                    if (orderRequest.getCart_num().get(0) != null) {                    	
                    	userCartService.deleteItemsByCartNums(orderRequest.getCart_num(), order.getMember_id());
                    }

                } catch (SQLException e) {
                    try {
                        paymentService.cancelPayment(paymentId);
                    } catch (Exception cancelEx) {
                    	
                    }
                } catch (Exception e) {
                    try {
                        paymentService.cancelPayment(paymentId);
                    } catch (Exception cancelEx) {
                    	
                    }
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("주문 삽입 중 오류 발생, 결제취소");
                }
                return ResponseEntity.ok(jsonResponse);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("결제 검증 실패");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("서버 오류 발생");
        }
    }
}
