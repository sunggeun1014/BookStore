function price_formatter(number) {
	const formatter = new Intl.NumberFormat('en-US', {
	    style: 'decimal', // 또는 'currency'로 설정 가능
	    minimumFractionDigits: 0,
	    maximumFractionDigits: 0
	});
	
	return formatter.format(number) + "원";
}

function checkbox_chang() {
	$("#select_all").on("change", function() {
		$(".row_checkbox").prop("checked", $(this).prop("checked"));
	});
}

function reset_btn() {
	$(".reset_btn").on("click", function() {
		document.getElementById("filter_form").reset();
	});
}