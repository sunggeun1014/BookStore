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

	fetchBooks('/user/main/best-books/json', 'best-books-wrapper');
	fetchBooks('/user/main/new-books/json', 'new-books-wrapper');
	fetchBooks('/user/main/top-rated-books/json', 'top-rated-books-wrapper');
	fetchBooks('/user/main/recommend-books/json', 'recommend-books-wrapper');
});

// 책 데이터 가져오기 및 표시
function fetchBooks(url, wrapperId) {
	fetch(url)
		.then(response => response.json())
		.then(data => {
			const books = data.data; // 서버에서 반환한 책 데이터
			const booksWrapper = document.getElementById(wrapperId);

			booksWrapper.innerHTML = ''; // 기존 내용 지우기

			books.forEach(book => {
				// 책 정보를 HTML로 생성
				const bookElement = createBookElement(book);
				// 생성된 책 요소를 페이지에 추가
				booksWrapper.appendChild(bookElement);
			});
		})
}

// 책 요소 생성
function createBookElement(book) {
	const bookElement = document.createElement('div');
	bookElement.classList.add('section-content-books-wrapper');
	bookElement.innerHTML = `
	    <div class="section-content-book">
	        <div class="thumbnail-wrapper">
	            <img alt="썸네일" src="/user/main/img/books/${book.book_isbn}.jpg" class="book-thumbnail">
	            <div class="book-interactive">
	                <div class="book-interactive-info" onclick="location.href=''">
	                    ${book.book_intro}
	                </div>
	                <div class="book-interactive-cart">
	                    <button onclick="location.href=''" class="add-cart-button">장바구니</a>
	                </div>
	            </div>
	        </div>
	        <div class="book-info" onclick="location.href=''">
	            <h3 class="book-title">${book.book_name}</h3>
	            <p class="book-author">${book.book_author}</p>
	        </div>
	    </div>
    `;
	return bookElement;
}
