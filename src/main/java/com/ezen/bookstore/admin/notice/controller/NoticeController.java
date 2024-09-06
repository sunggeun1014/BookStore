package com.ezen.bookstore.admin.notice.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezen.bookstore.admin.notice.dto.NoticeDTO;
import com.ezen.bookstore.admin.notice.service.NoticeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/admin/notice")
@Controller
public class NoticeController {
	
	private final NoticeService noticeService;
	
	@GetMapping("/notice")
	public String notice() {
		return "admin/notice/notice";
	}
	
	@GetMapping(value = "/json", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, Object> tableData() {
		List<NoticeDTO> tables = noticeService.getList();
		
		Map<String, Object> response = new HashMap<>();
		response.put("data", tables);
		response.put("size", tables.size());

		return response;
	}
	
	
	
	@PostMapping("/details")
	public String showNoticeDetails(@RequestParam("notice_num") Integer noticeNum, Model model) {
		
		NoticeDTO noticeDTO = noticeService.getDetailList(noticeNum);
		
		model.addAttribute("notice", noticeDTO);
		
		
		
		
		return "admin/notice/noticeDetails";
	}
	
	@PostMapping("/delete")
	public String deleteNotices(@RequestBody List<Integer> noticeNums, Model model) {
		
		noticeService.deleteNoticesByNums(noticeNums);
		
		
		return "redirect:/admin/notice/notice";
	}

	
	@GetMapping("/noticeReg")
	public String noticeReg() {
		return "admin/notice/noticeReg";
	}
	
}
