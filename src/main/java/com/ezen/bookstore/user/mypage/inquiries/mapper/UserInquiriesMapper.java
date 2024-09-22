package com.ezen.bookstore.user.mypage.inquiries.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ezen.bookstore.user.mypage.inquiries.dto.UserInquiriesDTO;

@Mapper
public interface UserInquiriesMapper {
	List<UserInquiriesDTO> getInquiriesList(UserInquiriesDTO inquiriesDTO);
	void deleteInquiry(Integer inquiry_num);
	List<UserInquiriesDTO> getOrderList(String member_id);
}
