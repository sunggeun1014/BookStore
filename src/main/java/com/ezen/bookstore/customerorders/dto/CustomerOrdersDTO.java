package com.ezen.bookstore.customerorders.dto;

import java.text.DecimalFormat;
import java.util.Date;

import lombok.Data;

@Data
public class CustomerOrdersDTO {

	private Integer order_num;        
	private String order_addr ;
	private String order_addr_detail; 
	private String common_ent_pw;
	private String order_delivery_status;       
	private String order_payment_status;     
	private Date order_purchase_date;      
	private Date order_modify_date;          
	private String member_id;
	private String member_name;
	
	private Integer order_detail_num;       
	private Integer order_detail_qty;       
	private String order_detail_status;             
	private String book_isbn; 
	private Integer order_detail_price;
	private Integer total_order_price;
	private String order_status;
	
    public String getFormatPrice() {
        if (this.total_order_price == null) {
            return "";
        }
        DecimalFormat df = new DecimalFormat("#,###원");
        return df.format(this.total_order_price);
    }
}
