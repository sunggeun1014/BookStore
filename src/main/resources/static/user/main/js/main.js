const swiper = new Swiper('.swiper', {
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



document.addEventListener('DOMContentLoaded', function() {
	fetchNewBooks();
});

function fetchNewBooks() {
	fetch('/user/main/new-books/json')
		.then(response => response.json())
		.then(data => {
			const books = data.data; // 서버에서 반환한 책 데이터
			const booksWrapper = document.getElementById('new-books-wrapper');

			booksWrapper.innerHTML = ''; // 기존 내용을 지우기

			books.forEach(book => {
				// 책 정보를 HTML로 생성
				/* <img src="${book.book_thumbnail_changed}" alt="${book.book_name}" class="book-thumbnail" /> */
				const bookElement = document.createElement('div');
				bookElement.classList.add('section-content-book');
				bookElement.innerHTML = `
					<div class="thumbnail-wrapper">
				                <img alt="썸네일" src="/user/main/img/01.jpg" class="book-thumbnail">
				                        <div class="thumbnail-shadow"></div>
				                    </div>
                    <div class="book-info">
                        <h3 class="book-title">${book.book_name}</h3>
                        <p class="book-author">${book.book_author}</p>
                    </div>
                `;
				// 생성된 책 요소를 페이지에 추가
				booksWrapper.appendChild(bookElement);
			});
		})
		.catch(error => console.error('Error fetching new books:', error));
}

