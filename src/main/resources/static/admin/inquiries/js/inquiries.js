var table;
$(document).ready(function() {
	// 테이블이 이미 초기화되어 있는지 확인
	if (!$.fn.DataTable.isDataTable('#inquiries')) {
		table = $('#inquiries').DataTable({
			columnDefs: [
			],
			order: [[5, 'desc']], // 문의 작성 날짜 컬럼을 최신 날짜순으로 정렬 (내림차순)
			ajax: {
				url: '/admin/inquiries/json',
				dataSrc: function(json) {
					$('#total-row').text('총 ' + json.size + '건');
					return json.data;
				}
			},
			columns: [
				{
					data: 'inquiry_num', // 실제 데이터는 변경하지 않습니다.
					orderable: true // 이 컬럼은 정렬 가능
				},
				{
					data: 'inquiry_title',
					render: function(data, type, row) {
						return '<a href="#" class="book-title-link" style="color: inherit; text-decoration: underline; cursor: pointer;">' + data + '</a>';
					}
				},
				{ data: 'inquiry_type' },
				{ data: 'member_id' },
				{
					data: 'inquiry_write_date',
					render: function(data, type, row) {
						if (type === 'display' || type === 'filter') {
							var date = new Date(data);
							var formattedDate = date.toISOString().split('T')[0];
							return formattedDate;
						}
						return data;
					}
				},
				{
					data: 'answer_write_date',
					render: function(data, type, row) {
						if (type === 'display' || type === 'filter') {
							var date = new Date(data);
							var formattedDate = date.toISOString().split('T')[0];
							return formattedDate;
						}
						return data;
					}
				},
				{ data: 'inquiry_answer_status'}

			],
			"info": false,
			lengthChange: false,
			dom: 'lrtip',
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
			rowCallback: function(row, data, index) {
				// 화면에 표시되는 열을 1부터 시작하도록 변경
				var pageInfo = table.page.info(); // 현재 페이지 정보를 가져옴
				var rowIndex = pageInfo.start + index + 1; // 현재 페이지 시작 인덱스 + 현재 행 인덱스 + 1
				$('td:eq(0)', row).html(rowIndex); // 행 번호를 이어서 설정

				$(row).attr('data-id', data.inquiry_num); // 각 행에 고유 ID 설정

				// 제목 컬럼의 링크 클릭 이벤트 추가
				$(row).find('.book-title-link').on('click', function(event) {
					event.preventDefault(); // 링크 기본 동작 방지
					postToDetailPage(data); // 폼 생성 및 제	출 함수 호출
				});
			}
		});
	}




	$('#startDate, #endDate').on('change', function() {
		table.draw(); // 날짜 변경 시 테이블 다시 그리기
	});

	// 날짜 필터링 로직 추가
	$.fn.dataTable.ext.search.push(
		function(settings, data, dataIndex) {
			var startDate = $('#startDate').val();
			var endDate = $('#endDate').val();
			var memberDate = data[5];

			// 날짜 형식을 Date 객체로 변환
			var start = startDate ? new Date(startDate) : null;
			var end = endDate ? new Date(endDate) : null;
			var member = new Date(memberDate);

			if ((start === null && end === null) ||
				(start <= member && (end === null || member <= end))) {
				return true;
			}
			return false;
		}
	);
	document.querySelector('[onclick="resetFilters()"]').addEventListener('click', resetFilters);

	// 모든 date-option 버튼에 클릭 이벤트 리스너 추가
	document.querySelectorAll('.date-option').forEach(function(button) {
		button.addEventListener('click', function() {
			setActive(this);  // 클릭된 버튼에 'active' 클래스 설정
		});
	});


});

function postToDetailPage(data) {
	var existingForm = $('#postToDetailForm');

	if (existingForm.length) {
		existingForm.remove();
	}

	// 폼 생성
	var form = $('<form>', {
		id: 'postToDetailForm',
		method: 'POST',
		action: '/admin/inquiries/details'  // 서버의 상세 페이지 URL로 설정
	});


	// 데이터를 숨김 필드로 추가
	form.append($('<input>', { type: 'hidden', name: 'inquiry_num', value: data.inquiry_num }));

	// 폼을 body에 추가하고 제출
	form.appendTo('body').submit();
}


function setToday() {
	var today = new Date().toISOString().split('T')[0];
	$('#startDate').val(today);
	$('#endDate').val(today).trigger('change');
}

function setDateRange(days) {
	var startDate = new Date();
	startDate.setDate(startDate.getDate() - days);
	$('#startDate').val(startDate.toISOString().split('T')[0]);
	$('#endDate').val(new Date().toISOString().split('T')[0]).trigger('change');
}

function resetFilters() {
	// 검색어 필터 초기화
	$('#searchColumn').val('2');

	// 날짜 필터 초기화
	$('#startDate').val('');
	$('#endDate').val('');

	// DataTables 검색 및 필터링 초기화
	table.search('').columns().search('').draw(); // 검색어 및 모든 컬럼 필터 초기화

	table.draw();
}


function setActive(element) {
	// 모든 date-option 버튼에서 'active' 클래스를 제거
	var options = document.querySelectorAll('.date-option');
	options.forEach(function(option) {
		option.classList.remove('active');
	});

	// 클릭된 요소에 'active' 클래스를 추가
	element.classList.add('active');
}

datepicker("startDate", "endDate");