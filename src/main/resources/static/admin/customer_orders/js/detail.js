checkboxHandler();

function checkboxHandler() {
    $("#select-all").on("change", function() {
        $(".row-checkbox").prop("checked", $(this).prop("checked"));
	});
}

function requestCompletionBtn() {
	let form = $("#table-form input[name='order_detail_num']:checked").map(function() {
	    return {
	        "order_detail_num": this.value,
	        "order_detail_status": $("#request-select-area").val()
	    };
	}).get();
	
	if(form.length > 0) { 
		// 모달창 띄우기
		
	} else {
		// 요청 창 띄우기
	}
}

function getModal(msg) {
	let divArea = $("<div></div>", { class: "modal-area" });
	let contentArea = $("<div><span>확인</span></div>", { class: "modal-content-area" });
	let btnArea = $("<div></div>", { class: "modal-btn-area" });
	let messageArea = $("<div></div>", { class: "modal-message-area" });
	
	let message = $(`<span>${msg}</span>`, { class: "modal-message" })
	let checkBtn = $("<span>확인</span>", { class: "modal-check" })
	let closeBtn = $("<span>취소</span>", { class: "modal-close" })
	
	
	
	contentArea.append(message);
	
	divArea.append(contentArea);
	
	$("body").append(divArea);
}