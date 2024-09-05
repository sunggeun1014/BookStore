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
	$("input[name='order_selected_status']").val($("#request-select-area").val());
	
	const form = $("#table-form").each(function() {
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