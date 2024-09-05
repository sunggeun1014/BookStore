package com.ezen.bookstore.admin.warehouse.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class WarehouseDTO {
	
	private String inv_isbn;
	private String inv_title;
	private Integer inv_qty;
	private Timestamp inv_registration_date;
	private String zone_num;
}
