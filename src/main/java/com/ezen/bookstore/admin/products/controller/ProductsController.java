package com.ezen.bookstore.admin.products.controller;

import com.ezen.bookstore.admin.commons.SearchCondition;
import com.ezen.bookstore.admin.products.dto.ProductsDTO;
import com.ezen.bookstore.admin.products.service.ProductsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin/products")
@Controller
public class ProductsController {

    private final ProductsService productService;

    @GetMapping(value = "/json", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> tableData(
            @RequestParam(required = false) String book_state,
            @RequestParam(required = false) String start_date,
            @RequestParam(required = false) String end_date,
            @RequestParam(required = false) String search_condition,
            @RequestParam(required = false) String word
    ) throws ParseException {
        List<ProductsDTO> products;

        // 검색 조건을 담을 객체 생성
        SearchCondition condition = new SearchCondition();
        condition.setBook_state(book_state);
        condition.setStart_date(start_date);
        condition.setEnd_date(end_date);
        condition.setSearch_conditions(search_condition);
        condition.setWord(word);

        // 모든 검색 조건을 한 번에 서비스로 전달
        products = productService.getBooksByCondition(condition);

        // DataTables가 요구하는 형식으로 JSON 데이터 구성
        Map<String, Object> response = new HashMap<>();
        response.put("data", products);
        return response;
    }

    //@RequestMapping(value = "/editProduct", method = {RequestMethod.GET, RequestMethod.POST})
    @GetMapping("/editProduct")
    public String getBookDetail(@RequestParam("book_isbn") String bookISBN, Model model) {
        if (bookISBN != null || bookISBN.isEmpty()) {
            log.warn("isbn 못받아왔다");
        }
        log.info("Received request for book ISBN: {}", bookISBN);
        ProductsDTO productDetail = productService.detailList(bookISBN);
        log.info("Retrieved product detail: {}", productDetail);

        model.addAttribute("product_detail", productDetail);

        model.addAttribute("template", "/admin/products/edit-product");

        return "admin/index";
    }

    @PostMapping("/editProduct")
    public String editBookDetail(@RequestParam("book_isbn") String bookISBN, @ModelAttribute ProductsDTO productDTO) {
        log.info("Received request to update book with ISBN: {}", bookISBN);
        productDTO.setBook_isbn(bookISBN);
        productService.updateBookInfo(productDTO);
        return "redirect:/admin/products/editProduct?book_isbn=" + bookISBN;
    }

    @GetMapping("/addProduct")
    public String addBook(Model model) {
        model.addAttribute("template", "/admin/products/add-product");
        return "admin/index";
    }
}
