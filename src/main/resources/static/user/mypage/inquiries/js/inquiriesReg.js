$(document).ready(function () {
	const maxByteLength = 500;

	
	function getByteLength(str) {
		let byteLength = 0;
		for (let i = 0; i < str.length; i++) {
			let charCode = str.charCodeAt(i);
			if (charCode <= 0x007F) {
				byteLength += 1;
			} else if (charCode <= 0x07FF) {
				byteLength += 2;
			} else {
				byteLength += 3;
			}
		}
		return byteLength;
	}
	
	
	$("#inquiryContent").on("input", function (e) {
		let textarea = $(this);
		let content = textarea.val();
		let byteLength = getByteLength(content);

		if (byteLength > maxByteLength) {
			while (getByteLength(content) > maxByteLength) {
				content = content.substring(0, content.length - 1);
			}
			textarea.val(content);
		}

		$("#charCount").text(getByteLength(textarea.val()));
	});
	
	$("form").on("submit", function(e){
		let title = $("#inquiryTitle").val();
		let content = $("#inquiryContent").val();
		
		if (!title) {
			getErrorModal("제목을 입력해주세요");
			$("#inquiryTitle").focus(); 
			e.preventDefault();
			return;
		}
		
		if (!content) {
			getErrorModal("문의 내용을 입력해주세요");
			$("#inquiryContent").focus(); 
			e.preventDefault();
			return;
		}
	});
	
});


function openOrderProduct() {
	$.ajax({
		url: '/user/mypage/inquiries-page/search-order',
		method: 'POST',
		contentType: 'application/json',
		data: JSON.stringify({ startDate: null, endDate: null }),
		success: function (response) {
			const orderList = response; 
			createOrderModal(orderList);
		},
		error: function () {
			getErrorModal("주문 데이터를 불러오는 중 오류가 발생했습니다.");
			return;
		}
	});
}


