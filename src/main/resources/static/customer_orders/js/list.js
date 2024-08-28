let table = null;

$(document).ready(function() {
	table = $('#content_table').DataTable({
		ajax: {
			url: '/rest/customerOrders',
			dataSrc: function(json) {
				$("#total_list_cnt").text(`총 ${json.recordsTotal}건`);
				return json.data;
			}
		},
		order: [[10, 'desc']],
		columns: [
			{ 
                data: null, // 데이터 소스가 없으므로 null로 설정
                render: function () {
                    return '<input type="checkbox" class="row_checkbox checkbox_area">';
				}
            },
			{ data: null },
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
	

    $('#search_btn').on('click', function() {
		filter()
        table.draw();
    });

	$("#word").on("keypress", function() {
		table.draw();
	})
	
	checkbox_handler();
	reset_btn();
	datepicker();
});

function filter() {
	table.ajax.url("/rest/dataFilter");
	table.settings()[0].ajax.data = function(d) {
		d.date_column = $("#date_select_box").val();
		d.start_date = $("#start_date").val();
		d.end_date = $("#end_date").val();
		d.order_status = $("input[name='order_status']:checked").val();
		d.search_conditions = $("select[name='search_conditions']").val();
		d.word = $("#word").val();
	} 
	
	table.ajax.reload();
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