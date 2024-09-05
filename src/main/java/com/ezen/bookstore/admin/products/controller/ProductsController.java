package com.ezen.bookstore.admin.products.controller;

import com.ezen.bookstore.admin.commons.SearchCondition;
import com.ezen.bookstore.admin.products.dto.CategoryDTO;
import com.ezen.bookstore.admin.products.dto.InventoryDTO;
import com.ezen.bookstore.admin.products.dto.ProductsDTO;
import com.ezen.bookstore.admin.products.service.ProductsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin/products")
@Controller
public class ProductsController {

    private final ProductsService productService;
    
    @GetMapping("/products")
    public String products() {
    	return "admin/products/products";
    }
    
    @GetMapping(value = "/json", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> tableData(
            @RequestParam(required = false) String book_state,
            @RequestParam(required = false) String start_date,
            @RequestParam(required = false) String end_date,
            @RequestParam(required = false) String search_conditions,
            @RequestParam(required = false) String word
    ) throws ParseException {
        List<ProductsDTO> products;

        // 검색 조건을 담을 객체 생성
        SearchCondition condition = new SearchCondition();
        condition.setBook_state(book_state);
        condition.setStart_date(start_date);
        condition.setEnd_date(end_date);
        condition.setSearch_conditions(search_conditions);
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
        ProductsDTO productDetail = productService.detailList(bookISBN);
        List<CategoryDTO> bookCategory = productService.categoryList();

        model.addAttribute("product_detail", productDetail);
        model.addAttribute("book_category", bookCategory);


        return "admin/products/edit-product";
    }

    @PostMapping("/editProduct")
    public String editBookDetail(@RequestParam("book_isbn") String bookISBN, ProductsDTO productDTO) {

        productDTO.setBook_isbn(bookISBN);

        productService.updateBookInfo(productDTO);
        return "redirect:/admin/products/editProduct?book_isbn=" + bookISBN;
    }

    @GetMapping("/addProduct")
    public String addBook(Model model) {
        List<CategoryDTO> bookCategory = productService.categoryList();

        model.addAttribute("book_category", bookCategory);


        return "admin/products/add-product";
    }

    @GetMapping(value = "/inventory/json", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String, Object> getInventoryData() {
        List<InventoryDTO> inventory = productService.inventoryList();

        Map<String, Object> response = new HashMap<>();
        response.put("data", inventory);
        return response;
    }

    @PostMapping("/addProduct")
    public String insertBook(@ModelAttribute ProductsDTO productDTO, Model model) throws IOException {

        productService.insertBook(productDTO);

        return "redirect:/admin/products/products";
    }

    @PostMapping("/checkISBN")
    public ResponseEntity<Map<String, Boolean>> checkISBN(@RequestBody Map<String, String> request) {
        String bookISBN = request.get("book_isbn");
        boolean exists = productService.existsIsbn(bookISBN);

        Map<String, Boolean> response = new HashMap<>();
        response.put("exists", exists);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/delete")
    public String deleteBook(@RequestBody List<String> bookISBNs) {
        for (String bookISBN : bookISBNs) {
            productService.deleteBook(bookISBN);
        }
        return "redirect:/admin/products/products";
    }

}
