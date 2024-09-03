package com.ezen.bookstore.admin.products.service;

import com.ezen.bookstore.admin.commons.SearchCondition;
import com.ezen.bookstore.admin.products.dto.CategoryDTO;
import com.ezen.bookstore.admin.products.dto.InventoryDTO;
import com.ezen.bookstore.admin.products.dto.ProductsDTO;
import com.ezen.bookstore.admin.products.repository.ProductsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductsService {

    private final ProductsRepository productRepository;

    public List<ProductsDTO> list(){
        // DB에서 모든 주문 목록을 꺼내와야 한다
        return productRepository.getlist();
    }

    public List<ProductsDTO> getBooksByCondition(SearchCondition searchCondition) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        Date startDate = null;
        Date endDate = null;

        if (searchCondition.getStart_date() != null) {
            startDate = formatter.parse(searchCondition.getStart_date());
        }

        if (searchCondition.getEnd_date() != null) {
            endDate = formatter.parse(searchCondition.getEnd_date());
        }

        return productRepository.findBookCondition(
                searchCondition.getBook_state(),
                startDate,
                endDate,
                searchCondition.getSearch_conditions(),
                searchCondition.getWord()
        );
    }


    public List<ProductsDTO> getBookState(String bookState) {
        return productRepository.getBookState(bookState);
    }

    public ProductsDTO detailList(String bookISBN){
        return productRepository.getBookDetail(bookISBN);
    }

    public List<CategoryDTO> categoryList() {
        return productRepository.getCategory();
    }

    public List<InventoryDTO> inventoryList() {
        return  productRepository.getInventory();
    }

    public void updateBookInfo(ProductsDTO productsDTO) {
        MultipartFile thumbnailImg = productsDTO.getThumbnail_img();

        if (thumbnailImg != null && !thumbnailImg.isEmpty()) {
            try {
                String fileName = thumbnailImg.getOriginalFilename();
                Path tempPath = Files.createTempDirectory("book_thumbnails");
                Path filePath = tempPath.resolve(fileName);
                Files.copy(thumbnailImg.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                productsDTO.setBook_thumbnail(filePath.toString());
            } catch (IOException e) {
                // 로그 출력 또는 예외 처리
                e.printStackTrace();
            }

        } else {
            ProductsDTO existingProduct = productRepository.getBookDetail(productsDTO.getBook_isbn());
            productsDTO.setBook_thumbnail(existingProduct.getBook_thumbnail());
        }

        productRepository.updateBookInfo(productsDTO);
    }

    // 이미지 저장하기
    // 경로 어디..?
    // String rootPath = "";
    private void saveFile(MultipartFile thumbnailImg) {
        if (thumbnailImg != null) {
            return;
        }

        String fileName = thumbnailImg.getOriginalFilename();


    }

}