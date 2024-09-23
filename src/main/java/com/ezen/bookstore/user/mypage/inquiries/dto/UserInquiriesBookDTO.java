package com.ezen.bookstore.user.mypage.inquiries.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class UserInquiriesBookDTO {
	String book_name;
	Integer order_detail_num;
	Integer order_detail_qty;
}
