package com.ezen.bookstore.user.mypage.inquiries.service;

import java.io.IOException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ezen.bookstore.commons.FileManagement;
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
	FileManagement fileManagement;
	
	@Override
	public List<UserInquiriesDTO> searchInquiries(UserInquiriesDTO inquiriesDTO) {
		inquiriesDTO.setMember_id(SessionUtils.getUserAttribute(UserSessionInfo.MEMBER_ID));
		return userInquiriesMapper.getInquiriesList(inquiriesDTO);
	}
	
	@Override
	public void deleteInquiry(Integer inquiry_num) {
		userInquiriesMapper.deleteInquiry(inquiry_num);
	}
	
	@Override
	public List<UserInquiriesDTO> searchOrderList(UserInquiriesDTO inquiriesDTO) {
		inquiriesDTO.setMember_id(SessionUtils.getUserAttribute(UserSessionInfo.MEMBER_ID));
		return userInquiriesMapper.getOrderList(inquiriesDTO);
	}
	
	@Override
	public void registerInquiry(MultipartFile imageFile, UserInquiriesDTO inquiriesDTO) throws IOException {
		inquiriesDTO.setMember_id(SessionUtils.getUserAttribute(UserSessionInfo.MEMBER_ID));
		
		if (imageFile != null && !imageFile.isEmpty()) {
			String originalFilename = imageFile.getOriginalFilename();
			String modifiedFilename = FileManagement.generateNewFilename(originalFilename, FileManagement.INQUIRIES_UPLOAD_NAME);
			
			FileManagement.saveImage(imageFile, modifiedFilename, fileManagement.getInquiriesPath());
			
			inquiriesDTO.setInquiries_original(originalFilename);
			inquiriesDTO.setInquiries_changed(modifiedFilename);
		} else {
			inquiriesDTO.setInquiries_original(null);
			inquiriesDTO.setInquiries_changed(null);
		}
		
		userInquiriesMapper.registerInquiry(inquiriesDTO);
	}
}
