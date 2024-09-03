package com.ezen.bookstore.admin.inquiries.repository;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.ezen.bookstore.admin.inquiries.dto.InquiriesDTO;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class InquiriesRepository {
	private final SqlSessionTemplate sql;
	
	public List<InquiriesDTO> getList() {
		return sql.selectList("Inquiries.getList"); 
	}
	
	public InquiriesDTO getDetailList(Integer inquiryNum) {
		return sql.selectOne("Inquiries.getDetailList", inquiryNum);
	}
	
	public void updateInquiry(InquiriesDTO inquiriesDTO) {
		sql.update("Inquiries.updateInquiry", inquiriesDTO);
	}
	
	public void insertInquiry(InquiriesDTO inquiriesDTO) {
		sql.insert("Inquiries.insertInquiry", inquiriesDTO);
	}
}
