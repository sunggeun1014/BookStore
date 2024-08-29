package com.ezen.bookstore.admin.customerorders.dto;

import java.sql.Timestamp;
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
	private Timestamp order_purchase_date;      
	private Timestamp order_modify_date;          
	private String member_id;
	private String retrieve_addr;
	private String retrieve_addr_detail;
	
	private Integer order_detail_num;       
	private Integer order_detail_qty;       
	private String order_detail_status;             
	private String book_isbn; 
	private Integer order_detail_price;
	private Integer total_order_price;
	private String order_status;
	
	private String member_name;
	private String member_pw;
	private String member_email;
	private String member_phoneno;
	private String member_addr;
	private String member_detail_addr;
	private String naver_login_cd;
	private String kakao_login_cd;
	private Timestamp member_date;
	
    public String getFormatPrice() {
        if (this.total_order_price == null) {
            return "";
        }
        DecimalFormat df = new DecimalFormat("#,###원");
        return df.format(this.total_order_price);
    }
}
