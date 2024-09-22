package com.ezen.bookstore.admin.customerorders.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezen.bookstore.admin.customerorders.dto.CustomerOrdersDTO;
import com.ezen.bookstore.admin.customerorders.dto.CustomerOrdersListDTO;
import com.ezen.bookstore.admin.customerorders.repository.CustomerOrdersRepository;
import com.ezen.bookstore.admin.inventorylog.repository.InventoryLogRepository;
import com.ezen.bookstore.admin.warehouse.repository.WarehouseRepository;
import com.ezen.bookstore.commons.SearchCondition;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomerOrdersServiceImpl implements CustomerOrdersService {
	
	private final CustomerOrdersRepository cor;
	private final WarehouseRepository wr;
	private final InventoryLogRepository ilr;
	
	@Override
	public List<CustomerOrdersDTO> getCustomerOrdersList() {
		try {
			return cor.getCustomerOrdersList();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public List<CustomerOrdersDTO> getDataFilter(SearchCondition condition) {
		try {
			return cor.getDataFilterList(condition);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public int deliveryRequestSave(List<Integer> order_nums, String manager_id) {
		int result = 0;
		
		try {
			if(cor.deliveryRequestSave(manager_id) > 0) {
				for(int order_num : order_nums) {
					result += cor.deliveryDetailSave(order_num);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public CustomerOrdersDTO getCustomerOrdersDetail(int order_num) {
		try {
			return cor.getCustomerOrdersDetail(order_num);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public List<CustomerOrdersDTO> getCustomerOrdersDetailList(int order_num) {
		try {
			return cor.getCustomerOrdersDetailList(order_num);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Transactional
	@Override
	public void orderStatusUpdate(CustomerOrdersListDTO list, int order_num, String order_selected_status, String manager_id) {
		try {
			if(order_selected_status.equals("05") || order_selected_status.equals("06")) {
				cor.orderPaymentUpdate(order_num);
			}
			
			for(int i = 0; i < list.getOrder_detail_num().size(); i++) {
				String isbn = list.getBook_isbn().get(i);
				String bookTitle = list.getBook_name().get(i);
				String zoenNum = getZone_num(isbn);
				Integer inputQty = list.getInput_qty().get(i);
				
				if(order_selected_status.equals("06")) {
					// 입출고 기록 (교환)
					wr.invQtyUpdate(isbn, inputQty);
					
					ilr.invLogRequestInsert("04", manager_id);
					ilr.invLogRequestDetailInsert(isbn, bookTitle, zoenNum, inputQty);
				} else if(order_selected_status.equals("07")) {
					// 입출고 기록 (반품)
					wr.invQtyUpdate(isbn, Math.negateExact(inputQty));
					
					ilr.invLogRequestInsert("03", manager_id);
					ilr.invLogRequestDetailInsert(isbn, bookTitle, zoenNum, inputQty);
				}
				
				cor.orderStatusUpdate(list.getOrder_detail_num().get(i), order_selected_status, list.getInput_qty().get(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
	        throw e;
		}
		
	}

	private String getStatus(String statusCd) {
		Map<String, String> map = new HashMap<>();
		
		map.put("01", "주문완료");
		map.put("02", "취소요청");
		map.put("03", "교환요청");
		map.put("04", "반품요청");
		map.put("05", "취소완료");
		map.put("06", "교환완료");
		map.put("07", "반품완료");
		map.put("08", "처리불가");
		
		return map.get(statusCd);
	}
	
	private String getZone_num(String isbn) {
		try {
			return wr.getZoneNum(isbn);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
}
