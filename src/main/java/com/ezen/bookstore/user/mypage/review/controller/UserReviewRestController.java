package com.ezen.bookstore.user.mypage.review.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ezen.bookstore.user.mypage.review.dto.UserBookReviewDTO;
import com.ezen.bookstore.user.mypage.review.service.UserReviewService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/user/mypage/my-reviews-page")
public class UserReviewRestController {
	
	UserReviewService userReviewService;
	
	 // 리뷰를 기다리는 도서 목록
    @GetMapping("/pending-reviews")
    public ResponseEntity<Map<String, Object>> getPendingReviews(@ModelAttribute UserBookReviewDTO userBookReviewDTO,
														             @RequestParam(value = "page", defaultValue = "1") int page,
														             @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
    	 Map<String, Object> pendingReviews = userReviewService.getPendingReviews(userBookReviewDTO, page, pageSize);
        return ResponseEntity.ok(pendingReviews);
    }

    // 작성한 리뷰 목록
    @GetMapping("/written-reviews")
    public ResponseEntity<Map<String, Object>> getWrittenReviews(@ModelAttribute UserBookReviewDTO userBookReviewDTO,
														             @RequestParam(value = "page", defaultValue = "1") int page,
														             @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
    	Map<String, Object> writtenReviews = userReviewService.getWrittenReviews(userBookReviewDTO, page, pageSize);
        return ResponseEntity.ok(writtenReviews);
    }

    @GetMapping("/edit-review/{reviewNum}")
    public ResponseEntity<UserBookReviewDTO> getReview(@PathVariable Integer reviewNum) {
        UserBookReviewDTO review = userReviewService.getReviewByReviewNum(reviewNum);
        return ResponseEntity.ok(review);
    }

    @PostMapping("/update-review/{reviewNum}")
    public ResponseEntity<String> updateReview(@PathVariable("reviewNum") Integer reviewNum,
    										   @RequestBody UserBookReviewDTO userBookReviewDTO) {
        userReviewService.updateReview(reviewNum, userBookReviewDTO);
        return ResponseEntity.ok("리뷰가 성공적으로 수정되었습니다.");
    }

    @DeleteMapping("/delete-review/{reviewNum}")
    public ResponseEntity<String> deleteReview(@PathVariable Integer reviewNum) {
        userReviewService.deleteReview(reviewNum);
        return ResponseEntity.ok("리뷰가 성공적으로 삭제되었습니다.");
    }
	
}
