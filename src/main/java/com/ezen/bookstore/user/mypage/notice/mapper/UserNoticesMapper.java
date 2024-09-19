package com.ezen.bookstore.user.mypage.notice.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ezen.bookstore.user.mypage.notice.dto.UserNoticesDTO;

@Mapper
public interface UserNoticesMapper {
	List<UserNoticesDTO> getNoticesList(UserNoticesDTO usernoticesDTO);	
}
