package com.ezen.bookstore.user.cart.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ezen.bookstore.user.payment.dto.OrderItemDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ezen.bookstore.commons.AccountManagement;
import com.ezen.bookstore.user.cart.dto.UserCartDTO;
import com.ezen.bookstore.user.cart.service.UserCartService;
import com.ezen.bookstore.user.members.dto.UserMembersDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/user/cart")
public class UserCartController {

	private final UserCartService userCartService;

	@GetMapping("/list")
	public String showCart(HttpServletRequest request, Model model) {
		String memberId = getMemberIdFromSession(request);
		if (memberId == null) {
			return "redirect:/user/login";
		}

		List<UserCartDTO> cartItems = userCartService.getCartItemList(memberId);
		model.addAttribute("cartItems", cartItems);

		return "/user/main/customer_order/cart";
	}

	@PostMapping("/add")
	public ResponseEntity<String> addCartItem(@RequestBody UserCartDTO userCartDTO, HttpServletRequest request) {
		
			String memberId = getMemberIdFromSession(request);
			if (memberId == null) {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("사용자가 인증되지 않았습니다.");
			}
			
	        // 장바구니에 추가할 항목에 회원 ID 설정
			userCartDTO.setMember_id(memberId);
			userCartService.addCartItem(userCartDTO);
			return ResponseEntity.ok("장바구니에 추가하였습니다.");
	}

	@PostMapping("/delete")
	public ResponseEntity<?> deleteCartItems(@RequestBody Map<String, List<Integer>> requestData,
							HttpServletRequest request) {
		List<Integer> cartNums = requestData.get("cartNums");
		String memberId = getMemberIdFromSession(request);

		if (memberId == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
					.body(Map.of("success", false, "message", "로그인 되지 않았습니다"));
		}
		userCartService.deleteItemsByCartNums(cartNums, memberId);
		return ResponseEntity.ok(Map.of("success", true));
	}

	@PostMapping("/order")
	public ResponseEntity<Void> orderToPayment(@RequestBody List<OrderItemDTO> selectedItems, HttpSession session) {

		session.setAttribute("orderItems", selectedItems);
		session.setAttribute("purchaseType", "cart");

		return ResponseEntity.ok().build();
	}

	private String getMemberIdFromSession(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null) {
			UserMembersDTO membersDTO = (UserMembersDTO) session.getAttribute(AccountManagement.MEMBER_INFO);
			if (membersDTO != null) {
				return membersDTO.getMember_id();
			}
		}
		return null;
	}

}
