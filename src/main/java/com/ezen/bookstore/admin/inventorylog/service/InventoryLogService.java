package com.ezen.bookstore.admin.inventorylog.service;

import java.util.List;

import com.ezen.bookstore.admin.commons.SearchCondition;
import com.ezen.bookstore.admin.inventorylog.dto.InventoryLogDTO;

public interface InventoryLogService {

	public List<InventoryLogDTO> getInventoryLogList();
	public List<InventoryLogDTO> getDataFilter(SearchCondition condition);
	public InventoryLogDTO getInventoryLogDetail(Integer log_transaction_num);
	public List<InventoryLogDTO> getInventoryLogDetailList(Integer log_transaction_num);
	
}
