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
			const bookIsbn = this.getAttribute('data-isbn');
			addToCart(bookIsbn, 1);
		});
	});

});

function redirectToDetailPage(isbn) {
	window.location.href = './products/detail?book_isbn=' + encodeURIComponent(isbn);
}

function addToCart(bookIsbn, qty) {
	const cartItem = { book_isbn: bookIsbn, cart_purchase_qty: qty };

	$.ajax({
		url: '/user/cart/add',
		type: 'POST',
		contentType: 'application/json',
		data: JSON.stringify(cartItem),
		success: function(response) {
			if (response.message === '장바구니에 이미 있는 상품입니다.') {
			    getConfirmModal('장바구니에 이미 있는 상품입니다. 장바구니로 이동하시겠습니까?', function() {
			        window.location.href = '/user/cart/list';
			    });
			} else {
				getConfirmModal('장바구니에 담겼습니다. 장바구니로 이동하시겠습니까?', function() {
					window.location.href = '/user/cart/list';
				});
			}
		},
		error: function() {
			getCheckModal('장바구니 추가에 실패했습니다.');
		}
	});
}