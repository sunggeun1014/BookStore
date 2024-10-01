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
    
    List<RequestDTO> request_details;
}