function createOrderModal(orderList) {
	
	let divArea = $("<div id='myModal' class='modal' style='display: block;'></div>");
	let contentArea = $("<div class='inquiry-modal-content'></div>");
	let headArea = $("<div></div>");
	let headButtonArea = $(`
		<div class='head-button-area'>
			<button class='custom-clicked-btn' data-period='1month'>최근 1개월</button>
			<button class='custom-btn' data-period='2months'>최근 2개월</button>
			<button class='custom-btn' data-period='3months'>최근 3개월</button>
			<button class='custom-btn' data-period='all'>전체</button>
		</div>
	`);
	let messageArea = $("<div class='inquiry-modal-text'><h3>주문상품 선택</h3><button id='cancel-btn' class='cancel-btn-img'></button></div>");
	let modalFotter = $("<div class='custom-modal-footer'></div>");
	let btnArea = $("<button id='confirm-select' class='custom-modal-btn confirm'>선택완료</button>");

	
	let orderArea = $("<div class='order-list'></div>");

	
	function populateOrderList(orderList) {
	    orderArea.empty(); 

	    orderList.forEach(order => {
	        let formattedDate = formatDate(order.order_purchase_date);
	        const books = order.books || []; 
	        let hasMultipleBooks = books.length > 1;

	        
			let orderItem = $(`
			    <div class='order-item'>
			        <div class="flex-center">
			            <p class="sub-title-font">${formattedDate}</p>
			            <span class="btn-spacebetween">|</span>
			            <p>주문번호 ${order.order_num}</p>
			        </div>
			        <div class="flex-center between" style="margin-bottom: 10px;">
			            <div class="flex-center" style="gap: 5px;">
			                <input type='radio' name='order' value='${order.order_num}' 
			                    data-order-detail-num='${books.length > 0 ? books[0].order_detail_num : ''}' 
			                    data-qty='${books.length > 0 ? books[0].order_detail_qty - books[0].order_request_qty : ''}' 
			                    onclick="handleRadioClick('${order.order_num}')"/>
			                <label>${books.length > 0 ? books[0].book_name : '책 정보 없음'} ${hasMultipleBooks ? `외 ${books.length - 1}건` : ''}</label>
			            </div>
			            ${hasMultipleBooks ? `<i class="icon-arrow-off" id="order-arrow-${order.order_num}" onclick="toggleOrderDetails(this, '${order.order_num}')"></i>` : ''}
			        </div>
			        ${hasMultipleBooks ? `
			            <div class="order-details" id="order-details-${order.order_num}" style="display: none;">
			                ${books.map((book, index) => `
			                    <div class='flex-center' style="gap: 5px; margin: 5px; 10px 0px 10px 0px">
			                        <input type='checkbox' class="check-box" id='book-${order.order_num}-${index}' 
			                            data-order-num='${order.order_num}' 
			                            data-order-detail-num='${book.order_detail_num}' 
			                            data-qty='${book.order_detail_qty - book.order_request_qty}' 
			                            onclick="handleCheckboxClick('${order.order_num}', this)"/>
			                        <label for='book-${order.order_num}-${index}'></label>
			                        <p>${book.book_name}</p>
			                    </div>
			                `).join('')}
			            </div>
			        ` : ''}
			    </div>
			`);

	        orderArea.append(orderItem);
	    });
	}

	populateOrderList(orderList); 

	
	headArea.append(messageArea);
	headArea.append(headButtonArea);
	contentArea.append(headArea);
	contentArea.append(orderArea);
	modalFotter.append(btnArea);
	contentArea.append(modalFotter);
	divArea.append(contentArea);

	
	$("body").append(divArea);

	
	$('.head-button-area').on('click', 'button', function () {
		let period = $(this).data('period');
		setButtonStyles(this);

		let startDate, endDate;
		let now = new Date();

		if (period === '1month') {
			let oneMonthAgo = new Date();
			oneMonthAgo.setMonth(now.getMonth() - 1); 
			startDate = formatDateForDB(oneMonthAgo, 'start');
			endDate = formatDateForDB(new Date(), 'end');
		} else if (period === '2months') {
			let twoMonthsAgo = new Date();
			twoMonthsAgo.setMonth(now.getMonth() - 2); 전
			startDate = formatDateForDB(twoMonthsAgo, 'start');
			endDate = formatDateForDB(new Date(), 'end');
		} else if (period === '3months') {
			let threeMonthsAgo = new Date();
			threeMonthsAgo.setMonth(now.getMonth() - 3); 
			startDate = formatDateForDB(threeMonthsAgo, 'start');
			endDate = formatDateForDB(new Date(), 'end');
		} else {
			startDate = "";
			endDate = "";
		}

		
		$.ajax({
			url: '/user/mypage/inquiries-page/search-order',
			method: 'POST',
			contentType: 'application/json',
			data: JSON.stringify({ startDate: startDate, endDate: endDate }),
			success: function (response) {
				populateOrderList(response); 
			},
			error: function () {
				getErrorModal("주문 데이터를 불러오는 중 오류가 발생했습니다.");
				return;
			}
		});
	});

	
	function setOrderDetails(orderNumber, orderDetailNumber, maxQty) {
	    
	    $("#order-number-display").text(orderNumber);
	    $("#order-detail-number-display").text(orderDetailNumber); 

	    $("#quantity").attr("max", maxQty);

	   
	    $("#order-number").val(orderNumber);
	    $("#order-detail-number").val(orderDetailNumber); 
	}

	
	$(".custom-modal-btn.confirm").on("click", function () {
	    let selectedOrder = $("input[name='order']:checked"); 
	    let selectedBook = $("input.check-box:checked").first(); 
		let isExchangeInquiry = inquiryTypeSelect.value === '05';
		
	    if (selectedOrder.length && !selectedBook.length) {
	        
	        let orderNumber = selectedOrder.val();
	        let orderDetailNumber = selectedOrder.data('orderDetailNum');
	        let orderQty = selectedOrder.data('qty');

			
			if (orderQty <= 0 && isExchangeInquiry) {
				getErrorModal("해당 주문은 이미 모두 교환 요청 상태입니다.다른 문의 유형을 선택해주세요");
				return;
			}
			
	        setOrderDetails(orderNumber, orderDetailNumber, orderQty);
			 
	    } else if (selectedOrder.length && selectedBook.length) {
			
	        let orderNumber = selectedBook.data('orderNum');
	        let orderDetailNumber = selectedBook.data('orderDetailNum');
	        let bookQty = selectedBook.data('qty');
			
			
			if (bookQty <= 0 && isExchangeInquiry) {
				getErrorModal("해당 주문은 이미 모두 교환 요청 상태입니다.다른 문의 유형을 선택해주세요");
				return;
			}
			
	        setOrderDetails(orderNumber, orderDetailNumber, bookQty);
	    }
		
	    divArea.remove();
		
		let quantitySection = document.getElementById('quantitySection'); 
		quantitySection.style.display = isExchangeInquiry ? 'flex' : 'none';
		$(".order-summary").css("display", "flex");
	});
	
	$("#cancel-btn").on("click", function() {
		divArea.remove();
	});
}


function changeQty(delta) {
    const quantityInput = document.getElementById('quantity');
    const currentValue = parseInt(quantityInput.value);
    const max = parseInt(quantityInput.max); 
    const min = 1; 
    
    let newValue = currentValue + delta;

    if (newValue < min) {
        newValue = min;
    }

    if (newValue > max) {
        newValue = max;
		getErrorModal("최초 주문수량을 넘길 수 없습니다")
    }

    quantityInput.value = newValue;
}



