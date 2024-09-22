$(document).ready(function () {
	const maxByteLength  = 1000;
	
	
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
	
	
	$("#inquiryContent").on("input", function () {
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
	
	
});
function openOrderProduct() {
	$.ajax({
       url: '/user/mypage/inquiries-page/search-order',
       method: 'GET',
       success: function (response) {
           const orderList = response; // 서버에서 받아온 주문 데이터
           
           // 주문 상품 선택 모달 생성
           createOrderModal(orderList);
       },
       error: function () {
           alert("주문 데이터를 불러오는 중 오류가 발생했습니다.");
       }
   });
}

function createOrderModal(orderList) {
    // 모달 생성
    let divArea = $("<div id='myModal' class='modal' style='display: block;'></div>");
    let contentArea = $("<div class='inquiry-modal-content'></div>");
	let headArea = $("<div></div>");
	let headButtonArea = $(`<div class='head-button-area'>
	                            <button>최근 1개월</button>
								<button>최근 2개월</button>
								<button>최근 3개월</button>
						  </div>`);
    let messageArea = $("<div class='inquiry-modal-text'><h3>주문상품 선택</h3></div>");
    let modalFotter = $("<div class='modal-footer'></div>");
    let btnArea = $("<button id='confirm-select' class='modal-btn confirm'>선택완료</button>"); 

    // 주문 리스트 영역
    let orderArea = $("<div class='order-list'></div>");

    // 주문 내역 리스트 생성
    orderList.forEach(order => {
		let formattedDate = formatDate(order.order_purchase_date);
		const bookNames = order.book_name || []; // 책 이름 리스트
        let hasMultipleBooks = bookNames.length > 1; // 책이 여러 권인지 확인

        // 주문 항목 생성 (책이 한 권이든 여러 권이든 radio 버튼 사용)
        let orderItem = $(`<div class='order-item'>
						       <div class="flex-center">
							       <p class="sub-title-font">${formattedDate}</p>
								   <span class="btn-spacebetween">|</span>
								   <p>주문번호 ${order.order_num}</p>
							   </div>
							   <div class="flex-center between">
							   	   <div class="flex-center" style="gap: 5px;">
			                           <input type='radio' name='order' value='${order.order_num}' data-qty='${order.order_detail_qty}' />
			                           <label>${bookNames.length > 0 ? bookNames[0] : '책 정보 없음'} ${hasMultipleBooks ? `외 ${bookNames.length - 1}건` : ''}</label>
								   </div>
								   ${hasMultipleBooks ? `<i class="icon-arrow-off" onclick="toggleOrderDetails(this, '${order.order_num}')"></i>` : ''}
							   </div>
							   ${hasMultipleBooks ? `
							   <!-- 주문 상세 정보가 표시될 영역 (초기에 숨김 처리) -->
   							   <div class="order-details" id="order-details-${order.order_num}" style="display: none;">
								   	${bookNames.map((book, index) => `
	                                  <div>
	                                      <input type='checkbox' class="check-box" id='book-${order.order_num}-${index}' data-order-num='${order.order_num}' data-qty='${order.order_detail_qty}' />
	                                      <label for='book-${order.order_num}-${index}'>책: ${book}</label>
	                                  </div>
	                                `).join('')}
   							   </div>
							   ` : ''}
                           </div>`);

        orderArea.append(orderItem);
    });

    // 모달에 요소 추가
	headArea.append(messageArea);
	headArea.append(headButtonArea);
    contentArea.append(headArea);
    contentArea.append(orderArea);
    modalFotter.append(btnArea);
    contentArea.append(modalFotter);
    divArea.append(contentArea);

    // 모달을 body에 추가
    $("body").append(divArea);

    // 선택 완료 시
    $(".modal-btn.confirm").on("click", function() {
        const selectedOrder = $("input[name='order']:checked"); // 선택된 주문

        if (selectedOrder.length) {
            const orderNumber = selectedOrder.val();
            const orderQty = selectedOrder.data('qty');

            // 주문번호를 특정 위치에 반영
            $("#order-number").text(orderNumber);

            // 선택된 주문에 따라 수량의 최대값 설정
            $("#quantity").attr("max", orderQty);

            // 모달 제거
            divArea.remove(); 
        } else {
            alert("주문을 선택해주세요.");
        }
    });
}
function toggleOrderDetails(iconElement, orderNum) {
    const detailsSection = $(`#order-details-${orderNum}`);
    const radioButton = $(`input[name='order'][value='${orderNum}']`); // 해당하는 라디오 버튼
    const isOpen = detailsSection.is(':visible');

    if (isOpen) {
        // 주문 상세 정보 숨기기
        detailsSection.slideUp();
        $(iconElement).removeClass('icon-arrow-on').addClass('icon-arrow-off'); // 아이콘 변경
    } else {
        // 주문 상세 정보 표시
        detailsSection.slideDown();
        $(iconElement).removeClass('icon-arrow-off').addClass('icon-arrow-on'); // 아이콘 변경

        // 해당하는 라디오 버튼을 체크
        radioButton.prop('checked', true);
    }
}

// 라디오 버튼 클릭 시 주문 상세 정보 토글
$(document).on('change', "input[name='order']", function() {
    const orderNum = $(this).val(); // 선택된 주문번호
    const iconElement = $(`i[onclick="toggleOrderDetails(this, '${orderNum}')"]`); // 아이콘 엘리먼트 찾기

    // 주문 상세 정보 토글 함수 호출
    toggleOrderDetails(iconElement, orderNum);
});


function toggleInquiryProduct() {
	const inquiryTypeSelect = document.getElementById('inquiryTypeSelect');
    const isTypeSelected = !!inquiryTypeSelect.value;
    const isExchangeInquiry = inquiryTypeSelect.value === '교환문의'; // 교환문의인지 확인

    const fields = ['inquiryTitle', 'inquiryContent', 'inquiryEmail'].map(id => document.getElementById(id));
    const sections = ['inquiryProduct', 'inquiryProductQty'].map(id => document.getElementById(id));
    const quantitySection = document.getElementById('quantitySection'); // 수량 조절 영역

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

    quantitySection.style.display = isExchangeInquiry ? 'block' : 'none';
}

function previewImage(event) {
    const fileInput = document.getElementById('image-upload');
    const previewImage = document.getElementById('preview');
	
    const file = fileInput.files[0];
	const uploadStatus = document.getElementById('upload-status');
	
    const allowedExtensions = /(\.jpg|\.jpeg|\.png|\.gif)$/i;

    if (file && !allowedExtensions.exec(file.name)) {
        getCheckModal('JPG, PNG, GIF 파일만 업로드 가능합니다.');
        fileInput.value = '';
        return;
    }

    if (file) {
        const reader = new FileReader();
        reader.onload = function(e) {
            previewImage.src = e.target.result; 
			previewImage.style.display = 'block';
			uploadStatus.textContent = '사진첨부 1';
        };
        reader.readAsDataURL(file);
    } else {
		previewImage.style.display = 'none';
		uploadStatus.textContent = '사진첨부 0';
	}
}

function changeQty(change) {
    const quantityInput = document.getElementById('quantity');
    let currentValue = parseInt(quantityInput.value);

    if (isNaN(currentValue)) {
        currentValue = 0; 
    }

    const newValue = currentValue + change;
    
    if (newValue >= 0 && newValue <= 999) {
        quantityInput.value = newValue;
    }
}


function formatDate(timestamp) {
    var date = new Date(timestamp);
    var year = date.getFullYear();
    var month = (date.getMonth() + 1).toString().padStart(2, '0');
    var day = date.getDate().toString().padStart(2, '0');
    return `${year}.${month}.${day}`;
}