package com.ezen.bookstore.user.members.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezen.bookstore.user.members.dto.UserMembersDTO;
import com.ezen.bookstore.user.members.mapper.UserMgntMapper;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
@Slf4j
public class UserMgntServiceImpl implements UserMgntService {
    PasswordEncoder passwordEncoder;
    UserMgntMapper userMgntMapper;
    @Override
    public void joinProcess(UserMembersDTO userMembersDTO) {
	    	    
        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(userMembersDTO.getMember_pw());
        userMembersDTO.setMember_pw(encodedPassword);

        // 데이터베이스에 저장
        userMgntMapper.addMember(userMembersDTO);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean isMemberIdAvailable(String memberId) {
    	int count = userMgntMapper.findById(memberId);
        return count == 0;
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean isKakaoIdAvailable(String kakaoId) {
    	int count = userMgntMapper.findByKakaoId(kakaoId);
        return count == 0;
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean isNaverIdAvailable(String naverId) {
    	int count = userMgntMapper.findByNaverId(naverId);
        return count == 0;
    }
  
    @Override
    @Transactional(readOnly = true)
    public int getBasketCount(String member_id) {
    	int result = 0;
    	
    	try {
			result = userMgntMapper.getBasketCount(member_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return result;
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<UserMembersDTO> searchMember(String name, String email) {
    	return userMgntMapper.findMember(name,email);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean isMemberVerification(UserMembersDTO userMembersDTO) {
        
        int count = userMgntMapper.memberVerification(userMembersDTO);
        
        return count > 0;
    }
    
    @Override
    public boolean modifyMemberPw(UserMembersDTO userMembersDTO) {
        String encodedPassword = passwordEncoder.encode(userMembersDTO.getMember_pw());
        userMembersDTO.setMember_pw(encodedPassword);

    	int result = userMgntMapper.modifyMemberPw(userMembersDTO);
    	
    	return result > 0;
    }
}
