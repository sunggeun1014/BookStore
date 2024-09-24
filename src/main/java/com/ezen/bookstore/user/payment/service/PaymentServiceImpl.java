package com.ezen.bookstore.user.payment.service;

import com.ezen.bookstore.user.payment.dto.PaymentRequestDTO;
import com.ezen.bookstore.user.payment.dto.UserOrderDTO;
import com.ezen.bookstore.user.payment.dto.UserOrderDetailsDTO;
import com.ezen.bookstore.user.payment.repository.PaymentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
//@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final RestTemplate restTemplate;
    private HttpClient httpClient;
    private final ObjectMapper mapper;

//    @Value("${import.api.key}")
//    private String apiKey;
//
//    @Value("${import.api.secret}")
//    private String apiSecret;

    @Value("${import.v2.api}")
    private String apiSecret;

    @Value("${import.store.id}")
    private String storeId;

    @Value("${import.id}")
    private String iamId;

    public PaymentServiceImpl(PaymentRepository paymentRepository, RestTemplate restTemplate, ObjectMapper mapper) {
        this.paymentRepository = paymentRepository;
        this.restTemplate = restTemplate;
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
    public String getToken(String apiKey) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.portone.io/login/api-secret"))
                .header("Content-Type", "application/json")
                .header("Authorization", "PortOne " + apiSecret)
                .method("POST", HttpRequest.BodyPublishers.ofString("{\"apiSecret\":\"" + apiSecret + "\"}"))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return parseTokenFromResponse(response.body());
    }

    private String parseTokenFromResponse(String response) throws JsonProcessingException {
        JsonNode node = mapper.readTree(response);
        return node.get("accessToken").asText();
    }

    @Override
    public String registerPayment(String paymentId, PaymentRequestDTO paymentRequestDTO) throws IOException, InterruptedException {
        Map<String, Object> data = new HashMap<>();
        data.put("storeId", storeId);
        data.put("paymentId", paymentId);
        data.put("totalAmount", paymentRequestDTO.getTotalAmount());
        data.put("currency", "KRW");

        String requestBody = mapper.writeValueAsString(data);
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

        // 결제 상태가 'PAID'일 때만 true 반환
        return "PAID".equals(status);
    }


}
