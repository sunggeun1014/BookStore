package com.ezen.bookstore.admin.notice.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ezen.bookstore.admin.notice.dto.NoticeDTO;

@Mapper
public interface NoticeMapper {
	List<NoticeDTO> getList();
	NoticeDTO getDetailList(Integer noticeNum);
	void deleteNoticesByNums(List<Integer> noticeNums);
}
