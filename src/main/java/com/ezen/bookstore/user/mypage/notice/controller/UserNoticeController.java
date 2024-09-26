package com.ezen.bookstore.user.mypage.notice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ezen.bookstore.user.mypage.notice.dto.UserNoticesDTO;
import com.ezen.bookstore.user.mypage.notice.service.UserNoticesService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/user/mypage")
@Controller
public class UserNoticeController {
	
	UserNoticesService userNoticesService;
	
	@GetMapping("/notices-page")
	public String noticesPage() {
		return "user/mypage/notices/notices";
	}
	
	@GetMapping("/notices-detail")
	public String getNoticeDetail(@RequestParam Integer noticeNum,
	                              @RequestParam(required = false) String prevNoticeNum,
	                              @RequestParam(required = false) String nextNoticeNum,
	                              @RequestParam(required = false) String keyword, 
	                              Model model) {
		if (keyword == null) {
	        keyword = "";  
	    }
	    UserNoticesDTO noticeDetail = userNoticesService.getNoticesDeatil(noticeNum);
	    UserNoticesDTO previousNotice = userNoticesService.getPreviousNoticeByNumber(noticeNum, keyword);
	    UserNoticesDTO nextNotice = userNoticesService.getNextNoticeByNumber(noticeNum, keyword);

	    model.addAttribute("notice", noticeDetail);
	    model.addAttribute("prevNotice", previousNotice);
	    model.addAttribute("nextNotice", nextNotice);
	    model.addAttribute("keyword", keyword);  

	    return "user/mypage/notices/notices-detail";
	}


}
