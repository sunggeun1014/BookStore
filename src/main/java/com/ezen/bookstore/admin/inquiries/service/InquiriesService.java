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
	
	
	public List<InquiriesDTO> getList() {
		try {
			return inquiriesRepository.getList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public InquiriesDTO getDetailList(Integer inquiryNum) {
		try {
			return inquiriesRepository.getDetailList(inquiryNum);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Transactional
	public void updateInquiry(InquiriesDTO inquiriesDTO) {
		try {
			inquiriesRepository.updateInquiry(inquiriesDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Transactional
	public void insertInquiry(InquiriesDTO inquiriesDTO) {
		try {
			inquiriesRepository.insertInquiry(inquiriesDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
