package com.ezen.bookstore.user.payment.controller;

import com.ezen.bookstore.commons.AccountManagement;
import com.ezen.bookstore.user.members.dto.UserMembersDTO;
import com.ezen.bookstore.user.payment.dto.OrderItemDTO;
import com.ezen.bookstore.user.payment.dto.PaymentRequestDTO;
import com.ezen.bookstore.user.payment.service.PaymentService;
import com.siot.IamportRestClient.IamportClient;
import com.siot.IamportRestClient.exception.IamportResponseException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@RequestMapping("/user")
@RequiredArgsConstructor
@Controller
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping("/order")
    public String payment(HttpSession session, Model model) {
        UserMembersDTO memberDTO = (UserMembersDTO) session.getAttribute(AccountManagement.MEMBER_INFO);

        if (memberDTO != null) {
            model.addAttribute("memberDTO", memberDTO);
        }

        String purchaseType = (String) session.getAttribute("purchaseType");

        List<OrderItemDTO> items = (List<OrderItemDTO>) session.getAttribute("orderItems");
        List<OrderItemDTO> cartItems = (List<OrderItemDTO>) session.getAttribute("orderCartItems");

        Integer totalItems = 0;
        Integer totalPrice = 0;

        log.info("받은 목록: {}", items);
        log.info("받은 카드목록: {}", cartItems);
        log.info("받은 타입: {}", purchaseType);

        List<OrderItemDTO> combinedItems = new ArrayList<>();
        if (items != null) {
            combinedItems.addAll(items);
        }

        if (cartItems != null) {
            combinedItems.addAll(cartItems);
        }

        if (!combinedItems.isEmpty()) {
            for (OrderItemDTO item : combinedItems) {
                totalItems += item.getCart_purchase_qty();
                totalPrice += item.getBook_price();
            }

            model.addAttribute("combinedItems", combinedItems);
            model.addAttribute("itemsCnt", totalItems);
            model.addAttribute("totalPrice", totalPrice);
        } else {
            model.addAttribute("error", "데이터를 찾을 수 없습니다");
        }

//        session.removeAttribute("purchaseType");

        return "/user/main/customer_order/payment";
    }

//    @PostMapping("/order/complete")
//    public ResponseEntity<Object> paymentPrepare(@RequestBody PaymentRequestDTO paymentRequestDTO) {
//        log.info("결제 요청 수신: {}", paymentRequestDTO);
//        try {
//            paymentService.processPayment(paymentRequestDTO);
//        } catch (IamportResponseException e) {
//            log.info("결제오류: {}", e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("결제 요청 처리 중 오류 발생");
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//
//        return ResponseEntity.ok().build();
//    }

    @GetMapping("/order/payment/{paymentId}")
    public ResponseEntity<Object> paymentPrepare(@PathVariable("paymentId") String paymentId) {
        try {
            paymentService.verifyPayment(paymentId);
        } catch (IamportResponseException | IOException e) {
            log.info("결제오류: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("결제 요청 처리 중 오류 발생");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.ok().build();
    }


}
