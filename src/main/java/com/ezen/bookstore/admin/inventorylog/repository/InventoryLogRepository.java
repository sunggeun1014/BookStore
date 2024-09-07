package com.ezen.bookstore.admin.inventorylog.repository;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.ezen.bookstore.admin.commons.SearchCondition;
import com.ezen.bookstore.admin.inventorylog.dto.InventoryLogDTO;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class InventoryLogRepository {
	
	private final SqlSessionTemplate sql;

	public List<InventoryLogDTO> getInventoryLogList() {
		return sql.selectList("InventoryLog.getList");
	}
	
	public List<InventoryLogDTO> getDataFilter(SearchCondition condition) {
		return sql.selectList("InventoryLog.getList", condition);
	}
	
	public InventoryLogDTO getInventoryLogDetail(Integer log_transaction_num) {
		return sql.selectOne("InventoryLog.getDetail", log_transaction_num);
	}
}
