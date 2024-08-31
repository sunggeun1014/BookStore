package com.ezen.bookstore.admin.customerorders.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ezen.bookstore.admin.commons.AccountManagement;
import com.ezen.bookstore.admin.commons.SearchCondition;
import com.ezen.bookstore.admin.customerorders.dto.CustomerOrdersDTO;
import com.ezen.bookstore.admin.customerorders.service.CustomerOrdersService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/customer_orders_rest")
public class CustomerOrdersRestController {
	
	private final CustomerOrdersService cos;
	
    @GetMapping(value = "/customerOrders", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getTableData() {
        Map<String, Object> responseMap = new HashMap<>();
        List<CustomerOrdersDTO> list = cos.getCustomerOrdersList();
        
        responseMap.put("data", list);
        responseMap.put("recordsTotal", list.size());

        return responseMap;
    }
    
    @GetMapping(value = "/dataFilter", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getDataFilter(SearchCondition condition) {
    	Map<String, Object> responseMap = new HashMap<>();
        List<CustomerOrdersDTO> list = cos.getDataFilter(condition);
        
        responseMap.put("data", list);
        responseMap.put("recordsTotal", list.size());
    	
    	return responseMap;
    }
    
    @PostMapping(value = "/deliveryRequest")
    public int deliveryRequest(@RequestBody List<Integer> order_nums, HttpSession session) {
    	return cos.deliveryRequestSave(order_nums, session.getAttribute(AccountManagement.ADMIN_ID).toString());
    }
    
    
}
