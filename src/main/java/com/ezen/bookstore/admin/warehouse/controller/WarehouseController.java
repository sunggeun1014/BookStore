package com.ezen.bookstore.admin.warehouse.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ezen.bookstore.admin.warehouse.dto.WarehouseDTO;
import com.ezen.bookstore.admin.warehouse.dto.ZoneNumDTO;
import com.ezen.bookstore.admin.warehouse.service.WarehouseService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Controller
@RequestMapping("/admin/warehouse")
public class WarehouseController {
	
	private final WarehouseService warehouseService;
	
	@GetMapping("/warehouse")
	public String warehouse() {
		return "admin/warehouse/warehouse";
	}
	
	@GetMapping(value = "/json", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, Object> tableData() {
		List<WarehouseDTO> tables = warehouseService.list();
		
		Map<String, Object> response = new HashMap<>();
		response.put("data", tables);
        response.put("size", tables.size());

		return response;
	}
	
	@PostMapping("/details")
	public String showStockDetails(@RequestParam("inv_isbn") String invIsbn, Model model) {
		WarehouseDTO stockDetails = warehouseService.detailList(invIsbn);
		List<ZoneNumDTO> zoneNumList =  warehouseService.zoneNumList();
		
		model.addAttribute("stockDetails", stockDetails);
		model.addAttribute("zoneNumList", zoneNumList);
		
				
		return "admin/warehouse/warehouseDetail";
	}
	
	@PostMapping("/update")
	public String upateDetails(@ModelAttribute WarehouseDTO warehouseDTO) {
		
		warehouseService.updateStockDetails(warehouseDTO);
		
		return "redirect:/admin/warehouse/warehouse";
	}
	
}
