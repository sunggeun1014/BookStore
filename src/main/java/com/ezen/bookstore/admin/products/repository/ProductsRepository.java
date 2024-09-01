package com.ezen.bookstore.admin.products.repository;

import com.ezen.bookstore.admin.products.dto.ProductsDTO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class ProductsRepository {
    private final SqlSessionTemplate sql;

    public List<ProductsDTO> getlist() {
        return sql.selectList("Products.getAll");
    }

    public List<ProductsDTO> findBookCondition(
            String bookState,
            Date startDate,
            Date endDate,
            String searchColumn,
            String searchKeyword) {
        HashMap<String, Object> paraMap = new HashMap<>();

        paraMap.put("book_state", bookState);
        paraMap.put("start_date", startDate);
        paraMap.put("end_date", endDate);
        paraMap.put("search_condition", searchColumn);
        paraMap.put("word", searchKeyword);

        return sql.selectList("Products.findByCondition", paraMap);
    }

    public List<ProductsDTO> getBookState(String bookState) {
        return sql.selectList("Products.getBookState", bookState);
    }

    public ProductsDTO getBookDetail(String bookISBN){
        return sql.selectOne("Products.getDetail", bookISBN);
    }

    public void updateBookInfo(ProductsDTO productsDTO) {
        sql.update("Products.updateBookInfo", productsDTO);
    }

}

