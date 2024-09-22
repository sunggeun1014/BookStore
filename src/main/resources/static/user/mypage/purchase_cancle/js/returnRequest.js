$(document).ready(function() {
	$("#selected-all").on("change", function() {
		$(".check-box").prop("checked", $(this).prop("checked"));
		
	});
	
	$(".check-box").on("change", function() {
	    calculateTotalAmount();
		selectedCountChange()
	});
});

function countValidation(obj) {
	const cnt = parseInt($(obj).val());
	const maxQty = Math.max(parseInt($(obj).attr("data-max-qty")), 0);
	
	if(maxQty < cnt) {
		$(obj).val(maxQty);
	} 
	
	const isChecked = $(obj).closest(".cancle-book-detail").find(".checked").prop("checked");
	
	if(isChecked) {
		calculateTotalAmount();
	}
}

function calculateTotalAmount() {
    let totalAmount = 0;

    $(".cancle-book-detail").each(function() {
		const isChecked = $(this).find(".checked").prop("checked");
		
		if(isChecked) {
			const quantity = parseInt($(this).find(".input-count input").val()) || 0;
            const price = parseInt($(this).find(".book-price").text().replace(/[^0-9]/g, "")) || 0;

   			totalAmount += quantity * price;
		}
    });

	$("#refund-price").text(numberFormatter(totalAmount));
}

function numberFormatter(number) {
    const formatter = new Intl.NumberFormat('en-US', {
        style: 'decimal', // 또는 'currency'로 설정 가능
        minimumFractionDigits: 0,
        maximumFractionDigits: 0
    });
    
    return formatter.format(number) + "원";
}

function selectedCountChange() {
	const selectedCount = $(".cancle-book-detail").find("input[type='checkbox']:checked").length;
	
	$("#selected-count").text(`(${selectedCount})`);
}