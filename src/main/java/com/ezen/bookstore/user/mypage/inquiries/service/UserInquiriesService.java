package com.ezen.bookstore.user.mypage.inquiries.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.ezen.bookstore.user.mypage.inquiries.dto.UserInquiriesDTO;

public interface UserInquiriesService {
	List<UserInquiriesDTO> searchInquiries(UserInquiriesDTO inquiriesDTO);
	void deleteInquiry(Integer inquiry_num, Integer order_detail_num);
	List<UserInquiriesDTO> searchOrderList(UserInquiriesDTO inquiriesDTO);
	void registerInquiry(MultipartFile imageFile, UserInquiriesDTO inquiriesDTO) throws IOException;
}
