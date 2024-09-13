document.addEventListener('DOMContentLoaded', function () {

	datepicker("startDate", "endDate");
	
	
	$('#searchBtn').on('click',function() {
		var startDate = $('startDate').val();
		var endDate = $('endDate').val();
		
		if (!startDate || !endDate) {
			getErrorModal('시작일 또는 종료일을 선택해주세요.');
			return;
		}
		
		$.ajax({
			url: '/user/mypage/search-reviews',
			method: 'GET',
			data: {
				startDate: startDate,
				endDate: endDate
			},
			success: function(data){
				if(data.length === 0) {
					$('#result-wrap').show();
					$('#book-list').hide();
				} else {
					$('#result-wrap').hide();
					$('#book-list').empty().show();
					$.each(data, function(index, book) {
						var imageUrl = "/images/books/" + book.book_isbn + ".jpg";
						
						var purchaseDateFormatted = formatDate(book.order_purchase_date);
						
		                var bookHtml = 
						   `<div class="book-info">
		                       <img src="${imageUrl}" alt="책 이미지">
		                       <div class="book-details">
		                           <span>${book.book_name}</span>
		                           <span>저자: ${book.book_author}</span>
		                           <span>구매일: ${purchaseDateFormatted}</span>
		                       </div>
							   <button class="default-btn border size-up review-btn" data-isbn="${book.book_isbn}">리뷰작성</button>
		                   </div>`;
	                   $('#book-list').append(bookHtml);
                   });
				   $('.review-btn').on('click', function() {
                      var isbn = $(this).data('isbn');
                      window.location.href = `/user/mypage/write-review?isbn=${isbn}`;
                   });
				}
			},
			error: function() {
				getErrorModal('검색 중 오류가 발생했습니다.');
				return;
			}
		});
	});
	
	$('.tab-btn').on('click', function() {
    	// 모든 버튼에서 active 클래스를 제거한 후
    	$('.tab-btn').removeClass('active');
    	// 클릭된 버튼에만 active 클래스를 추가
    	$(this).addClass('active');
	});
	
	
	function formatDate(timestamp) {
	    // timestamp를 Date 객체로 변환
	    var date = new Date(timestamp);

	    // 연도, 월, 일 추출
	    var year = date.getFullYear();
	    var month = (date.getMonth() + 1).toString().padStart(2, '0');  // 월은 0부터 시작하므로 +1
	    var day = date.getDate().toString().padStart(2, '0');  // 1일 -> 01로 변환

	    // 'YYYY-MM-DD' 형식으로 반환
	    return `${year}-${month}-${day}`;
	}	
});