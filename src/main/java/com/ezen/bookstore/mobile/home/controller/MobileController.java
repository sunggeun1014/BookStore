package com.ezen.bookstore.mobile.home.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ezen.bookstore.commons.Pagination;
import com.ezen.bookstore.commons.PaginationProcess;
import com.ezen.bookstore.mobile.home.dto.DeliveryDTO;
import com.ezen.bookstore.mobile.home.service.DeliveryService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Controller
@RequestMapping("/mobile/admin")

public class MobileController {
	PaginationProcess paginationProcess;
	DeliveryService deliveryService;
	
	@GetMapping("/login")
	public String login() {		
		return "/mobile/login/login";		
	}
	
	@GetMapping("/index")
    public String index(Model model, Pagination pagination) {
		List<DeliveryDTO> deliveryList = deliveryService.getRequestList();
		
		if (deliveryList != null && !deliveryList.isEmpty()) {
			Map<String, Object> map = paginationProcess.process(pagination, deliveryList);
			model.addAttribute("page", map.get("page"));
			model.addAttribute("list", map.get("list"));
		}
		
		return "mobile/home/home";
    }
	
	@GetMapping("/delivery-detail")
	public String orderDetailPage(@RequestParam("orderNum") Integer orderNum,
								  Model model) {
		model.addAttribute("orderNum", orderNum);
		model.addAttribute("list", deliveryService.getRequestDetail(orderNum));
		return "mobile/home/delivery_detail";
	}
}
