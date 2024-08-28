package com.ezen.bookstore.commons;

import lombok.Data;

@Data
public class SearchCondition {
	private String date_column;
	private String start_date;
	private String end_date;
	private String order_status;
	private String search_conditions;
	private String word;
}
