package com.ezen.bookstore.user.cart.dto;

import lombok.Data;

@Data
public class UserCartDTO {
	private Integer cart_num;
	private String book_isbn;
	private String book_thumbnail_changed;
	private Integer book_price;
	private String member_id;
	private Integer cart_purchase_qty;
	private Integer cart_total_price;
}
