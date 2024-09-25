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
	public String noticesDetailPage(
	    @RequestParam("noticeNum") Integer noticeNum, Model model) {

	    // 현재 공지사항 불러오기
	    UserNoticesDTO currentNotice = userNoticesService.getNoticesDeatil(noticeNum);

	    // DB에서 현재 noticeNum보다 작은 번호 중 가장 큰 값을 가져와서 prevNotice로 사용
	    UserNoticesDTO prevNotice = userNoticesService.getPreviousNoticeByNumber(noticeNum);
	    
	    // DB에서 현재 noticeNum보다 큰 번호 중 가장 작은 값을 가져와서 nextNotice로 사용
	    UserNoticesDTO nextNotice = userNoticesService.getNextNoticeByNumber(noticeNum);

	    // 모델에 데이터 담기
	    model.addAttribute("notice", currentNotice);
	    model.addAttribute("prevNotice", prevNotice);
	    model.addAttribute("nextNotice", nextNotice);

	    return "user/mypage/notices/notices-detail";
	}

}
