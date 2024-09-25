package com.ezen.bookstore.user.members.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ezen.bookstore.user.members.dto.UserMembersDTO;

@Mapper
public interface UserMgntMapper {
    
    int addMember(UserMembersDTO userMembersDTO);
    int findById(@Param("member_id") String memberId);
    int findByKakaoId(@Param("kakao_login_cd") String kakaoId);
    int findByNaverId(@Param("naver_login_cd") String naverId);
 
    int getBasketCount(@Param("member_id") String memberId);
    List<UserMembersDTO> findMember(@Param("member_name") String name, @Param("member_email") String email);
    int memberVerification(UserMembersDTO userMembersDTO);
    int modifyMemberPw(UserMembersDTO userMembersDTO);
}
