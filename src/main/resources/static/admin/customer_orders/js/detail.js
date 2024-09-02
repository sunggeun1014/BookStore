$(document).ready(function() {
	checkboxHandler();
});

$("button").on("click", function(event) {
    event.preventDefault();
});

function checkboxHandler() {
    $("#select-all").on("change", function() {
        $(".row-checkbox").prop("checked", $(this).prop("checked"));
	});
}

function requestCompletionBtn() {
	const form = $("#table-form input[name='order_detail_num']:checked").map(function() {
		return $(this).val();
	}).get();
	
	if(form.length > 0) {
		getConfirmModal('주문 상태를 변경하시겠습니까?', function() {
			document.getElementById("table-form").submit();
		})
		
	} else {
		getCheckModal('1개 이상의 선택이 필요합니다.');
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

function getCheckModal(msg) {
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
    });
    
}

