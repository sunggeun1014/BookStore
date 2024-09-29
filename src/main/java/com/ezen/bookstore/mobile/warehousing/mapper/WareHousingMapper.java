package com.ezen.bookstore.mobile.warehousing.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ezen.bookstore.admin.supplierorders.dto.SupplierOrdersDTO;
import com.ezen.bookstore.mobile.warehousing.dto.OrderDetailsDTO;

@Mapper
public interface WareHousingMapper {
	
	List<SupplierOrdersDTO> getOrderDetails(@Param("order_num") Integer orderNum);
	void insertInventoryLog(SupplierOrdersDTO supplierOrdersDTO);
	Integer getLastLogTransactionNum();
    void insertInventoryLogDetail(OrderDetailsDTO orderDetailsDTO);
    
    void updateReceivedQty(@Param("order_detail_num")Integer orderDetailNum, @Param("order_detail_qty")Integer orderDetailQty);
}
