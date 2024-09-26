package com.ezen.bookstore.user.payment.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class OrderItemDTO {
    private String book_isbn;
    private String book_name;
    private Integer book_price;
    private Integer cart_purchase_qty;
    private String book_thumbnail_changed;
    private Integer cart_num;

    @Getter
    @Setter
    private boolean selected;

}
