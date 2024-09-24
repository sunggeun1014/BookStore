package com.ezen.bookstore.admin.warehouse.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)

public class WarehouseDTO implements Serializable {

	private static final long serialVersionUID = -8687202117698780883L;
	
	private String inv_isbn;
	private String inv_title;
	private Integer inv_qty;
	private Timestamp inv_registration_date;
	private String zone_num;
}
