package com.ezen.bookstore.user.mypage.inquiries.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	@Transactional(readOnly = true)
	@Override
	public Map<String, Object> searchInquiries(UserInquiriesDTO inquiriesDTO, int page, int pageSize) {
		inquiriesDTO.setMember_id(SessionUtils.getUserAttribute(UserSessionInfo.MEMBER_ID));
		int startRow = (page - 1) * pageSize + 1;
        int endRow = page * pageSize;

        // 조회된 데이터와 총 카운트 가져오기
        List<UserInquiriesDTO> inquiriesList = userInquiriesMapper.getInquiriesList(inquiriesDTO, startRow, endRow);
        int totalCount = userInquiriesMapper.getTotalCount(inquiriesDTO);

        // 총 페이지 수 계산
        int totalPages = (int) Math.ceil((double) totalCount / pageSize);

        // 데이터와 페이지 정보를 반환할 맵 생성
        Map<String, Object> result = new HashMap<>();
        result.put("inquiries", inquiriesList);
        result.put("totalPages", totalPages);

        return result;
	}
	
	@Override
	public void deleteInquiry(Integer inquiry_num, Integer order_detail_num) {
		if (userInquiriesMapper.deleteInquiry(inquiry_num) > 0) {
			userInquiriesMapper.resetRequestQty(order_detail_num);
		}
	}
	
	@Transactional(readOnly = true)
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
		
		if (userInquiriesMapper.registerInquiry(inquiriesDTO) > 0 && inquiriesDTO.getOrder_request_qty() > 0) {
			
			userInquiriesMapper.updateRequest(inquiriesDTO);
		}
	}
}
