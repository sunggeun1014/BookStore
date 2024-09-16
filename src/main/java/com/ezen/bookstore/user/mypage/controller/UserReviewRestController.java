package com.ezen.bookstore.user.mypage.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ezen.bookstore.user.mypage.dto.UserBookReviewDTO;
import com.ezen.bookstore.user.mypage.service.UserReviewService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/user/mypage/my-reviews-page")
public class UserReviewRestController {
	
	UserReviewService userReviewService;
	
    @GetMapping("/pending-reviews")
    public ResponseEntity<List<UserBookReviewDTO>> getPendingReviews(@ModelAttribute UserBookReviewDTO userBookReviewDTO) {
        List<UserBookReviewDTO> pendingReviews = userReviewService.getPendingReviews(userBookReviewDTO);
        return ResponseEntity.ok(pendingReviews);
    }

    @GetMapping("/written-reviews")
    public ResponseEntity<List<UserBookReviewDTO>> getWrittenReviews(@ModelAttribute UserBookReviewDTO userBookReviewDTO) {
        List<UserBookReviewDTO> writtenReviews = userReviewService.getWrittenReviews(userBookReviewDTO);
        return ResponseEntity.ok(writtenReviews);
    }

    @GetMapping("/edit-review/{reviewNum}")
    public ResponseEntity<UserBookReviewDTO> getReview(@PathVariable Long reviewNum) {
        UserBookReviewDTO review = userReviewService.getReviewByReviewNum(reviewNum);
        return ResponseEntity.ok(review);
    }

    @PostMapping("/update-review/{reviewNum}")
    public ResponseEntity<String> updateReview(@PathVariable("reviewNum") Long reviewNum,
    										   @RequestBody UserBookReviewDTO userBookReviewDTO) {
        userReviewService.updateReview(reviewNum, userBookReviewDTO);
        return ResponseEntity.ok("리뷰가 성공적으로 수정되었습니다.");
    }

    @DeleteMapping("/delete-review/{reviewNum}")
    public ResponseEntity<String> deleteReview(@PathVariable Long reviewNum) {
        userReviewService.deleteReview(reviewNum);
        return ResponseEntity.ok("리뷰가 성공적으로 삭제되었습니다.");
    }
	
}
