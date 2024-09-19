package com.ezen.bookstore.user.mypage.inquiries.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezen.bookstore.commons.SessionUtils;
import com.ezen.bookstore.user.commons.UserSessionInfo;
import com.ezen.bookstore.user.mypage.inquiries.dto.UserInquiriesDTO;
import com.ezen.bookstore.user.mypage.inquiries.mapper.UserInquiriesMapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class UserInquiriesServiceImpl implements UserInquiriesService {

	UserInquiriesMapper userInquiriesMapper;
	
	@Override
	public List<UserInquiriesDTO> searchInquiries(UserInquiriesDTO inquiriesDTO) {
		inquiriesDTO.setMember_id(SessionUtils.getUserAttribute(UserSessionInfo.MEMBER_ID));
		return userInquiriesMapper.getInquiriesList(inquiriesDTO);
	}
	
	@Override
	public void deleteInquiry(Integer inquiry_num) {
		userInquiriesMapper.deleteInquiry(inquiry_num);
	}
}
