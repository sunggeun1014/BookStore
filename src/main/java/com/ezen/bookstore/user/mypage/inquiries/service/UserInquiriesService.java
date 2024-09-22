package com.ezen.bookstore.user.mypage.inquiries.service;

import java.util.List;

import com.ezen.bookstore.user.mypage.inquiries.dto.UserInquiriesDTO;

public interface UserInquiriesService {
	List<UserInquiriesDTO> searchInquiries(UserInquiriesDTO inquiriesDTO);
	void deleteInquiry(Integer inquiry_num);
	List<UserInquiriesDTO> searchOrderList();
}
