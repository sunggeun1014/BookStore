package com.ezen.bookstore.commons;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class PaginationProcess {
	
	public Pagination process(Pagination pagination, List<?> list) {
		pagination.setTotalPages(list.size());
		
		return pagination;
	}

}
