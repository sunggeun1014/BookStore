package com.ezen.bookstore.admin.products.service;

import com.ezen.bookstore.admin.commons.SearchCondition;
import com.ezen.bookstore.admin.products.dto.CategoryDTO;
import com.ezen.bookstore.admin.products.dto.InventoryDTO;
import com.ezen.bookstore.admin.products.dto.ProductsDTO;
import com.ezen.bookstore.admin.products.repository.ProductsRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductsService {

    private static final Logger log = LoggerFactory.getLogger(ProductsService.class);
    private final ProductsRepository productRepository;

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

    public ProductsDTO detailList(String bookISBN){
        return productRepository.getBookDetail(bookISBN);
    }

    public List<CategoryDTO> categoryList() {
        return productRepository.getCategory();
    }

    public List<InventoryDTO> inventoryList() {
        return  productRepository.getInventory();
    }

    public InventoryDTO inventoryISBN (String invISBN) {
        return productRepository.getInvISBN(invISBN);
    }

    public String deleteState(String bookISBN) {
        return productRepository.deleteState(bookISBN);
    }

    public boolean existsIsbn (String bookISBN) {
        return productRepository.existsByIsbn(bookISBN);
    }

    public void deleteBook(String bookISBN) {
        productRepository.deleteBook(bookISBN);
    }

    public void insertBook(ProductsDTO productsDTO) throws IOException {
        MultipartFile thumbnailImg = productsDTO.getThumbnail_img_file();
//        ProductsDTO DTO = productRepository.getBookDetail(productsDTO.getBook_isbn());

        // 재고정보에서 isbn 가져오기
        InventoryDTO inventoryDTO = productRepository.getInvISBN(productsDTO.getBook_isbn());

        if (inventoryDTO == null) {
            throw new IllegalArgumentException("해당 ISBN에 대한 재고정보가 없습니다.");
        }

        String selectedISBN = inventoryDTO.getInv_isbn();


        if (!thumbnailImg.isEmpty()) {
            try {
                // 원본 파일 이름과 확장자 추출
                String originalFilename = thumbnailImg.getOriginalFilename();
                String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));

                // isbn 으로 파일 이름 설정 (등록할 때 재고 목록에서 가져오는 데이터로 등록해서 인벤토리 isbn이용)
                String newFileName = selectedISBN + "." + fileExtension;

                // 프로젝트의 정적 리소스 디렉토리에 이미지 저장 경로 설정
                String projectDir = System.getProperty("user.dir");  // 프로젝트의 현재 작업 디렉토리 경로
                String uploadDir = projectDir + "/src/main/resources/static/admin/common/img/products/"; // 파일 저장 폴더 경로
                Path uploadPath = Paths.get(uploadDir, newFileName);

                // 폴더가 없으면 생성
                File uploadDirFile = new File(uploadDir);
                if (!uploadDirFile.exists()) {
                    uploadDirFile.mkdirs();
                }

                // 파일을 지정된 경로에 저장
                thumbnailImg.transferTo(uploadPath.toFile());

                // DTO에 파일 정보 설정
                productsDTO.setBook_thumbnail_original(originalFilename);
                productsDTO.setBook_thumbnail_changed(newFileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 기본값이 설정된 후 데이터베이스에 삽입
        productRepository.insertBook(productsDTO);

    }

    public void updateBookInfo(ProductsDTO productsDTO) {
        MultipartFile thumbnailImg = productsDTO.getThumbnail_img_file();

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
    String rootPath = "D:/spring_upload_files/";
    private void saveFile(ProductsDTO productsDTO, MultipartFile thumbnailImageFile) throws IllegalStateException, IOException {
        if (thumbnailImageFile == null || thumbnailImageFile.isEmpty()) {
            return;
        }

        InventoryDTO inventoryDTO = new InventoryDTO();
        String storedFileName = inventoryDTO.getInv_isbn();
        String savePath = rootPath + storedFileName;

        thumbnailImageFile.transferTo(new File(savePath));

        // 2. 서버 컴퓨터에 파일 쓰기를 성공했다면 DB에 경로들을 보관
        productsDTO.setBook_thumbnail(storedFileName);
    }

}