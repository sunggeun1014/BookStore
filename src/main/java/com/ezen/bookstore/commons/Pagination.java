package com.ezen.bookstore.commons;

import lombok.Data;

@Data
public class Pagination {
    private Integer currentPage = 1;
    private Integer pageSize = 10;
    private Integer totalPages;
}
