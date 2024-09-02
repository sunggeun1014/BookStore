package com.ezen.bookstore.admin.supplierorders.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class SupplierOrdersDTO {

	private Integer order_num;       
	private String order_status;      
	private Timestamp order_date; 
	private String manager_id;
	private Integer order_total_qty; 
	private Integer order_total_price;
}
