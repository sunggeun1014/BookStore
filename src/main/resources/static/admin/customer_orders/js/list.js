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
                    return `<input type="checkbox" class="" name="order_num" value="${row.order_num}"><label for="select-all"></label>`;
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
				className:"dt_data_center"
			},
			{
				targets:[1, 2, 5],
				className:"dt_data_right"
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
				        $(td).addClass('text-red');
				    } else if(cellData === "처리완료") {
				        $(td).addClass('text-green');
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
	    $(".checkbox_area").prop('checked', false);
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
});

function filter() {
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

function reset_btn() {
	$(".reset_btn").on("click", function() {
		document.getElementById("filter_form").reset();
	});
}

function date_btn_click(day) {
	const now_date = new Date();
	const new_date = new Date(); 
	
	new_date.setDate(now_date.getDate() - day);
	
	const start_year = new_date.getFullYear();
	const start_month = (new_date.getMonth() + 1).toString().padStart(2, '0');
	const start_day	= new_date.getDate().toString().padStart(2, '0');
	
	
	const end_year = now_date.getFullYear();
	const end_month = (now_date.getMonth() + 1).toString().padStart(2, '0');
	const end_day	= now_date.getDate().toString().padStart(2, '0');
	
	
	$("#start_date").val(`${start_year}-${start_month}-${start_day}`);
	$("#end_date").val(`${end_year}-${end_month}-${end_day}`);
}

function delivery_request() {
	var checked_values = $("#table_form input[name='order_num']:checked")
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
				console.log(data, "요청완료");
			},
			error: function () {
				console.log("ERROR");
			}
		});
	} else {
		// 요청 창 띄우기
	}
}

function checkbox_handler() {
	$("#select_all").on("change", function() {
		$(".row_checkbox").prop("checked", $(this).prop("checked"));
	});
}