package com.ezen.bookstore.security.service.admin;


import java.util.ArrayList;
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

import jakarta.servlet.http.HttpServletRequest;

@Service
public class CustomAdminDetailsService implements UserDetailsService {

    private final AdminMapper adminMapper;
    private final HttpServletRequest request;

    public CustomAdminDetailsService(AdminMapper adminMapper, HttpServletRequest request) {
        this.adminMapper = adminMapper;
        this.request = request;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AdminManagersDTO manager = adminMapper.loadAdminByUsername(username);
        
        if (manager == null) {
            throw new UsernameNotFoundException("아이디를 찾을 수 없습니다.");
        }

        // 사용자 권한 부여 (manager_dept에 따라 부여)
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        String requestURI = request.getRequestURI();
        
        if(requestURI.startsWith("/mobile/admin/login")) {
        	if("01".equals(manager.getManager_dept())) {
                authorities.addAll(getWorkerAuthorities(manager));  
        	}
        } else if(requestURI.startsWith("/admin/login")) {
        	if("02".equals(manager.getManager_dept())) {
                authorities.addAll(getWorkerAuthorities(manager)); 
                authorities.addAll(getOperatorAuthorities(manager));  
        	}
        }
        
        return new CustomAdminDetails(manager, authorities);

    }

    // ROLE_OPERATOR 권한 부여
    private Collection<? extends GrantedAuthority> getOperatorAuthorities(AdminManagersDTO manager) {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_OPERATOR"));
    }

    // ROLE_WORKER 권한 부여
    private Collection<? extends GrantedAuthority> getWorkerAuthorities(AdminManagersDTO manager) {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_WORKER"));
    }
}


