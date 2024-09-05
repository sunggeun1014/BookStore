package com.ezen.bookstore.admin.home.repository;

import com.ezen.bookstore.admin.inquiries.dto.InquiriesDTO;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class HomeRepository {
    private final SqlSession sql;

//    public List<InquiriesDTO> getInquiries(){
//        return sql.selectList()
//    }

}
