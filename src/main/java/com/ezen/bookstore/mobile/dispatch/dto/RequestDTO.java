package com.ezen.bookstore.mobile.dispatch.dto;

import java.sql.Timestamp;
import java.util.List;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class RequestDTO {
    String book_isbn;
    Integer total_order_quantity;
    String book_name;
    Timestamp book_request_date;
    String order_num;
    String manager_id;
    Integer log_transaction_num;
    String zone_num;
    Integer request_num;
    

    // 여러 주문 항목을 받을 리스트 필드
    List<RequestDTO> request_details;
}
