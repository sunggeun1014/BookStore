package com.ezen.bookstore.admin.inquiries.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezen.bookstore.admin.commons.AccountManagement;
import com.ezen.bookstore.admin.inquiries.dto.InquiriesDTO;
import com.ezen.bookstore.admin.inquiries.service.InquiriesService;
import com.ezen.bookstore.admin.managers.dto.ManagersDTO;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/admin/inquiries")
@Controller
public class InquiriesController {
	private final InquiriesService iqs;
	
	@GetMapping(value = "/json", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, Object> tableData() {
		List<InquiriesDTO> tables = iqs.getList();

		// DataTables가 요구하는 형식으로 JSON 데이터 구성
		Map<String, Object> response = new HashMap<>();
		response.put("data", tables);
		response.put("size", tables.size());

		return response;
	}

	@PostMapping("/details")
	public String showInquiriesDetails(@RequestParam("inquiry_num") Integer inquiryNum, Model model) {
		InquiriesDTO tables = iqs.getDetailList(inquiryNum);

		model.addAttribute("inquiries", tables);
		
		String templatePath = "/admin/inquiries/inquiriesDetails";
		model.addAttribute("template", templatePath); // 경로를 template로 전달
		
		return "admin/index";
	}
	
	
	
	@PostMapping("/update")
	public String updateInquiries(@ModelAttribute("inquiries") InquiriesDTO inquiriesDTO, Model model) {
		
		iqs.updateInquiry(inquiriesDTO);
		
		String templatePath = "/admin/inquiries/inquiries";
		model.addAttribute("template", templatePath);
		
		return "admin/index";
	}
	
	@PostMapping("/insert")
	public String insertInquiries(@ModelAttribute("inquiries") InquiriesDTO inquiriesDTO, HttpSession session, Model model) {
		
		ManagersDTO sessionInfo = (ManagersDTO) session.getAttribute(AccountManagement.MANAGER_INFO);
		
        inquiriesDTO.setManager_id(sessionInfo.getManager_id());  // DTO에 manager_id 설정
        
		iqs.insertInquiry(inquiriesDTO);
		
		String templatePath = "/admin/inquiries/inquiries";
		model.addAttribute("template", templatePath);
		
		
		return "admin/index";
	}

}
