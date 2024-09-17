package com.ezen.bookstore.user.payment.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/user")
@Controller
public class CompleteOrderController {

    @GetMapping("/complete-order")
    public String completeOrder() {
        return "/user/main/customer_order/complete-order";
    }
}
