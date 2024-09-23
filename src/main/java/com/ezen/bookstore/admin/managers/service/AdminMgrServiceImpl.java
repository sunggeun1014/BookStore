package com.ezen.bookstore.admin.managers.service;
import java.io.IOException;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.ezen.bookstore.admin.managers.dto.AdminManagersDTO;
import com.ezen.bookstore.admin.managers.mapper.AdminMgrMapper;
import com.ezen.bookstore.commons.FileManagement;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Transactional
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Service
public class AdminMgrServiceImpl implements AdminMgrService {
	
    AdminMgrMapper adminMgrMapper;
    PasswordEncoder passwordEncoder;
    FileManagement fileManagement;
    
    @Override
    @Transactional(readOnly = true)
    public List<AdminManagersDTO> list() {    	
        return adminMgrMapper.getManagersList();
    }
    
    @Override
    @Transactional(readOnly = true)
    public AdminManagersDTO detailList(String managerId) {
        return adminMgrMapper.getManagerDetails(managerId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean isManagerIdAvailable(String managerId) {
    	return adminMgrMapper.findById(managerId);
    }

    @Override
    public void changeDept(String managerId, String dept) {
        if (dept == null || dept.isEmpty()) {
            throw new IllegalArgumentException("리뷰 ID 목록이 비어 있습니다.");
        }
        adminMgrMapper.updateManagerDept(managerId, dept);
    }
    
    @Override
    public void joinProcess(AdminManagersDTO managersDTO, MultipartFile profileImage) {
	    if (!profileImage.isEmpty()) {
            try {
            	String originalFileName = profileImage.getOriginalFilename();
            	String newFileName = FileManagement.generateNewFilename(originalFileName, FileManagement.PROFILE_UPLOAD_NAME);
            	
            	FileManagement.saveImage(profileImage, newFileName, fileManagement.getProfilePath());
            	
                managersDTO.setManager_profile_original(originalFileName);
                managersDTO.setManager_profile_changed(newFileName);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
	    
        String encodedPassword = passwordEncoder.encode(managersDTO.getManager_pw());
        managersDTO.setManager_pw(encodedPassword);
        System.out.println(encodedPassword);

        adminMgrMapper.addManager(managersDTO);
    }
    
    @Override
    public void updateManager(AdminManagersDTO managersDTO, MultipartFile profileImage) {
    	String password = managersDTO.getManager_pw();
    	
    	if (!password.isEmpty()) {
    		String encodedPassword = passwordEncoder.encode(password);
    		managersDTO.setManager_pw(encodedPassword);
    	}
		if (!profileImage.isEmpty()) {
	        try {
	             String originalFilename = profileImage.getOriginalFilename();
	             String modifiedFilename = FileManagement.generateNewFilename(originalFilename, FileManagement.PROFILE_UPLOAD_NAME);
	             
	             FileManagement.saveImage(profileImage, modifiedFilename,  fileManagement.getProfilePath());
	
	             managersDTO.setManager_profile_original(originalFilename);
	             managersDTO.setManager_profile_changed(modifiedFilename);
	        } catch (IOException e) {
	              e.printStackTrace();
	        }
	      }

		adminMgrMapper.updateManager(managersDTO);
    }
    
}