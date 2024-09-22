package com.ezen.bookstore.user.mypage.inquiries.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ezen.bookstore.user.mypage.inquiries.dto.UserInquiriesDTO;
import com.ezen.bookstore.user.mypage.inquiries.service.UserInquiriesService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequestMapping("/user/mypage/inquiries-page")
@RestController
public class UserInquiriesRestController {
	
	UserInquiriesService userInquiriesService;
	
	@GetMapping("/search")
	public ResponseEntity<List<UserInquiriesDTO>> searchInquiries(@ModelAttribute UserInquiriesDTO inquiriesDTO) {
		List<UserInquiriesDTO> inquiries = userInquiriesService.searchInquiries(inquiriesDTO);
		return ResponseEntity.ok(inquiries);
	}
	
	@GetMapping("/search-order")
	public List<UserInquiriesDTO> searchOrderList() {
		return userInquiriesService.searchOrderList();
	}
	
	
    @DeleteMapping("/delete/{inquiry_num}")
    public ResponseEntity<?> deleteInquiry(@PathVariable Integer inquiry_num) {
        try {
        	userInquiriesService.deleteInquiry(inquiry_num);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("문의 삭제 중 오류가 발생했습니다.");
        }
    }
}
