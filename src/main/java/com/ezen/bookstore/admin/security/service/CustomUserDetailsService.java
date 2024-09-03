package com.ezen.bookstore.admin.security.service;


import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ezen.bookstore.admin.managers.dto.ManagersDTO;
import com.ezen.bookstore.admin.security.mapper.UserMapper;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserMapper userMapper;

    public CustomUserDetailsService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        ManagersDTO manager = userMapper.loadUserByUsername(username);
//        if (manager == null) {
//            throw new UsernameNotFoundException("User not found");
//        }
//
//        // 여기서 사용자 정보를 반환하면 Spring Security가 자동으로 비밀번호를 비교합니다.
//        return new CustomUserDetails(manager, getAuthorities(manager));
//    }

//    private Collection<? extends GrantedAuthority> getAuthorities(ManagersDTO manager) {
//        String role = "ROLE_USER";
//        if ("01".equals(manager.getManager_dept())) {
//            role = "ROLE_ADMIN";
//        }
//        return Collections.singletonList(new SimpleGrantedAuthority(role));
//    }
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ManagersDTO manager = userMapper.loadUserByUsername(username);
        if (manager == null) {
            throw new UsernameNotFoundException("User not found");
        }

        // manager_dept가 02가 아닌 경우 로그인 실패 처리
        if (!"02".equals(manager.getManager_dept())) {
            throw new UsernameNotFoundException("Access denied: User does not have the required department.");
        }

        // manager_dept가 02인 경우만 CustomUserDetails를 반환
        return new CustomUserDetails(manager, getAuthorities(manager));
    }
    
    private Collection<? extends GrantedAuthority> getAuthorities(ManagersDTO manager) {
        // manager_dept가 02인 경우 ROLE_USER 권한 부여
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
    }
}

