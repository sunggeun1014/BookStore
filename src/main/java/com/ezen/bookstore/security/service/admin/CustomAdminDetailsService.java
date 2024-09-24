package com.ezen.bookstore.security.service.admin;


import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ezen.bookstore.admin.managers.dto.AdminManagersDTO;
import com.ezen.bookstore.security.mapper.AdminMapper;

@Service
public class CustomAdminDetailsService implements UserDetailsService {

    private final AdminMapper adminMapper;

    public CustomAdminDetailsService(AdminMapper adminMapper) {
        this.adminMapper = adminMapper;
    }

    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AdminManagersDTO manager = adminMapper.loadAdminByUsername(username);
        if (manager == null) {
            throw new UsernameNotFoundException("아이디를 찾을 수 없습니다.");
        }

        // manager_dept가 02가 아닌 경우 로그인 실패 처리
        if (!"02".equals(manager.getManager_dept())) {
        	throw new UsernameNotFoundException("접근 권한이 없습니다.");        }

        // manager_dept가 02인 경우만 CustomUserDetails를 반환
        return new CustomAdminDetails(manager, getAuthorities(manager));
    }
    
    private Collection<? extends GrantedAuthority> getAuthorities(AdminManagersDTO manager) {
        // manager_dept가 02인 경우 ROLE_USER 권한 부여
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }
}

