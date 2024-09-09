package com.ezen.bookstore.security.service;


import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ezen.bookstore.admin.managers.dto.ManagersDTO;
import com.ezen.bookstore.security.mapper.UserMapper;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserMapper userMapper;

    public CustomUserDetailsService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ManagersDTO manager = userMapper.loadUserByUsername(username);
        if (manager == null) {
            throw new UsernameNotFoundException("아이디를 찾을 수 없습니다.");
        }

        // manager_dept가 02가 아닌 경우 로그인 실패 처리
        if (!"02".equals(manager.getManager_dept())) {
        	throw new UsernameNotFoundException("접근 권한이 없습니다.");        }

        // manager_dept가 02인 경우만 CustomUserDetails를 반환
        return new CustomUserDetails(manager, getAuthorities(manager));
    }
    
    private Collection<? extends GrantedAuthority> getAuthorities(ManagersDTO manager) {
        // manager_dept가 02인 경우 ROLE_USER 권한 부여
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }
}

