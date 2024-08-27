$(document).ready(function() {
	let table = $('#content_table').DataTable({
		ajax: {
			url: '/tables/customer_orders',
			dataSrc: function(json) {
				$("#total_list_cnt").text(`총 ${json.recordsTotal}건`);
				return json.data;
			}
		},
		order: [[9, 'desc']],
		columns: [
			{ 
                data: null, // 데이터 소스가 없으므로 null로 설정
                render: function () {
                    return '<input type="checkbox" class="row_checkbox checkbox_area">';
				}
            },
			{ data: 'order_num' },
			{ data: 'member_id' },
			{ data: 'member_name' },
			{ 
				data: 'total_order_price', 
				render: function (data) {
					return price_formatter(data);
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
				targets:"_all",
				className:"dt_data_center"
			},
			{
				targets:[1, 4],
				className:"dt_data_right"
			}
		],
		"info": false,
		lengthChange: false,
		dom: 'lrtip' // 기본 검색 필드 숨기기 (f를 제거)
	});
/*	$('#searchButton').on('click', function() {
		var selectedColumn = $('#searchColumn').val();
		var keyword = $('#searchKeyword').val();
		table.column(selectedColumn).search(keyword).draw(); // 선택된 컬럼과 입력된 키워드로 필터링
	});

	$('#startDate, #endDate').on('change', function() {
		table.draw(); // 날짜 변경 시 테이블 다시 그리기
	});

	// 날짜 필터링 로직 추가
	$.fn.dataTable.ext.search.push(
	function(settings, data, dataIndex) {
		var startDate = $('#startDate').val();
		var endDate = $('#endDate').val();
		var memberDate = data[5]; // 가입날짜 데이터 (6번째 컬럼)
	
		// 날짜 형식을 Date 객체로 변환
		var start = startDate ? new Date(startDate) : null;
		var end = endDate ? new Date(endDate) : null;
		var member = new Date(memberDate);
	
		if ((start === null && end === null) || // 날짜가 설정되지 않았거나
			(start <= member && (end === null || member <= end))) {
			return true;
		}
		return false;
	});*/
});

checkbox_handler();
reset_btn();
datepicker();
		




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

