function orderCancleBtn(obj) {
	const orderNum = $(obj).attr("data-order-num");
	$.ajax({
		url: "/user/mypage/orderCancle",
		method: "PUT",
		data: { orderNum: orderNum },
		success: function(response) {
			if(response > 0) {
				getCheckModal("<span>취소가 완료 되었습니다.</span><br> 결제 취소는 카드사에 따라<br> 3일~2주 이내에 완료 됩니다.");
				
				$("#confirm-delete").on("click", function() {
					location.href = "#";
				});
			} else {
				getCheckModal("고객센터에 문의 바랍니다.");
			}
			
		},
		error: function() {
			getErrorModal("다시 시도해 주세요.");
		}
	});
	
	 
}