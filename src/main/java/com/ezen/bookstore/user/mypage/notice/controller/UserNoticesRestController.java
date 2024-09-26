package com.ezen.bookstore.user.mypage.notice.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ezen.bookstore.user.mypage.notice.dto.UserNoticesDTO;
import com.ezen.bookstore.user.mypage.notice.service.UserNoticesService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/user/mypage/notices-page")
public class UserNoticesRestController {
	
	UserNoticesService userNoticesService;
	
	@GetMapping("/get-notices-list")
	public Map<String, Object> getNoticesList(
	        @RequestParam int page,
	        @RequestParam int size,
	        @RequestParam(required = false) String keyword, 
	        UserNoticesDTO userNoticesDTO) {
	    
	    keyword = (keyword == null) ? "" : keyword;

	    List<UserNoticesDTO> noticesList = userNoticesService.getNoticeList(page, size, keyword, userNoticesDTO);
	    int totalNoticesCount = userNoticesService.getTotalNoticesCount(keyword);

	    Map<String, Object> response = new HashMap<>();
	    response.put("notices", noticesList);
	    response.put("totalCount", totalNoticesCount);

	    return response;
	}

}
