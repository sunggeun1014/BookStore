package com.ezen.bookstore.admin.customerorders.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ezen.bookstore.admin.commons.SearchCondition;
import com.ezen.bookstore.admin.customerorders.service.CustomerOrdersService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/customer_orders")
public class CustomerOrdersController {
   
   private final CustomerOrdersService cos;

   @GetMapping("/list")
   public String customerOrdersList(Model model) {
      model.addAttribute("template", "/admin/customer_orders/customerList");
      
      return "/admin/index";
   }
   
   @GetMapping("/detail")
   public String customerOrdersList(Model model, int order_num, SearchCondition condition) {
      model.addAttribute("detail", cos.getCustomerOrdersDetail(order_num));
      model.addAttribute("detailList", cos.getCustomerOrdersDetailList(order_num));
      model.addAttribute("condition", condition);
      
      model.addAttribute("template", "/admin/customer_orders/customerDetail");
      
      return "/admin/index";
   }
    
    @GetMapping(value = "/orderStatusUpdate")
    public String orderStatusUpdate(@RequestParam(value = "order_detail_num") List<Integer> list, String order_detail_status, int order_num) {
       cos.orderStatusUpdate(list, order_detail_status);
       
       return "redirect:/admin/customer_orders/detail?order_num=" + order_num;
    }

}
