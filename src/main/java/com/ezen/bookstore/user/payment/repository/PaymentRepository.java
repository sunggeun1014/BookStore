package com.ezen.bookstore.user.payment.repository;

import com.ezen.bookstore.admin.customerorders.dto.CustomerOrdersDTO;
import com.ezen.bookstore.user.payment.dto.CompleteDetailDTO;
import com.ezen.bookstore.user.payment.dto.CompleteOrderDTO;
import com.ezen.bookstore.user.payment.dto.UserOrderDTO;
import com.ezen.bookstore.user.payment.dto.UserOrderDetailsDTO;
import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Repository
public class PaymentRepository {
    private final SqlSessionTemplate sql;

    public UserOrderDTO getOrder(int order_num, String member_id) {
        HashMap<String, Object> param = new HashMap<>();
        param.put("order_num", order_num);
        param.put("member_id", member_id);

        return sql.selectOne("OrderPayment.getOrderDetail", param);
    }

    public List<UserOrderDetailsDTO> getDetails(int order_num) {
        return sql.selectList("OrderPayment.OrderDetail", order_num);
    }

    public void insertOrder(CompleteOrderDTO completeOrderDTO) throws SQLException {
        sql.insert("OrderPayment.insertCustomerOrder", completeOrderDTO);
    }

    public void insertOrderDetail(CompleteDetailDTO completeDetailDTO) throws SQLException {
        sql.insert("OrderPayment.insertOrderDetail", completeDetailDTO);
    }

}
