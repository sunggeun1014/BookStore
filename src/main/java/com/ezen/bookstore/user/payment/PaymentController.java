package com.ezen.bookstore.user.payment;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user")
@Controller
public class PaymentController {

    @GetMapping("/payment")
    public String payment() {
        return "/user/main/customer_order/payment";
    }
}
