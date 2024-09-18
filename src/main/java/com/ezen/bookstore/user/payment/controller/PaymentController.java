package com.ezen.bookstore.user.payment.controller;

import com.ezen.bookstore.commons.AccountManagement;
import com.ezen.bookstore.user.members.dto.UserMembersDTO;
import com.ezen.bookstore.user.payment.dto.OrderItemDTO;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequestMapping("/user")
@Controller
public class PaymentController {

    @GetMapping("/order")
    public String payment(HttpSession session, Model model) {
        UserMembersDTO memberDTO = (UserMembersDTO) session.getAttribute(AccountManagement.MEMBER_INFO);

        if (memberDTO != null) {
            model.addAttribute("memberDTO", memberDTO);
        }

        String purchaseType = (String) session.getAttribute("purchaseType");

        List<OrderItemDTO> items = (List<OrderItemDTO>) session.getAttribute("orderItems");

        Integer totalItems = 0;
        Integer totalPrice = 0;

        log.info("받은 목록: {}", items);
        log.info("받은 타입: {}", purchaseType);

        if (items != null) {
            if ("instantBuy".equals(purchaseType)) {
                for (OrderItemDTO item : items) {
                    totalPrice = item.getBook_price();
                    totalItems += item.getCart_purchase_qty();
                }
                model.addAttribute("orderItems", items);
            } else if ("cart".equals(purchaseType)) {
                for (OrderItemDTO item : items) {
                    totalPrice += item.getBook_price();
                    totalItems += item.getCart_purchase_qty();
                }
                model.addAttribute("orderCartItems", items);
            }
            model.addAttribute("itemsCnt", totalItems);
            model.addAttribute("totalPrice", totalPrice);
        } else {
            model.addAttribute("error", "데이터를 찾을 수 없습니다");
        }

        session.removeAttribute("purchaseType");

        return "/user/main/customer_order/payment";
    }
}
