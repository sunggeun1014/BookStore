package com.ezen.bookstore.admin.supplierorders.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ezen.bookstore.admin.commons.AccountManagement;
import com.ezen.bookstore.admin.commons.SearchCondition;
import com.ezen.bookstore.admin.managers.dto.ManagersDTO;
import com.ezen.bookstore.admin.supplierorders.dto.SupplierOrdersDTO;
import com.ezen.bookstore.admin.supplierorders.service.SupplierOrdersService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/supplierOrdersRest")
public class SupplierOrdersRestController {
	
	private final SupplierOrdersService sos;
	
	@GetMapping(value = "/supplierOrders", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getTableData() {
        Map<String, Object> responseMap = new HashMap<>();
        List<SupplierOrdersDTO> list = sos.getSupplierOrdersList();
        
        responseMap.put("data", list);
        responseMap.put("recordsTotal", list.size());

        return responseMap;
    }
	
    @GetMapping(value = "/dataFilter", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> getDataFilter(SearchCondition condition) {
    	Map<String, Object> responseMap = new HashMap<>();
        List<SupplierOrdersDTO> list = sos.getDataFilter(condition);
        
        responseMap.put("data", list);
        responseMap.put("recordsTotal", list.size());
    	
    	return responseMap;
    }

	@PostMapping("/orderConfirm")
	public void orderConfirm(Model model, @RequestBody List<SupplierOrdersDTO> list, HttpSession session) {
		sos.orderConfirmInsert(list, ((ManagersDTO)session.getAttribute(AccountManagement.MANAGER_INFO)).getManager_id());
		
		model.addAttribute("template", "/admin/supplierOrders/supplierList");
	}
}
