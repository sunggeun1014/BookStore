package com.ezen.bookstore.admin.managers.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezen.bookstore.admin.managers.dto.ManagersDTO;
import com.ezen.bookstore.admin.managers.repository.MgrRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class MgrService {
	private final  MgrRepository mgrRepository;
	
	@Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	public List<ManagersDTO> list(){
		// DB에서 모든 주문 목록을 꺼내와야 한다
		return mgrRepository.getMembers();
	}
	
	public ManagersDTO detailList(String managerId) {
		return mgrRepository.getManagerDetails(managerId);
	}
	
	@Transactional
    public void changeDept(String managerId, String dept) {
        if (dept == null || dept.isEmpty()) {
            throw new IllegalArgumentException("리뷰 ID 목록이 비어 있습니다.");
        }
        mgrRepository.changeAllByDept(managerId, dept);
    }
	
	@Transactional
    public void joinProcess(ManagersDTO managersDTO) {
        // 비밀번호 암호화
        String encodedPassword = bCryptPasswordEncoder.encode(managersDTO.getManager_pw());
        managersDTO.setManager_pw(encodedPassword);

        // 데이터베이스에 저장
        int result = mgrRepository.addManager(managersDTO);
        if (result != 1) {
            throw new RuntimeException("회원가입에 실패했습니다.");
        }

        log.info("회원가입 성공: {}", managersDTO.getManager_id());
    }
	

	
}
