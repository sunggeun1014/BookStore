package com.ezen.bookstore.user.members.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezen.bookstore.admin.members.dto.MembersDTO;
import com.ezen.bookstore.user.members.dto.UserMembersDTO;
import com.ezen.bookstore.user.members.repository.UserMgntRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Transactional
@Slf4j
@RequiredArgsConstructor
@Service
public class UserMgntService {
	private final UserMgntRepository userMgntRepository;
    private final PasswordEncoder passwordEncoder;
    
    public void joinProcess(UserMembersDTO userMembersDTO) {
	    	    
        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(userMembersDTO.getMember_pw());
        userMembersDTO.setMember_pw(encodedPassword);
        System.out.println(encodedPassword);

        // 데이터베이스에 저장
        userMgntRepository.addMember(userMembersDTO);
    }
    
    @Transactional(readOnly = true)
    public boolean isMemberIdAvailable(String memberId) {
        return userMgntRepository.findById(memberId);
    }
    
    public int getBasketCount(String member_id) {
    	int result = 0;
    	
    	try {
			result = userMgntRepository.getBasketCount(member_id);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return result;
    }
    
    public List<UserMembersDTO> searchMember(String name, String email) {
    	return userMgntRepository.getFindMember(name,email);
    }
    
    public boolean isMemberVerification(UserMembersDTO userMembersDTO ) {
    	
    	return userMgntRepository.memberVerification(userMembersDTO);
    }
    
    public boolean modifyMemberPw(UserMembersDTO userMembersDTO) {
        String encodedPassword = passwordEncoder.encode(userMembersDTO.getMember_pw());
        userMembersDTO.setMember_pw(encodedPassword);

    	int result = userMgntRepository.modifyPw(userMembersDTO);
    	
    	return result > 0;
    }
}
