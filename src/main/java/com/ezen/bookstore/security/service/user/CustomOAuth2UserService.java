package com.ezen.bookstore.security.service.user;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.ezen.bookstore.security.mapper.UserMapper;
import com.ezen.bookstore.user.members.dto.UserMembersDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserMapper userMapper;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        Map<String, Object> attributes = oAuth2User.getAttributes();

        String kakaoId = null;
        String naverId = null;
        UserMembersDTO user = null;

        // 카카오 사용자 정보 처리
        if ("kakao".equals(registrationId)) {
            Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
            kakaoId = String.valueOf(attributes.get("id")); // 카카오 유저의 고유 ID
            user = userMapper.loadUserByKakaoLoginCd(kakaoId);

        // 네이버 사용자 정보 처리
        } else if ("naver".equals(registrationId)) {
            Map<String, Object> response = (Map<String, Object>) attributes.get("response");            
            naverId = String.valueOf(response.get("id")); // 네이버 유저의 고유 ID
            user = userMapper.loadUserByNaverLoginCd(naverId);
           
        }

        // 사용자가 존재하지 않으면 예외 처리
        if (user == null) {
            throw new AccountNotLinkedException("연동된 계정이 존재하지 않습니다.");
        }

        // 확장된 속성 추가
        Map<String, Object> extendedAttributes = new HashMap<>(attributes);
        extendedAttributes.put("userMembersDTO", user); 

        SimpleGrantedAuthority roleUserAuthority = new SimpleGrantedAuthority("ROLE_USER");

        
        // 카카오와 네이버에 따라 다른 리턴 처리
        if ("kakao".equals(registrationId)) {
            return new DefaultOAuth2User(
                Collections.singleton(roleUserAuthority), 
                extendedAttributes,
                "id"
            );
        } else if ("naver".equals(registrationId)) {
            return new DefaultOAuth2User(
                Collections.singleton(roleUserAuthority),
                extendedAttributes,
                "response"
            );
        } else {
            throw new OAuth2AuthenticationException("지원하지 않는 OAuth2 제공자입니다.");
        }
    }

}
