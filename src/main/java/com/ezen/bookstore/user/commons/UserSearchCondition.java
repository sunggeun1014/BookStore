package com.ezen.bookstore.user.commons;

import com.ezen.bookstore.commons.Pagination;

import lombok.Data;

@Data
public class UserSearchCondition {
	private String word;
	private String search_conditions;
	private String search_field;
	private Integer orderByValue;
	private Integer categoryCountryType;
}
