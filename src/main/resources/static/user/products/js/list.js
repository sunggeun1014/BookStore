$(document).ready(function() {
	conditionBtnStyle();
	Research();
	
	$(".condition-click").each(function() {
	    $(this).on("click", function() {
	        const radioId = $(this).attr("data-radio-id");
	        $(`#${radioId}`).prop("checked", true);
	    });
	});
	
	$(".search-field-click").each(function() {
	    $(this).on("click", function() {
	        const radioId = $(this).attr("data-radio-id");
	        $(`#${radioId}`).prop("checked", true);
			$("#search-form").submit();
	    });
	});
});

function Research() {
	$("#word").on("keydown", function(e) {
		if(e.key === 'Enter') {
			$("#search-form").submit();
		}
	});
}

function conditionBtnStyle() {
	$(".order-area > span").on("click", function() {
		$(".order-area > span").removeClass("order-by-click");
		$(this).addClass("order-by-click");
	});
		
	$(".condition-click").on("click", function() {
		$(".condition-click").removeClass("on");
		$(this).addClass("on");
	});
	
	$(".search-field-click").on("click", function() {
		$(".search-field-click").removeClass("on");
		$(this).addClass("on");
	});
}

function productOrderBy(num) {
	$("input[name='orderByValue']").val(num);
	$("#search-form").submit();
} 