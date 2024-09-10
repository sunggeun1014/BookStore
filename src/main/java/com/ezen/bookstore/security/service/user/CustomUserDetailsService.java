package com.ezen.bookstore.security.service.user;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ezen.bookstore.admin.members.dto.MembersDTO;
import com.ezen.bookstore.security.mapper.UserMapper;
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserMapper userMapper;

    public CustomUserDetailsService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MembersDTO member = userMapper.loadUserByUsername(username);

        if (member == null) {
            throw new UsernameNotFoundException("아이디를 찾을 수 없습니다.");
        }

        // ROLE_USER 권한 부여하고 CustomUserDetails 반환
        return new CustomUserDetails(member, Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    }

}