package com.ezen.bookstore.user.payment.service;

import com.ezen.bookstore.user.payment.dto.ConnectAPIHeader;
import com.ezen.bookstore.user.payment.dto.PaymentRequestDTO;
import com.ezen.bookstore.user.payment.dto.UserOrderDTO;
import com.ezen.bookstore.user.payment.dto.UserOrderDetailsDTO;
import com.ezen.bookstore.user.payment.repository.PaymentRepository;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import com.siot.IamportRestClient.response.IamportResponse;
import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;

    @Value("${import.api.key}")
    private String apiKey;

    @Value("${import.api.secret}")
    private String apiSecret;

    @Value("${import.v2.api}")
    private String v2Api;

    @Value("${import.id}")
    private String iamId;

    private final RestTemplate restTemplate;

    private ConnectAPIHeader connectAPI;

    @Override
    public UserOrderDTO getOrder(int order_num, String member_id) {
        return paymentRepository.getOrder(order_num, member_id);
    }
    @Override
    public List<UserOrderDetailsDTO> getOrderDetail(int order_num) {
        return paymentRepository.getDetails(order_num);
    }

    @Override
    public void verifyPayment(String paymentId) throws Exception {

        // 결제내역 단건조회 api 호출
        String url = "https://api.portone.io/payments/" + paymentId;

        // Authorization 헤더 설정
        ConnectAPIHeader connectAPI = new ConnectAPIHeader(v2Api);

        HttpEntity<String> entity = new HttpEntity<>(connectAPI.getHeaders());

        try {
            IamportResponse<Payment> response = restTemplate.getForObject(url, IamportResponse.class, entity);
            log.info("API 응답: {}", response);

        } catch (HttpServerErrorException e) {
            log.error("API 호출 실패: {}", e.getResponseBodyAsString());
            throw e; // 또는 적절한 처리를 추가
        }


    }
}
