package com.ezen.bookstore.user.mypage.inquiries.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ezen.bookstore.user.mypage.inquiries.dto.UserInquiriesDTO;

@Mapper
public interface UserInquiriesMapper {
	List<UserInquiriesDTO> getInquiriesList(@Param("inquiriesDTO") UserInquiriesDTO inquiriesDTO,
									        @Param("startRow") int startRow,
									        @Param("endRow") int endRow);
	
	int getTotalCount(@Param("inquiriesDTO") UserInquiriesDTO inquiriesDTO);

	Integer deleteInquiry(Integer inquiry_num);
	List<UserInquiriesDTO> getOrderList(UserInquiriesDTO inquiriesDTO);
	Integer registerInquiry (UserInquiriesDTO inquiriesDTO);
	Integer updateRequest (UserInquiriesDTO inquiriesDTO);
	void resetRequestQty (Integer order_detail_num);
}
