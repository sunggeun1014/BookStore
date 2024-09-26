package com.ezen.bookstore.user.payment.service;

import com.ezen.bookstore.user.payment.dto.*;
import com.ezen.bookstore.user.payment.repository.PaymentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
//@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private HttpClient httpClient;
    private final ObjectMapper mapper;

    @Value("${import.v2.api}")
    private String apiSecret;

    public PaymentServiceImpl(PaymentRepository paymentRepository, ObjectMapper mapper) {
        this.paymentRepository = paymentRepository;
        this.mapper = mapper;
        this.httpClient = HttpClient.newHttpClient(); // HttpClient 초기화
    }

    @Override
    public UserOrderDTO getOrder(int order_num, String member_id) {
        return paymentRepository.getOrder(order_num, member_id);
    }
    @Override
    public List<UserOrderDetailsDTO> getOrderDetail(int order_num) {
        return paymentRepository.getDetails(order_num);
    }

    @Override
    public String getToken() throws IOException, InterruptedException {
        log.info("토큰요청시작");
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.portone.io/login/api-secret"))
                .header("Content-Type", "application/json")
                .header("Authorization", "PortOne " + apiSecret)
                .method("POST", HttpRequest.BodyPublishers.ofString("{\"apiSecret\":\"" + apiSecret + "\"}"))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        log.info("토큰: {}", response.body());

        return parseTokenFromResponse(response.body());
    }

    private String parseTokenFromResponse(String response) throws JsonProcessingException {
        JsonNode node = mapper.readTree(response);
        String accessToken = node.get("accessToken").asText();

        log.info("Access Token 파싱 완료: {}", accessToken);

        return accessToken;
    }

    @Override
    public String registerPayment(String paymentId, CompleteOrderDTO completeOrderDTO) throws IOException, InterruptedException {
        log.info("결제 등록 요청 시작... Payment ID: {}", paymentId);

        Map<String, Object> data = new HashMap<>();
//        data.put("storeId", storeId);
//        data.put("paymentId", paymentId);
//        data.put("totalAmount", completeOrderDTO.getTotalAmount());
//        data.put("currency", "KRW");

        String requestBody = mapper.writeValueAsString(data);
        log.info("결제 등록 요청 본문: {}", requestBody);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.portone.io/payments/" + paymentId + "/pre-register"))
                .header("Content-Type", "application/json")
                .header("Authorization", "PortOne " + apiSecret)  // 인증 추가
                .method("POST", HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        log.info("결제사전등록?: {}", response.body());

        return response.body();
    }

    @Override
    public boolean verifyPayment(String paymentId, String token) throws Exception {
        log.info("결제 검증 요청 시작... Payment ID: {}", paymentId);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.portone.io/payments/" + paymentId))
                .header("Content-Type", "application/json")
                .header("Authorization", "Bearer " + token)
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        log.info("결제 검증 응답: {}", response.body());

        JsonNode jsonResponse = mapper.readTree(response.body());
        String status = jsonResponse.get("status").asText();
        log.info("결제 상태 확인: {}", status);

        // 결제 상태가 'PAID'일 때만 true 반환
        return "PAID".equals(status);
    }

    @Transactional
    @Override
    public void insertOrder(CompleteOrderDTO completeOrder, List<CompleteDetailDTO> completeDetailList) throws SQLException {
        // 1. order 테이블에 주문 삽입
        try {
            log.info("주문 삽입 요청: {}", completeOrder);
            paymentRepository.insertOrder(completeOrder);
            log.info("주문 삽입 완료: order_num={}", completeOrder.getOrder_num());
        } catch (SQLException e) {
            log.error("주문 삽입 중 오류 발생: {}", e.getMessage());
            throw e; // 예외를 상위 레이어에 전달
        }

        // 2. 주문 상세 삽입
        for (CompleteDetailDTO detail : completeDetailList) {
            detail.setOrder_num(completeOrder.getOrder_num());
            log.info("주문 상세 삽입 요청: {}", detail);
            try {
                paymentRepository.insertOrderDetail(detail);
                log.info("주문 상세 삽입 완료: order_num={}, book_isbn={}", detail.getOrder_num(), detail.getBook_isbn());
            } catch (SQLException e) {
                log.error("주문 상세 삽입 중 오류 발생: {}", e.getMessage());
                throw e; // 예외를 상위 레이어에 전달
            }
        }
    }


    @Override
    public void cancelPayment(String paymentId) throws Exception {
        log.info("결제 취소 요청: paymentId={}", paymentId);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.portone.io/payments/" + paymentId + "/cancel"))
                .header("Content-Type", "application/json")
                .header("Authorization", "PortOne " + apiSecret)
                .method("POST", HttpRequest.BodyPublishers.ofString("{\"reason\":\"주문오류\"}"))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

        response.body();
    }
}
