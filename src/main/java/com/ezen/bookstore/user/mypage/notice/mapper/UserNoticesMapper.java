package com.ezen.bookstore.user.mypage.notice.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ezen.bookstore.user.mypage.notice.dto.UserNoticesDTO;

@Mapper
public interface UserNoticesMapper {
	List<UserNoticesDTO> getNoticesList(UserNoticesDTO usernoticesDTO);
	UserNoticesDTO getNoticesDetail(Integer noticeNum);
    UserNoticesDTO getPreviousNotice(@Param("noticeNum") Integer noticeNum);
    UserNoticesDTO getNextNotice(@Param("noticeNum") Integer noticeNum);

}
