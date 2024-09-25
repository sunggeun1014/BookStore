package com.ezen.bookstore.user.mypage.inquiries.controller;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
	    public ResponseEntity<Map<String, Object>> getInquiries(
	            @ModelAttribute UserInquiriesDTO inquiriesDTO,
	            @RequestParam(value = "page", defaultValue = "1") int page,
	            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
	        
	        // 데이터 조회를 위한 서비스 호출
	        Map<String, Object> inquiries = userInquiriesService.searchInquiries(inquiriesDTO, page, pageSize);

	        // 데이터와 총 페이지 수를 프론트에 전달
	        return ResponseEntity.ok(inquiries);
	    }
	
	@PostMapping("/search-order")
	public List<UserInquiriesDTO> searchOrderList(@RequestBody UserInquiriesDTO inquiriesDTO) {
		return userInquiriesService.searchOrderList(inquiriesDTO);
	}
	
	
    @DeleteMapping("/delete/{inquiry_num}")
    public ResponseEntity<?> deleteInquiry(@PathVariable Integer inquiry_num,
    									   @RequestParam Integer order_detail_num) {
        try {
        	userInquiriesService.deleteInquiry(inquiry_num, order_detail_num);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("문의 삭제 중 오류가 발생했습니다.");
        }
    }
    
    @PostMapping("/register")
    public ResponseEntity<Void> registerInquiry(@RequestParam(value = "imageFile", required = false) MultipartFile imageFile,
    							  UserInquiriesDTO userInquiriesDTO) {
    	try {
			userInquiriesService.registerInquiry(imageFile, userInquiriesDTO);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    	HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URI.create("/user/mypage/inquiries-page"));
        
        return new ResponseEntity<>(headers, HttpStatus.FOUND);
    }
    
}
