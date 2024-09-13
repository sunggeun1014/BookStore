document.addEventListener('DOMContentLoaded', function() {

	
	// 배너 슬라이드 swiper
	new Swiper('.swiper', {
		loop: true, // 무한 루프 설정
		slidesPerView: 'auto',
		centeredSlides: true,
		pagination: {
			el: '.swiper-pagination',
			clickable: true, // 페이지네이션 클릭 가능
		},
		/* 
		autoplay: {
			delay: 3000, // 자동 재생 지연 시간 (밀리초 단위)
		},
		*/
		effect: 'slide', // 효과를 슬라이드로 설정 (기본값)
	});

	// 베스트셀러 헤더 월 변경되게
	const now = new Date();
	const month = now.getMonth() + 1;
	const titleElement = document.getElementById('best-seller-month');
	titleElement.textContent = titleElement.textContent.replace('월의 베스트셀러', `${month}월의 베스트셀러`);

});

function redirectToDetailPage(isbn) {
    window.location.href = './products/detail?book_isbn=' + encodeURIComponent(isbn);
}
