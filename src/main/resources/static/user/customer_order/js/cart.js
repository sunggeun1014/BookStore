document.addEventListener("DOMContentLoaded", function() {
	const cartItems = document.querySelectorAll('.cart-item');
	const totalProductsPriceElement = document.querySelector('.total-products-price');
	const totalItemsElement = document.querySelector('.total-qty');
	const expectedPaymentAmountElement = document.querySelector('.expected-payment-amount');

	const selectAllCheckbox = document.getElementById('select-all');
	const itemCheckboxes = document.querySelectorAll('.item-checkbox');
	const deleteButton = document.querySelector('.delete-product-btn');


	// 전체 선택 체크박스 상태 업데이트
	function updateSelectAllCheckbox() {
		const allChecked = Array.from(itemCheckboxes).every(checkbox => checkbox.checked);
		const anyChecked = Array.from(itemCheckboxes).some(checkbox => checkbox.checked);
		selectAllCheckbox.checked = allChecked;
		selectAllCheckbox.indeterminate = !allChecked && anyChecked;
	}

	// 총합 업데이트(가격, 갯수)
	function updateTotal() {
		let total = 0;
		let totalItems = 0;

		cartItems.forEach(item => {
			const inputQty = item.querySelector(".input-qty");
			const totalPriceElement = item.querySelector(".total-price");
			const price = parseInt(totalPriceElement.getAttribute("data-price")) || 0;
			const qty = parseInt(inputQty.value) || 1;

			total += price * qty;
			totalItems += qty;
		});

		if (totalProductsPriceElement) {
			totalProductsPriceElement.innerText = total.toLocaleString();
		}

		if (totalItemsElement) {
			totalItemsElement.innerText = totalItems;
		}

		if (expectedPaymentAmountElement) {
			expectedPaymentAmountElement.innerText = total.toLocaleString();
		}
	}

	function calcQty(item) {
		const inputQty = item.querySelector(".input-qty");
		const minus = item.querySelector(".fa-minus");
		const plus = item.querySelector(".fa-plus");
		const totalPriceElement = item.querySelector(".total-price");

		const price = parseInt(totalPriceElement.getAttribute("data-price")) || 0;

		let qty = parseInt(inputQty.value) || 1;

		function updatePrice() {
			totalPriceElement.innerText = (price * qty).toLocaleString();
			updateTotal();
		}

		updatePrice();

		minus.addEventListener("click", () => {
			if (qty > 1) {
				qty--;
				inputQty.value = qty;
				updatePrice();
			}
		});

		plus.addEventListener("click", () => {
			qty++;
			inputQty.value = qty;
			updatePrice();
		});

		function handleQtyChange() {
			let newQty = parseInt(inputQty.value);
			if (isNaN(newQty) || newQty < 1) {
				newQty = 1;
			}
			qty = newQty;
			inputQty.value = qty;
			updatePrice();
		}

		inputQty.addEventListener("change", handleQtyChange);
		inputQty.addEventListener("input", handleQtyChange);
	}

	// 배송예정일 업데이트 (오늘 날짜 이틀 뒤)
	function updateDeliveryDates() {
		const today = new Date();
		const deliveryDate = new Date(today.setDate(today.getDate() + 2));
		const formattedDate = formatDate(deliveryDate);

		document.querySelectorAll('.delivery-date-text').forEach(el => {
			el.textContent = formattedDate;
		});
	}

	function formatDate(date) {
		const daysOfWeek = ['일', '월', '화', '수', '목', '금', '토'];
		const day = String(date.getDate()).padStart(2, '0');
		const month = String(date.getMonth() + 1).padStart(2, '0');
		const dayOfWeek = daysOfWeek[date.getDay()];
		return `${month}/${day} (${dayOfWeek})`;
	}


	updateDeliveryDates();

	cartItems.forEach(item => {
		calcQty(item);
	});

	updateTotal();


	// 전체 선택 체크박스 클릭 시 처리
	selectAllCheckbox.addEventListener('change', function() {
		itemCheckboxes.forEach(checkbox => {
			checkbox.checked = selectAllCheckbox.checked;
		});
		updateSelectAllCheckbox();
	});

	// 개별 체크박스 클릭 시 처리
	itemCheckboxes.forEach(checkbox => {
		checkbox.addEventListener('change', updateSelectAllCheckbox);
	});


	// 삭제 버튼 클릭 시 처리
	deleteButton.addEventListener('click', function() {
		const selectedItems = document.querySelectorAll('.item-checkbox:checked');
		const cartNums = Array.from(selectedItems).map(checkbox => parseInt(checkbox.getAttribute('data-cart-num')));

		if (cartNums.length > 0) {
			const requestData = { cartNums: cartNums };

			$.ajax({
				url: '/user/cart/delete',
				method: 'POST',
				contentType: 'application/json',
				data: JSON.stringify(requestData),
				xhrFields: {
					withCredentials: true // 쿠키를 포함시키기 위한 설정
				},
				success: function(response) {
					if (response.success) {
						location.reload(); // 페이지 새로고침
					} else {
						getCheckModal('삭제에 실패했습니다.');
					}
				},
				error: function(jqXHR, textStatus, errorThrown) {
					getErrorModal();
				}
			});
		} else {
			getCheckModal('삭제할 상품을 선택해주세요.');
		}
	});
});
