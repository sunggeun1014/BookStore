package com.ezen.bookstore.admin.security.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.ezen.bookstore.admin.managers.dto.ManagersDTO;

@Mapper
public interface UserMapper {

    @Select("SELECT manager_id, manager_pw, manager_name, manager_dept FROM managers WHERE manager_id = #{username}")
    ManagersDTO loadUserByUsername(@Param("username") String username);
    
    
}