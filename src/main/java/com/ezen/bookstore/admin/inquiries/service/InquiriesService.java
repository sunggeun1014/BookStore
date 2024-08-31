package com.ezen.bookstore.admin.inquiries.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezen.bookstore.admin.inquiries.dto.InquiriesDTO;
import com.ezen.bookstore.admin.inquiries.repository.InquiriesRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class InquiriesService {
	
	private final InquiriesRepository inquiriesRepository;
	
	@Transactional(readOnly = true)
	public List<InquiriesDTO> getList() {
		return inquiriesRepository.getList();
	}
	
	@Transactional(readOnly = true)
	public InquiriesDTO getDetailList(Integer inquiryNum) {
		return inquiriesRepository.getDetailList(inquiryNum);
	}
	
	@Transactional
	public void updateInquiry(InquiriesDTO inquiriesDTO) {
		inquiriesRepository.updateInquiry(inquiriesDTO);
	}
	
	@Transactional
	public void insertInquiry(InquiriesDTO inquiriesDTO) {
		inquiriesRepository.insertInquiry(inquiriesDTO);
	}
}
