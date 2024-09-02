let addOrderInfoList = []; 
 
$(document).ready(function() {
	$(".form-btn").on("click", e => {
		e.preventDefault();	
	});
});

function resetBtn() {
	document.getElementById("supplier-form").reset();
}

function orderAddBtn() {
	const isbn = $("#order_detail_isbn");
	const title = $("#order_detail_title");
	const publisher = $("#order_detail_publisher");
	const price = $("#order_detail_price");
	const qty = $("#order_detail_qty");
	
	if(!isbn.val()) {
		getCheckModal('ISBN을 입력해 주세요.', isbn);
	}else if(!title.val()){
		getCheckModal('책제목을 입력해 주세요.', title);
	}else if(!publisher.val()){
		getCheckModal('출판사를 입력해 주세요.', publisher);
	}else if(!price.val()){
		getCheckModal('단가를 입력해 주세요.', price);
	}else if(!qty.val()) {
		getCheckModal('수량을 입력해 주세요.', qty);
	}else {
		addOrderInfoList.push({
			"isbn": isbn.val(),
			"title": title.val(),
			"publisher": publisher.val(),
			"price": numberFormatter(price.val()),
			"qty": qty.val(),
			"total_price": numberFormatter(price.val() * qty.val()) 
		});
		
		orderListDraw();
	}
	
}

function getConfirmModal(msg, func) {
    let divArea = $("<div class='modal-area'></div>");
    let contentArea = $("<div class='modal-content-area'></div>");
    
    let messageArea = $(`<div class='modal-message-area'><span class='modal-message'>${msg}</span></div>`);
    let btnArea = $("<div class='modal-btn-area'><span class='modal-check-btn'>확인</span><span class='modal-close-btn'>취소</span></div>");
    
    contentArea.append(messageArea);
    contentArea.append(btnArea);
    divArea.append(contentArea);

    $("body").append(divArea);
    $(".modal-check-btn").on("click", function() {
		func();
        divArea.remove(); // 모달 제거
    });
    
    // 취소 버튼 클릭 이벤트
    $(".modal-close-btn").on("click", function() {
        divArea.remove(); // 모달 제거
    });
}

function getCheckModal(msg, focusElement) {
    let divArea = $("<div class='modal-area'></div>");
    let contentArea = $("<div class='modal-content-area'></div>");
    
    let messageArea = $(`<div class='modal-message-area'><span class='modal-message'>${msg}</span></div>`);
    let btnArea = $("<div class='modal-btn-area'><span class='modal-check-btn'>확인</span></div>");
    
    contentArea.append(messageArea);
    contentArea.append(btnArea);
    divArea.append(contentArea);

    $("body").append(divArea);
    $(".modal-check-btn").on("click", function() {
        divArea.remove(); // 모달 제거
		if(focusElement) {
			focusElement.focus();
		}
    });
    
}

function orderListDraw() {
	$(".row-data-area").remove();
	
	addOrderInfoList.forEach((data, index, array) => {
		let row = $("<div class='row-data-area'></div>");		
		$("list-count");
		let item = $(`
			<div class="check-data">
				<span>
					<input type="checkbox" id="select-row" name="" class="checkbox row-checkbox" value=""><label for="select-row"></label>
				</span>
			</div>
			
			<div class="no-data">
				<span>${array.length - index}</span>
			</div>
			
			<div class="isbn-data">
				<span>${data["isbn"]}</span>
			</div>
			
			<div class="book-title-data">
				<span>${data["title"]}</span>
			</div>
			
			<div class="publisher-data">
				<span>${data["publisher"]}</span>
			</div>
			
			<div class="price-data">
				<span>${data["price"]}</span>
			</div>
			
			<div class="qty-data">
				<span>${data["qty"]}</span>
			</div>
			
			<div class="total-price-data">
				<span>${data["total_price"]}</span>
			</div>
		`);
		
		row.append(item);
		$("#order-register-list-form").append(row);	
	});
	
}

function qtyHandler(obj) {
	if($(obj).val() > 9999999) {
		$(obj).val(9999999);
	}
}

function numberFormatter(number) {
    const formatter = new Intl.NumberFormat('en-US', {
        style: 'decimal', // 또는 'currency'로 설정 가능
        minimumFractionDigits: 0,
        maximumFractionDigits: 0
    });
    
    return formatter.format(number) + "원";
}