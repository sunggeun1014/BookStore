package com.ezen.bookstore.admin.inventorylog.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class InventoryLogDTO {
	private Integer log_transaction_num;
	private String manager_id;
	private String log_transaction_status;
	private Timestamp log_operation_date;
	private Integer order_num;
}
