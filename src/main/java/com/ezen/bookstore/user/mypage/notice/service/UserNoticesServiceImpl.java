package com.ezen.bookstore.user.mypage.notice.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezen.bookstore.user.mypage.notice.dto.UserNoticesDTO;
import com.ezen.bookstore.user.mypage.notice.mapper.UserNoticesMapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class UserNoticesServiceImpl implements UserNoticesService{
	
	UserNoticesMapper noticesMapper;
	
	@Override
	@Transactional(readOnly = true)
	public List<UserNoticesDTO> getNoticeList(UserNoticesDTO userNoticesDTO) {		
		return noticesMapper.getNoticesList(userNoticesDTO);
	}
}
