package com.ezen.bookstore.security.service.user;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ezen.bookstore.admin.members.dto.MembersDTO;

public class CustomUserDetails implements UserDetails {

    private static final long serialVersionUID = 3779630099302073986L;

    private final MembersDTO membersDTO;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomUserDetails(MembersDTO membersDTO, Collection<? extends GrantedAuthority> authorities) {
        this.membersDTO = membersDTO;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public MembersDTO getMembersDTO() {
        return membersDTO;
    }

    public String getMemberId() {
        return membersDTO.getMember_id();
    }

    @Override
    public String getPassword() {
        return membersDTO.getMember_pw();
    }

    @Override
    public String getUsername() {
        return getMemberId();
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
