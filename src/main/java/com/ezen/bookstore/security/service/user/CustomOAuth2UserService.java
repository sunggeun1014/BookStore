package com.ezen.bookstore.security.service.user;

import java.util.HashMap;
import java.util.Map;

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
        // 사용자 정보 처리 (카카오의 경우)
        if ("kakao".equals(registrationId)) {
            Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
            kakaoId = String.valueOf(attributes.get("id")); // 카카오 유저의 고유 ID
        }

        // DB에서 사용자 조회 (이메일 또는 kakaoId로 조회)
        UserMembersDTO user = userMapper.loadUserByKakaoLoginCd(kakaoId);
        
        // 사용자가 존재하지 않으면 예외 처리
        if (user == null) {
            throw new OAuth2AuthenticationException("해당 사용자가 존재하지 않습니다.");
        }

        Map<String, Object> extendedAttributes = new HashMap<>(attributes);
        extendedAttributes.put("userMembersDTO", user);  // DTO를 속성에 추가

        return new DefaultOAuth2User(oAuth2User.getAuthorities(), extendedAttributes, "id");
    }
}