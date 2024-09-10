package com.ezen.bookstore.security.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.ezen.bookstore.admin.managers.dto.ManagersDTO;
import com.ezen.bookstore.admin.members.dto.MembersDTO;

@Mapper
public interface AdminMapper {

    @Select("SELECT manager_id, manager_pw, manager_name, manager_dept, manager_profile_original, manager_profile_changed,"
    		+ "	manager_email, manager_phoneNo, manager_addr, manager_detail_addr FROM managers WHERE manager_id = #{username}")
    ManagersDTO loadAdminByUsername(@Param("username") String username);
    // username = id    
}