package com.ezen.bookstore.mobile.home.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ezen.bookstore.admin.commons.AdminSessionInfo;
import com.ezen.bookstore.admin.managers.dto.AdminManagersDTO;
import com.ezen.bookstore.admin.managers.service.AdminMgrService;
import com.ezen.bookstore.commons.CommonConstants;
import com.ezen.bookstore.commons.Pagination;
import com.ezen.bookstore.commons.PaginationProcess;
import com.ezen.bookstore.commons.SessionUtils;
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
	AdminMgrService adminMgrService;
	
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
		
		return "/mobile/home/home";
    }
	
	
	@GetMapping("/delivery-detail")
	public String orderDetailPage(@RequestParam("requestNum") Integer requestNum,
								  Model model) {
		model.addAttribute("requestNum", requestNum);
		model.addAttribute("list", deliveryService.getRequestDetail(requestNum));
		return "/mobile/home/delivery_detail";
	}
	
	
	
	
	@GetMapping("/myinfo")
	public String myInfoPage(Model model) {
		AdminManagersDTO managerDTO = adminMgrService.detailList(SessionUtils.getAdminAttribute(AdminSessionInfo.MANAGER_ID));
		
        String[] phoneNumber = managerDTO.getManager_phoneNo().split("-");
        String countryNum = phoneNumber[0];
        String phonePart1 = phoneNumber[1];
        String phonePart2 = phoneNumber[2];
        
		model.addAttribute("countryNums", CommonConstants.COUNTRY_NUMS);
		model.addAttribute("countryNum", countryNum);
		model.addAttribute("phonePart1", phonePart1);
		model.addAttribute("phonePart2", phonePart2);
		
		model.addAttribute("manager", managerDTO);
		return "/mobile/myinfo/myinfo";
	}
	
	@PostMapping("/update-info")
    public String updateManagerInfo(@ModelAttribute AdminManagersDTO managerDTO) {
        
		managerDTO.setManager_id(SessionUtils.getAdminAttribute(AdminSessionInfo.MANAGER_ID));
        
        adminMgrService.updateManager(managerDTO, null);
        
        return "redirect:/mobile/admin/index";
    }
	
	@GetMapping("/delivery-status")
	public String orderStatusPage(Model model, Pagination pagination) {
		List<DeliveryDTO> orderStatus = deliveryService.getOrderStatus();
		
		if (orderStatus != null && !orderStatus.isEmpty()) {
			Map<String, Object> map = paginationProcess.process(pagination, orderStatus);
			model.addAttribute("page", map.get("page"));
			model.addAttribute("list", map.get("list"));
		}
		
		
		return "/mobile/home/delivery_status";
	}
}
