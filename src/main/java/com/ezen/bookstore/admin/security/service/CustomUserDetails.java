package com.ezen.bookstore.admin.security.service;


import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ezen.bookstore.admin.managers.dto.ManagersDTO;

public class CustomUserDetails implements UserDetails {

	private static final long serialVersionUID = -1573691849618309598L;
	
	private final ManagersDTO managersDTO;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(ManagersDTO managersDTO, Collection<? extends GrantedAuthority> authorities) {
        this.managersDTO = managersDTO;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
    
    public ManagersDTO getManagersDTO() {
		return managersDTO;
	}

	public String getPassword() {
        return managersDTO.getManager_pw();
    }

    
    public String getUsername() {
        return managersDTO.getManager_id();
    }
    
    public String getManagerName() {
        return managersDTO.getManager_name();
    }

    public String getManagerDept() {
        return managersDTO.getManager_dept();
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    
    
}
