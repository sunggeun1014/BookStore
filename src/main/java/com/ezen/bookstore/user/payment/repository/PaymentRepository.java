package com.ezen.bookstore.user.payment.repository;

import com.ezen.bookstore.admin.customerorders.dto.CustomerOrdersDTO;
import com.ezen.bookstore.user.payment.dto.UserOrderDTO;
import com.ezen.bookstore.user.payment.dto.UserOrderDetailsDTO;
import com.siot.IamportRestClient.response.Payment;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

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
}
