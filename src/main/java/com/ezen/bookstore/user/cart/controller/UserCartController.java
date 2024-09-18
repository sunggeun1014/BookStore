package com.ezen.bookstore.user.cart.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ezen.bookstore.user.cart.dto.UserCartDTO;
import com.ezen.bookstore.user.cart.service.UserCartService;
import com.ezen.bookstore.user.payment.dto.OrderItemDTO;
import com.ezen.bookstore.commons.SessionUtils;
import com.ezen.bookstore.user.commons.UserSessionInfo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user/cart")
public class UserCartController {

    private final UserCartService userCartService;

    @GetMapping("/list")
    public String showCart(Model model) {
        String memberId = SessionUtils.getUserAttribute(UserSessionInfo.MEMBER_ID);

        if (memberId == null) {
            return "redirect:/user/login"; // 로그인 페이지로 리다이렉트
        }

        List<UserCartDTO> cartItems = userCartService.getCartItemList(memberId);
        model.addAttribute("cartItems", cartItems);

        return "/user/main/customer_order/cart";
    }

    @PostMapping("/add")
    public ResponseEntity<Map<String, Object>> addCartItem(@RequestBody UserCartDTO userCartDTO) {
        Map<String, Object> response = new HashMap<>();
        String memberId = SessionUtils.getUserAttribute(UserSessionInfo.MEMBER_ID);

        if (memberId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
        userCartDTO.setMember_id(memberId);
        boolean exists = userCartService.cartItemExists(userCartDTO);

        if (exists) {
        	response.put("status", "warning");
        } else {
            userCartService.addCartItem(userCartDTO);
            response.put("success", true);
        }
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/update")
    public ResponseEntity<Map<String, Object>> updateCartItem(@RequestBody UserCartDTO userCartDTO) {
        Map<String, Object> response = new HashMap<>();
        String memberId = SessionUtils.getUserAttribute(UserSessionInfo.MEMBER_ID);

        userCartDTO.setMember_id(memberId);
        boolean updated = userCartService.updateCartItemQuantity(userCartDTO);

        if (updated) {
            response.put("success", true);
            response.put("message", "수량 업데이트 성공");
        } else {
            response.put("status", "error");
            response.put("message", "수량 업데이트 실패");
        }
        return ResponseEntity.ok(response);
    }


    @PostMapping("/delete")
    public ResponseEntity<Map<String, Object>> deleteCartItems(@RequestBody Map<String, List<Integer>> requestData) {
        List<Integer> cartNums = requestData.get("cartNums");
        String memberId = SessionUtils.getUserAttribute(UserSessionInfo.MEMBER_ID);

        Map<String, Object> response = new HashMap<>();

        if (memberId == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        userCartService.deleteItemsByCartNums(cartNums, memberId);
        response.put("success", true);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/order")
    public ResponseEntity<Void> orderToPayment(@RequestBody List<OrderItemDTO> selectedItems, HttpSession session) {
        session.setAttribute("orderItems", selectedItems);
        session.setAttribute("purchaseType", "cart");
        return ResponseEntity.ok().build();
    }
}
