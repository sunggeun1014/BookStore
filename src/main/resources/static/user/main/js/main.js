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

	document.querySelectorAll('.add-cart-button').forEach(button => {
		button.addEventListener('click', function() {
			const isbn = this.getAttribute('data-isbn');
			addToCart(isbn, 1);
		});
	});

});

function redirectToDetailPage(isbn) {
	window.location.href = './products/detail?book_isbn=' + encodeURIComponent(isbn);
}

function addToCart(isbn, quantity) {
	const cartItem = { bookIsbn: isbn, quantity: quantity };

	$.ajax({
		url: '/user/cart/add',
		type: 'POST',
		contentType: 'application/json',
		data: JSON.stringify(cartItem),
		success: function(response) {
			getCheckModal('장바구니에 담겼습니다.');
			if (confirm("장바구니로 이동하시겠습니까?")) {
				location.href = 'user/cart/list';
			}
		},
		error: function(xhr, status, error) {
			getCheckModal('장바구니 추가에 실패했습니다.');
		}
	});

}