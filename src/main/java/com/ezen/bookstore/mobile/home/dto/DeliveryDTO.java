package com.ezen.bookstore.mobile.home.dto;

import java.sql.Timestamp;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class DeliveryDTO {
	
	Integer request_num;
	Timestamp request_date;
	String manager_id;
	Timestamp request_end_date;
	
	Integer request_detail_num;
	Integer order_num;
	Integer order_detail_qty;
	Integer total_order_qty;
	
	String book_name;
	String book_isbn;
	
	String recipient_name;
	String order_addr;
	String order_addr_detail;
	String order_delivery_status;
	Timestamp order_purchase_date;
	Timestamp order_modify_date;
}
