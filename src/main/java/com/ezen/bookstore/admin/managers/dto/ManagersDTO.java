package com.ezen.bookstore.admin.managers.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class ManagersDTO {
	
	private String manager_id;
	private String manager_pw;
	private String manager_name;
	private String manager_phoneNo;
	private String manager_email;
	private String manager_profile_original;
	private String manager_profile_changed;
	private String manager_addr;
	private String manager_detail_addr;
	private String manager_dept;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date manager_join_date;
	
}
