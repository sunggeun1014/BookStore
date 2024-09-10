document.addEventListener('DOMContentLoaded', function () {
    // 요소 선택
    const allCategoriesButtons = document.querySelectorAll(".nav-all-categories");
    const modal = document.getElementById('categoryModal');
    const modalCategoryItems = document.querySelectorAll('.modal-category-item');
    const modalSelectedIcons = document.querySelectorAll('.modal-category-icon');
    const korCategories = document.getElementById('modal-categories-kor');
    const foreignCategories = document.getElementById('modal-categories-foreign');
    const headerFixed = document.querySelector('.header-fixed');
    const fixedPosition = 120; // 모달을 고정할 스크롤 위치

    // 스크롤 위치에 따라 헤더 고정 여부 결정 및 모달 고정
    function handleScroll() {
        if (window.scrollY > 120) {
            headerFixed.classList.add('visible');
        } else {
            headerFixed.classList.remove('visible');
        }

        // 모달 고정 처리
        if (window.scrollY > fixedPosition) {
            modal.classList.add('fixed-modal');
        } else {
            modal.classList.remove('fixed-modal');
        }
    }

    handleScroll(); // 초기 로딩 시 호출
    window.addEventListener('scroll', handleScroll); // 스크롤 이벤트 리스너 추가

    // 모달 열기 또는 닫기
    function toggleModal() {
        const isVisible = modal.classList.contains('visible');
        
        if (isVisible) {
            closeModal(); // 모달이 열려 있는 상태면 닫기
        } else {
            openModal(); // 모달이 닫혀 있는 상태면 열기
        }
    }

    // 카테고리 모달 열기
    function openModal() {
        allCategoriesButtons.forEach(button => {
            const icon = button.querySelector('.all-categories-icon');
            if (icon) {
                icon.src = '/user/common/imgs/category-list-button-open.svg'; // 아이콘 변경
            }
        });
        modal.classList.add('visible'); // 모달 표시
        resetModal(); // 모달이 열릴 때 기본 상태로 초기화
    }

    // 카테고리 모달 닫기
    function closeModal() {
        modal.classList.remove('visible'); // 모달 숨기기
        allCategoriesButtons.forEach(button => {
            const icon = button.querySelector('.all-categories-icon');
            if (icon) {
                icon.src = '/user/common/imgs/category-list-button.svg'; // 아이콘 원래대로
            }
        });
    }

    // 모든 버튼에 클릭 이벤트 리스너 추가
    allCategoriesButtons.forEach(button => {
        button.addEventListener('click', toggleModal);
    });

    // 모달 밖을 클릭하면 모달 닫기
    window.addEventListener('click', function (event) {
        if (event.target === modal) { // 모달 외부 클릭
            closeModal(); // 모달 닫기
            resetModal(); // 모달 초기화
        }
    });

    // 모달 초기화
    function resetModal() {
        modalCategoryItems.forEach(item => item.classList.remove('active')); // 모든 카테고리 아이템 비활성화
        modalSelectedIcons.forEach(icon => icon.classList.remove('active')); // 모든 아이콘 비활성화

        // 기본 카테고리 활성화 상태로 설정
        korCategories.classList.add('active');
        foreignCategories.classList.remove('active');

        // 기본 카테고리 활성화 상태로 설정
        const defaultItem = Array.from(modalCategoryItems).find(item => item.textContent.includes('국내도서'));
        if (defaultItem) {
            defaultItem.classList.add('active'); // 기본 항목 활성화
            const correspondingIcon = defaultItem.nextElementSibling; // 아이콘은 다음 형제 요소
            if (correspondingIcon && correspondingIcon.classList.contains('modal-category-icon')) {
                correspondingIcon.classList.add('active'); // 아이콘 활성화
            }
        }
    }

    // 카테고리 클릭 시 처리할 함수
    function handleCategoryClick(event) {
        event.preventDefault(); // 링크 클릭 시 페이지 새로고침 방지

        // 초기화
        modalCategoryItems.forEach(item => item.classList.remove('active')); // 모든 항목 비활성화
        modalSelectedIcons.forEach(icon => icon.classList.remove('active')); // 모든 아이콘 비활성화
        korCategories.classList.remove('active'); // 한국어 카테고리 비활성화
        foreignCategories.classList.remove('active'); // 외국어 카테고리 비활성화

        // 클릭된 항목에 active 클래스 추가
        const clickedItem = event.currentTarget;
        clickedItem.classList.add('active');

        // 해당 항목의 아이콘도 active 클래스 추가
        const clickedItemsIcon = clickedItem.nextElementSibling; // 아이콘은 다음 형제 요소
        if (clickedItemsIcon && clickedItemsIcon.classList.contains('modal-category-icon')) {
            clickedItemsIcon.classList.add('active');
        }

        // 카테고리별 내용 표시
        if (clickedItem.textContent.includes('국내도서')) {
            korCategories.classList.add('active');
        } else if (clickedItem.textContent.includes('외국도서')) {
            foreignCategories.classList.add('active');
        }
    }

    // 카테고리 항목 클릭 이벤트 리스너 추가
    modalCategoryItems.forEach(item => {
        item.addEventListener('click', handleCategoryClick);
    });
	
	$.ajax({
		url: "/user/members/getBasketCount",
		method: "GET",
		success: function(response) {
			$(".cart-qty p").text(response);
		},
		error: function () {
			getErrorModal();
		}
	});
});