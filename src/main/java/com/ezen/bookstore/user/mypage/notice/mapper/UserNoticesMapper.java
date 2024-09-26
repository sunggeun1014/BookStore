package com.ezen.bookstore.user.mypage.notice.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ezen.bookstore.user.mypage.notice.dto.UserNoticesDTO;

@Mapper
public interface UserNoticesMapper {
	
	List<UserNoticesDTO> getNoticesListWithPaging(@Param("dto") UserNoticesDTO userNoticesDTO,
            @Param("startRow") int startRow,
            @Param("endRow") int endRow,
            @Param("keyword") String keyword); 

	int getTotalNoticesCount(@Param("keyword") String keyword);

	UserNoticesDTO getNoticesDetail(Integer noticeNum);
	
	UserNoticesDTO getPreviousNotice(@Param("noticeNum") Integer noticeNum,
            @Param("keyword") String keyword);

	UserNoticesDTO getNextNotice(@Param("noticeNum") Integer noticeNum,
	        @Param("keyword") String keyword);

}
