package com.ezen.bookstore.admin.inquiries.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ezen.bookstore.admin.inquiries.dto.InquiriesDTO;

@Mapper
public interface InquiriesMapper {
	public List<InquiriesDTO> getList();
	
	public InquiriesDTO getDetailList(Integer inquiryNum);
	
	public void updateInquiry(InquiriesDTO inquiriesDTO);
	
	public void insertInquiry(InquiriesDTO inquiriesDTO);
}
