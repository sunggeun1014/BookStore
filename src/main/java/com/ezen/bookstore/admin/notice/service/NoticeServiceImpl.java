package com.ezen.bookstore.admin.notice.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ezen.bookstore.admin.commons.AccountManagement;
import com.ezen.bookstore.admin.commons.FileManagement;
import com.ezen.bookstore.admin.managers.dto.ManagersDTO;
import com.ezen.bookstore.admin.notice.dto.NoticeDTO;
import com.ezen.bookstore.admin.notice.mapper.NoticeMapper;

import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Service
public class NoticeServiceImpl implements NoticeService {
	NoticeMapper noticeMapper;
	
	@Override
	@Transactional(readOnly = true)
	public List<NoticeDTO> getList() {
		return noticeMapper.getList();
	}
	
	@Override
	@Transactional(readOnly = true)
	public NoticeDTO getDetailList(Long noticeNum) {
		return noticeMapper.getDetailList(noticeNum);
	}
	
	@Override
	public void deleteNoticesByNums(List<Long> noticeNums) {
		noticeMapper.deleteNoticesByNums(noticeNums);
	}
	
	@Override
	public void saveNotice(NoticeDTO noticeDTO, List<MultipartFile> images, HttpSession session) throws IOException {
		ManagersDTO managersDTO = (ManagersDTO) session.getAttribute(AccountManagement.MANAGER_INFO);
		
		noticeDTO.setManager_id(managersDTO.getManager_id());
		
		noticeMapper.insertNotice(noticeDTO);

		if (images != null && !images.isEmpty()) {
			for (MultipartFile image : images) {
				String originalFilename = image.getOriginalFilename();
				String modifiedFilename = FileManagement.generateNewFilename(originalFilename, FileManagement.NOTICE_UPLOAD_NAME);
				
				FileManagement.saveImage(image, modifiedFilename, FileManagement.NOTICE_PATH);
				
				
				noticeDTO.setNotice_detail_original(originalFilename);
				noticeDTO.setNotice_detail_changed(modifiedFilename);
				
				
				noticeMapper.insertNoticeDetail(noticeDTO);
			} 
        }
	};
	
	@Override
	public void updateNotice(NoticeDTO noticeDTO, List<MultipartFile> images, HttpSession session) throws IOException {
		ManagersDTO managersDTO = (ManagersDTO) session.getAttribute(AccountManagement.MANAGER_INFO);
		
		noticeDTO.setManager_id(managersDTO.getManager_id());
		
		noticeMapper.updateNotice(noticeDTO);
		
		if (images != null && !images.isEmpty()) {
			for (MultipartFile image : images) {
				String originalFilename = image.getOriginalFilename();
				String modifiedFilename = FileManagement.generateNewFilename(originalFilename, FileManagement.NOTICE_UPLOAD_NAME);
				
				FileManagement.saveImage(image, modifiedFilename, FileManagement.NOTICE_PATH);
				
				
				noticeDTO.setNotice_detail_original(originalFilename);
				noticeDTO.setNotice_detail_changed(modifiedFilename);
				
				
				noticeMapper.updateNoticeDetail(noticeDTO);
			}
		}
		
	}
	
}
