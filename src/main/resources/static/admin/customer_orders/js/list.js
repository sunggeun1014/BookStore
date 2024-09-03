let table = null;

$(document).ready(function() {
	table = $('#customer-orders-table').DataTable({
		ajax: {
			url: '/admin/customer_orders_rest/customerOrders',
			dataSrc: function(json) {
				$("#data-count").text(`총 ${json.recordsTotal}건`);
				return json.data;
			}
		},
		order: [[10, 'desc']],
		columns: [
			{ 
                data: null, // 데이터 소스가 없으므로 null로 설정
                render: function (data, type, row) {
                    return `<input type="checkbox" id="select-row" name="order_num" class="checkbox row-checkbox" value="${row.order_num}"><label for="select-row"></label>`;
				}
            },
			{ data: null },
			{ 
				data: 'order_num',
				render: function(data, type, row) {
					return `<a href="/admin/customer_orders/detail?order_num=${data}" class="order-detail-link">${data}</a>`;
				}
			},
			{ data: 'member_id' },
			{ data: 'member_name' },
			{ 
				data: 'total_order_price', 
				render: function (data) {
					return numberFormatter(data);
				}
			},
			{ 
				data: 'order_purchase_date', 
				render: function (data) {
					const date = new Date(data);
					return date.toISOString().split('T')[0];
				}
			},
			{ data: 'order_delivery_status' },
			{ data: 'order_payment_status' },
			{ data: 'order_status' },
			{
                data: 'order_modify_date',
                render: function (data, type, row) {
					const date = new Date(data);
                    return row.order_purchase_date === data ? '-' : date.toISOString().split('T')[0];
                }
            }
		],
		columnDefs: [
			{ targets: 0, orderable: false },
			{
				targets:"_all" ,
				className:"dt_data_text_center"
			},
			{
			   targets:[1],
			   render: function(data, type, row, meta){
			      return meta.row + 1;
			   }
			},
			{
				targets:[9],
				createdCell: function(td, cellData) {
				    if (cellData === "변경요청") {
				        $(td).addClass('text-color-red');
				    } else if(cellData === "처리완료") {
				        $(td).addClass('text-color-green');
					}
				}
			}
		],
		info: false,
		lengthChange: false,
		dom: "lrtip",
		language: {
		    searchPanes: {
		        i18n: {
		            emptyMessage: "조회된 정보가 없습니다."
		        }
		    },
		    infoEmpty: "조회된 정보가 없습니다.",
		    zeroRecords: "조회된 정보가 없습니다.",
		    emptyTable: "조회된 정보가 없습니다.",
		},
	});

	table.on('draw', function() {
	    $(".checkbox").prop('checked', false);
	});

    $('#searchButton').on('click', function() {
		filter();
        table.draw();
    });

	$("#word").on("keydown", function(e) {
		if(e.key === 'Enter') {
			filter();
		}
	})
	
	datepicker("startDate", "endDate");
	checkboxHandler();
});

function filter() {
	const input = Number($("#word").val());
	
	
	if(Number.isInteger(input)) {
		table.ajax.url("/admin/customer_orders_rest/dataFilter");
		
		table.settings()[0].ajax.data = function(d) {
			d.date_column = $("#dateColumn").val();
			d.start_date = $("#startDate").val();
			d.end_date = $("#endDate").val();
			d.order_status = $("input[name='order_status']:checked").val();
			d.search_conditions = $("#searchColumn").val();
			d.word = $("#word").val();
		}
		table.ajax.reload();
	}
	
}

function delivery_request() {
	let checked_values = $("#table-form input[name='order_num']:checked")
	    .map(function() {
	        return $(this).val();
	    }).get();

	if(checked_values.length > 0) { 
		$.ajax({
			url: "/admin/customer_orders_rest/deliveryRequest",
			method: "POST",
			contentType: 'application/json',
			data: JSON.stringify(checked_values),
			success: function(data) {
				getCheckModal(`${data}건 요청완료`);
			},
			error: function () {
				getCheckModal("ERROR");
			}
		});
	} else {
		getCheckModal("1개 이상의 선택이 필요합니다.");
	}
}

function resetBtn() {
	$(".date-btn").removeClass("active");
	
	$("#select-purchase").prop("selected", true);
	$("#startDate").val("");
	$("#endDate").val("");
	$("#order-status-all").prop("checked", true);
	$("#word").val("");
}

function numberFormatter(number) {
    const formatter = new Intl.NumberFormat('en-US', {
        style: 'decimal', // 또는 'currency'로 설정 가능
        minimumFractionDigits: 0,
        maximumFractionDigits: 0
    });
    
    return formatter.format(number) + "원";
}

function checkboxHandler() {
    $("#select-all").on("change", function() {
        $(".row-checkbox").prop("checked", $(this).prop("checked"));
	});
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