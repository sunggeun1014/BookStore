package com.ezen.bookstore.admin.supplierorders.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ezen.bookstore.admin.supplierorders.dto.SupplierOrdersDTO;
import com.ezen.bookstore.admin.supplierorders.service.SupplierOrdersService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/supplier_orders")
public class SupplierOrdersController {
   
   private final SupplierOrdersService sos;
   
   @GetMapping("/list")
   public String supplierOrdersList(Model model) {
      model.addAttribute("template", "/admin/supplier_orders/supplierList");
      
      return "/admin/index";
   }
   
   @GetMapping("/register")
   public String orderRegister(Model model) {
      model.addAttribute("template", "/admin/supplier_orders/supplierRegister");
      
      return "/admin/index";
   }
   
   @GetMapping("/detail")
   public String orderRegister(Model model, Integer order_num) {
      model.addAttribute("detail", sos.getSupplierOrdersDetail(order_num));
      model.addAttribute("detailList", sos.getSupplierOrdersDetailList(order_num));
      model.addAttribute("template", "/admin/supplier_orders/supplierDetail");
      
      return "/admin/index";
   }
   
   @PostMapping("/orderConfirm")
   public String orderConfirm(Model model, @RequestBody List<SupplierOrdersDTO> list) {
      System.out.println("sdfasfddfasdasffdasafd");
      for(SupplierOrdersDTO dto : list) {
         System.out.println(dto.getOrder_detail_title());
      }
      
      model.addAttribute("template", "/admin/supplier_orders/supplierList");
      
      return "/admin/index";
   }
   
   
}
