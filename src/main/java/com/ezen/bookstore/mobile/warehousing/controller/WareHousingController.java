package com.ezen.bookstore.mobile.warehousing.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ezen.bookstore.commons.CommonConstants;

@RequestMapping("/mobile/admin")
@Controller
public class WareHousingController {
	
	@GetMapping("/warehousing-page")
	public String warehousingPage(Model model) {
		
		model.addAttribute("menuItems", CommonConstants.MENU_ITEMS);
		
		return "mobile/warehousing/warehousing";
	}
	
	@GetMapping("/stockIn")
	public String stockIn(@RequestParam("zoneNum") String zoneNum, Model model) {
		model.addAttribute("zoneNum", zoneNum);
		return "mobile/warehousing/stockIn";
	}
	
}
