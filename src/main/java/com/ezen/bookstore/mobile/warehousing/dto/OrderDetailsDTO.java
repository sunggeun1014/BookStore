package com.ezen.bookstore.mobile.warehousing.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class OrderDetailsDTO {
	Integer log_transaction_num; 
	String order_detail_isbn;
	String order_detail_title;
	Integer order_detail_qty;
	String zone_num;
	Integer order_detail_num;
}
