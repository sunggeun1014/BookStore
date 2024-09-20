package com.ezen.bookstore.user.mypage.notice.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
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
	public ResponseEntity<List<UserNoticesDTO>> getNoticesList(@ModelAttribute UserNoticesDTO userNoticesDTO) {
		List<UserNoticesDTO> noticesList = userNoticesService.getNoticeList(userNoticesDTO);
		return ResponseEntity.ok(noticesList);
	}
}
