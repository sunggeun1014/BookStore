package com.ezen.bookstore.user.bookcategory.dto;

import lombok.Data;

@Data
public class UserBookCategoryDTO {
	
	private Integer category_num;
	private String category_country_type;
	private String category_name;
	private Integer category_cnt;
}
