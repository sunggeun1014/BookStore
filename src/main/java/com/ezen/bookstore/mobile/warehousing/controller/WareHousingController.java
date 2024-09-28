package com.ezen.bookstore.mobile.warehousing.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/mobile/admin")
@Controller
public class WareHousingController {
	
	@GetMapping("/warehousing-page")
	public String warehousingPage() {
		return "mobile/warehousing/warehousing";
	}
}
