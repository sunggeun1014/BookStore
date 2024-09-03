package com.ezen.bookstore.admin.inquiries.service;

import java.util.List;

import com.ezen.bookstore.admin.inquiries.dto.InquiriesDTO;

public interface InquiriesService {
    List<InquiriesDTO> getList();
    InquiriesDTO getDetailList(Integer inquiryNum);
    void updateInquiry(InquiriesDTO inquiriesDTO);
    void insertInquiry(InquiriesDTO inquiriesDTO);
}