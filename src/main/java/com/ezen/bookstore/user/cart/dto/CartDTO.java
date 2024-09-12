package com.ezen.bookstore.user.cart.dto;

import lombok.Data;

@Data
public class CartDTO {
    private Integer cart_num;
    private String book_isbn;
    private String member_id;
    private Integer cart_purchase_qty;
}
