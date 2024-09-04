package com.ezen.bookstore.admin.inquiries.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezen.bookstore.admin.inquiries.dto.InquiriesDTO;
import com.ezen.bookstore.admin.inquiries.mapper.InquiriesMapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class InquiriesServiceImpl implements InquiriesService {

    InquiriesMapper inquiriesMapper;

    @Override
    @Transactional(readOnly = true)
    public List<InquiriesDTO> getList() {
        return inquiriesMapper.getList();
    }

    @Override
    @Transactional(readOnly = true)
    public InquiriesDTO getDetailList(Integer inquiryNum) {
        return inquiriesMapper.getDetailList(inquiryNum);
    }

    @Override
    @Transactional
    public void updateInquiry(InquiriesDTO inquiriesDTO) {
    	inquiriesMapper.updateInquiry(inquiriesDTO);
    }

    @Override
    @Transactional
    public void insertInquiry(InquiriesDTO inquiriesDTO) {
    	inquiriesMapper.insertInquiry(inquiriesDTO);
    }
}
