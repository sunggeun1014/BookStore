package com.ezen.bookstore.admin.members.service;

import java.util.List;

import com.ezen.bookstore.admin.members.dto.AdminMembersDTO;

public interface AdminMgntService {
	List<AdminMembersDTO> list();
	AdminMembersDTO detailList(String memberId);
	void updateMemberDetails(AdminMembersDTO membersDTO);
}
