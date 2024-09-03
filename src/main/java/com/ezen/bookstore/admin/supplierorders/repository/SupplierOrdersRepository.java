package com.ezen.bookstore.admin.supplierorders.repository;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import com.ezen.bookstore.admin.commons.SearchCondition;
import com.ezen.bookstore.admin.supplierorders.dto.SupplierOrdersDTO;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class SupplierOrdersRepository {
	
	private final SqlSessionTemplate sql; 
	
	public List<SupplierOrdersDTO> getSupplierOrdersList() {
		return sql.selectList("SupplierOrders.getList");
	}
	
	public List<SupplierOrdersDTO> getDataFilter(SearchCondition condition) {
		return sql.selectList("SupplierOrders.getList", condition);
	}
	
	public List<SupplierOrdersDTO> getSupplierOrdersDetailList(Integer order_num) {
		return sql.selectList("SupplierOrders.getDetailList", order_num);
	}
	
	public SupplierOrdersDTO getSupplierOrdersDetail(Integer order_num) {
		return sql.selectOne("SupplierOrders.getDetail", order_num);
	}
	
}