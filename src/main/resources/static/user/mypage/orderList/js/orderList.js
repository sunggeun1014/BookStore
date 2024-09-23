$(document).ready(function() {
	datepicker('startDate', 'endDate');

	getStatusCounts();
	getOrderList();

	// 시작 날짜 선택 시 종료 날짜 초기화
	$('#startDate').on('change', function() {
		$('#endDate').val('');
		flatpickr("#endDate").clear();
	});

	$('#endDate').on('change', function() {
		if (!validateDates()) {
			$('#endDate').val('');
			flatpickr("#endDate").clear();
		}
	});

	$('#searchBtn').on('click', function() {
		if (!validateDates()) {
			return;
		}
		filterOrders();
	});

	// '주문내역' 클릭 이벤트: 초기화
	$('.box-title').on('click', function() {
		$('#startDate').val('');
		$('#endDate').val('');
		flatpickr("#startDate").clear();
		flatpickr("#endDate").clear();
		$('.history-val').removeClass('selected');

		$('.order-history-table-list').show();
		$('.result-wrap').remove();

		$('.order-history-table-list').css('border-bottom', ''); // 모든 리스트의 border-bottom 초기화
	});

	// 각 주문 상태 클릭 이벤트
	$('.history-val').on('click', function() {
		$('.history-val').removeClass('selected');
		$(this).addClass('selected');

		$('#startDate').val('');
		$('#endDate').val('');
		flatpickr("#startDate").clear();
		flatpickr("#endDate").clear();

		$('#searchBtn').trigger('click');
	});

	function validateDates() {
		var startDate = $('#startDate').val();
		var endDate = $('#endDate').val();

		if (startDate && endDate) {
			var start = new Date(startDate);
			var end = new Date(endDate);
			return end >= start;
		}
		return true;
	}

	function filterOrders() {
		var startDate = $('#startDate').val();
		var endDate = $('#endDate').val();
		var selectedStatus = $('.history-val.selected').next('.history-status').text(); // 선택된 상태 텍스트

		console.log("시작 날짜:", startDate);
		console.log("종료 날짜:", endDate);

		$('.order-history-table-list').hide();
		var hasOrders = false;
		var lastShown = null; // 마지막으로 보여진 항목

		$('.order-history-table-list').each(function() {
			var orderDateText = $(this).find('.order-date span').text();
			var orderDate = new Date(orderDateText);
			var orderStatus = $(this).find('.order-status span').text();
			var deliveryStatus = $(this).find('.delivery-status span').text();

			if (startDate) {
				var start = new Date(startDate);
				start.setHours(0, 0, 0, 0);
				if (orderDate < start) {
					return;
				}
			}

			if (endDate) {
				var end = new Date(endDate);
				end.setHours(23, 59, 59, 999);
				if (orderDate > end) {
					return;
				}
			}

			if (selectedStatus && !(orderStatus === selectedStatus || deliveryStatus === selectedStatus)) {
				return;
			}

			$(this).show();
			hasOrders = true;
			lastShown = $(this);
		});


		// 모든 항목의 border-bottom을 초기화
		$('.order-history-table-list').css('border-bottom', '');

		if (lastShown) {
			lastShown.css('border-bottom', 'none');
		}


		// 주문내역이 없을 경우 메시지 표시
		$('.result-wrap').remove();
		if (!hasOrders) {
			drawNoResultDefault('.order-history-table', '주문 내역이 존재하지 않습니다.');
		}
	}
});

// 주문/배송상태 카운트
function getStatusCounts() {
	$.ajax({
		url: '/user/mypage/statusCounts',
		method: 'GET',
		success: function(data) {
			$('#cancellation-count').text(data['취소요청'] + data['취소완료']);
			$('#exchange-return-count').text(data['반품요청'] + data['반품완료'] + data['교환완료']);
			$('#delivered-count').text(data['배송완료']);
			$('#in-delivery-count').text(data['배송중']);
			$('#before-delivery-count').text(data['배송전']);
		},
		error: function() {
			console.error('상태 카운트를 가져오는 데 실패했습니다.');
		}
	});
}

function getOrderList() {
	$.ajax({
		url: '/user/mypage/orderListData',
		method: 'GET',
		success: function(orderList) {
			$('#orderListContainer').empty();

			orderList.forEach(function(item) {
				const orderDate = new Date(item.order_purchase_date);
				const formattedDate = `${orderDate.getFullYear()}.${('0' + (orderDate.getMonth() + 1)).slice(-2)}.${('0' + orderDate.getDate()).slice(-2)}`;
				const orderNum = item.order_num;
				const bookThumbnail = item.book_thumbnail_changed || 'noimg.png';
				const orderPrice = item.order_price_total.toLocaleString() + '원';
				const orderStatus = item.order_detail_status;
				const deliveryStatus = item.order_delivery_status;

				// HTML 요소 생성
				const orderItem = `
				<div class="order-history-table-list order-list" >
                    <div class="history-table-item order-date">
                        <span>${formattedDate}</span>
                    </div>
                    <div class="history-table-item order-num">
                        <a href="/user/mypage/orderDetail?orderNum=${orderNum}">${orderNum}</a>
                    </div>
                    <a href="/user/mypage/orderDetail?orderNum=${orderNum}" class="history-table-item order-content">
                        <span class="img-wrap">
                            <img class="book-thumbnail" src="/images/books/${bookThumbnail}" alt="책 이미지">
                        </span>
                        <span class="book-info">
                            <span class="book-name">${item.book_name}</span>
                            ${item.order_qty_total > 1 ? `<span class="purchase-qty">외 ${item.order_qty_total - 1}개</span>` : ''}
                        </span>
                    </a>
                    <div class="history-table-item order-price">
                        <span class="total-price">${orderPrice}</span>
                    </div>
                    <div class="history-table-item order-status">
                        <span class="order_detail_status">${orderStatus}</span>
                    </div>
                    <div class="history-table-item delivery-status">
                        <span class="order_delivery_status">${deliveryStatus}</span>
                        ${deliveryStatus === '배송전' ? '<div class="history-table-item request-button"><button class="default-btn border return-cancel-btn">취소</button></div>' : ''}
                    </div>
                </div>
                `;

				$('#orderListContainer').append(orderItem);
			});
		},
		error: function() {
			console.error('주문 목록을 가져오는 데 실패했습니다.');
		}
	});
}


function drawNoResultDefault(parentEl, msg) {
	const parentElement = document.querySelector(`${parentEl}`);

	const resultWrap = document.createElement("div");
	const p = document.createElement("p");
	const titleText = document.createElement("span");

	resultWrap.classList.add("result-wrap");

	p.textContent = "!";
	titleText.textContent = `${msg}`;

	resultWrap.appendChild(p);
	resultWrap.appendChild(titleText);

	parentElement.appendChild(resultWrap);
}
