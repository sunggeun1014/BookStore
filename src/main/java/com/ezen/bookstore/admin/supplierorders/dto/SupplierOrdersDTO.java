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
	
	private Integer order_detail_num;
	private String order_detail_isbn;
	private String order_detail_title;
	private Integer order_detail_qty;
	private Integer order_detail_received_qty;
	private Integer order_detail_price;
	private String order_detail_publisher;
	private Integer detail_total_price;
	
	private String manager_name;
}