function formatDateForDB(date, type) {
    const d = new Date(date);
    const year = d.getFullYear();
    const month = String(d.getMonth() + 1).padStart(2, '0');
    const day = String(d.getDate()).padStart(2, '0');
    
    return type === 'start' ? `${year}-${month}-${day} 00:00:00` : `${year}-${month}-${day} 23:59:59`;
}



function setButtonStyles(clickedButton) {
	$('.head-button-area button').removeClass('custom-clicked-btn').addClass('custom-btn');
	$(clickedButton).removeClass('custom-btn').addClass('custom-clicked-btn');
}



function toggleOrderDetails(element, orderNum) {
    // 현재 클릭된 항목을 제외한 모든 선택을 해제
    resetSelections(orderNum);
    
    let detailsElement = document.getElementById(`order-details-${orderNum}`);
    let radioInput = document.querySelector(`input[name='order'][value='${orderNum}']`);
    
    if (detailsElement.style.display === 'none' || detailsElement.style.display === '') {
        detailsElement.style.display = 'block';
        element.classList.add('icon-arrow-on');
        element.classList.remove('icon-arrow-off');
        
        if (radioInput && !radioInput.checked) {
            radioInput.checked = true;
        }
    } else {
        detailsElement.style.display = 'none';
        element.classList.remove('icon-arrow-on');
        element.classList.add('icon-arrow-off');
        
        if (radioInput && radioInput.checked) {
            radioInput.checked = false;
        }
    }
}

function handleRadioClick(orderNum) {
    const radioInput = document.querySelector(`input[name='order'][value='${orderNum}']`);
    const arrowIcon = document.querySelector(`#order-arrow-${orderNum}`);

    if (radioInput) {
        radioInput.checked = true; 
    }

    if (arrowIcon) {
        toggleOrderDetails(arrowIcon, orderNum);
    }
}

function resetSelections(excludeOrderNum) {
    // 선택된 orderNum을 제외한 모든 세부 사항과 아이콘을 초기화
    $('input[name="order"]').each(function() {
        if (this.value != excludeOrderNum) {
            $(this).prop('checked', false);
        }
    });

    $('.check-box').prop('checked', false);

    // 선택된 세부 사항을 제외하고 나머지 숨기기
    $('.order-details').each(function() {
        const orderDetailsId = $(this).attr('id');
        if (orderDetailsId !== `order-details-${excludeOrderNum}`) {
            $(this).hide();
        }
    });

    // 선택된 아이콘을 제외하고 나머지는 초기화
    $('.icon-arrow-on').each(function() {
        const arrowId = $(this).attr('id');
        if (arrowId !== `order-arrow-${excludeOrderNum}`) {
            $(this).addClass('icon-arrow-off').removeClass('icon-arrow-on');
        }
    });
}

function handleCheckboxClick(orderNum, currentCheckbox) {
    const checkboxes = document.querySelectorAll(`input.check-box[data-order-num='${orderNum}']`);
    checkboxes.forEach(checkbox => {
        if (checkbox !== currentCheckbox) {
            checkbox.checked = false;
        }
    });

}




function toggleInquiryProduct() {
	const inquiryTypeSelect = document.getElementById('inquiryTypeSelect');
	const isTypeSelected = !!inquiryTypeSelect.value;
	
	const fields = ['inquiryTitle', 'inquiryContent', 'inquiryEmail'].map(id => document.getElementById(id));
	const sections = ['inquiryProduct', 'inquiryProductQty'].map(id => document.getElementById(id));

	fields.forEach(field => {
		field.readOnly = !isTypeSelected;
		field.style.pointerEvents = isTypeSelected ? 'auto' : 'none';
		field.style.backgroundColor = isTypeSelected ? '' : '#EBECF0';
		
		if (!isTypeSelected) {
			field.value = '';  
		}
	});

	sections.forEach(section => section.style.display = isTypeSelected ? 'block' : 'none');
	
	const imageUpload = document.getElementById('image-upload');
	const preview = document.getElementById('preview');
	imageUpload.disabled = !isTypeSelected;

	if (!isTypeSelected) {
		preview.src = '';
	}
	
}

function previewImage(event) {
    const file = event.target.files[0];

    if (file) {
        const reader = new FileReader();

        reader.onload = function(e) {
            const preview = document.getElementById('preview');
            preview.src = e.target.result;
            preview.style.display = 'block'; 
        };

        reader.readAsDataURL(file); 
    }
}

function formatDate(timestamp) {
    let date = new Date(timestamp);
    let year = date.getFullYear();
    let month = (date.getMonth() + 1).toString().padStart(2, '0');
    let day = date.getDate().toString().padStart(2, '0');
    return `${year}.${month}.${day}`;
}
