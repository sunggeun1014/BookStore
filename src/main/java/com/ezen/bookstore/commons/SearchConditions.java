package com.ezen.bookstore.commons;

import lombok.Data;

@Data
public class SearchConditions {

	private String date_select;
	private String start_date;
	private String end_date;
	private String order_status;
	private String search_conditions;
	private String word;
}
