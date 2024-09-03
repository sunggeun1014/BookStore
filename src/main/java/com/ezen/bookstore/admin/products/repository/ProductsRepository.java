package com.ezen.bookstore.admin.products.repository;

import com.ezen.bookstore.admin.products.dto.CategoryDTO;
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
            String book_state,
            Date start_date,
            Date end_date,
            String search_conditions,
            String word) {
        HashMap<String, Object> paraMap = new HashMap<>();

        paraMap.put("book_state", book_state);
        paraMap.put("start_date", start_date);
        paraMap.put("end_date", end_date);
        paraMap.put("search_conditions", search_conditions);
        paraMap.put("word", word);

        return sql.selectList("Products.findByCondition", paraMap);
    }

    public List<ProductsDTO> getBookState(String bookState) {
        return sql.selectList("Products.getBookState", bookState);
    }

    public ProductsDTO getBookDetail(String bookISBN){
        return sql.selectOne("Products.getDetail", bookISBN);
    }

    public List<CategoryDTO> getCategory() {
        return sql.selectList("Category.getCategory");
    }

    public void updateBookInfo(ProductsDTO productsDTO) {
        sql.update("Products.updateBookInfo", productsDTO);
    }

}

