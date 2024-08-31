var table;

$(document).ready(function() {
	// 테이블이 이미 초기화되어 있는지 확인
	if (!$.fn.DataTable.isDataTable('#inquiries')) {
		table = $('#inquiries').DataTable({
			order: [[4, 'desc']], // 기본적으로 문의 작성 날짜 컬럼을 최신 날짜순으로 정렬 (내림차순)
			ajax: {
				url: '/admin/inquiries/json',
				dataSrc: function(json) {
					$('#total-row').text('총 ' + json.size + '건');
					return json.data;
				}
			},
			columns: [
				{ data: 'inquiry_num', orderable: true },
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
						var date = new Date(data);
						return new Intl.DateTimeFormat('ko-KR', { dateStyle: 'medium' }).format(date);
					}
				},
				{
					data: 'answer_write_date',
					render: function(data, type, row) {
						if (type === 'display' && !data) {
							return '-';
						} else if (type === 'display' || type === 'filter') {
							var date = new Date(data);
							return new Intl.DateTimeFormat('ko-KR', { dateStyle: 'medium' }).format(date);
						}
						return data; // 정렬 및 필터를 위해 원본 데이터 반환
					}
				},
				{
					data: 'inquiry_answer_status',
					render: function(data, type, row) {
						var color = data === '미완료' ? '#F69E47' : (data === '처리완료' ? '#10A142' : 'black');
						return '<span style="color: ' + color + ';">' + data + '</span>';
					}
				}
			],
			info: false,
			lengthChange: false,
			dom: 'lrtip',
			language: {
				searchPanes: { i18n: { emptyMessage: "조회된 정보가 없습니다." } },
				infoEmpty: "조회된 정보가 없습니다.",
				zeroRecords: "조회된 정보가 없습니다.",
				emptyTable: "조회된 정보가 없습니다.",
			},
			rowCallback: function(row, data, index) {
				// 화면에 표시되는 열을 1부터 시작하도록 변경
				var pageInfo = table.page.info();
				var rowIndex = pageInfo.start + index + 1;
				$('td:eq(0)', row).html(rowIndex);

				$(row).attr('data-id', data.inquiry_num); // 각 행에 고유 ID 설정

				// 제목 컬럼의 링크 클릭 이벤트 추가
				$(row).find('.book-title-link').on('click', function(event) {
					event.preventDefault();
					postToDetailPage(data.inquiry_num);
				});
			}
		});
	}

	$('#startDate, #endDate').on('change', function() {
		table.draw(); // 날짜 변경 시 테이블 다시 그리기
	});

	const collator = new Intl.Collator('ko');
	
	// 날짜 필터링 로직 추가
	$.fn.dataTable.ext.search.push(function(settings, data, dataIndex) {
		var startDate = $('#startDate').val();
		var endDate = $('#endDate').val();
		var memberDate = data[4]; // 'inquiry_write_date' 컬럼의 인덱스


		var selectedStatusLabel = $('input[name="order_status"]:checked').next('label').text().trim(); // 선택된 상태의 라벨 텍스트
		var inquiryStatus = data[6].trim(); // 'inquiry_answer_status' 컬럼의 인덱스 (공백 제거)


		// 날짜 형식을 Date 객체로 변환
		var start = startDate ? new Date(startDate) : null;
		var end = endDate ? new Date(endDate) : null;
		var member = new Date(memberDate);

		// 날짜 필터링 로직
		if (start !== null && member < start) {
			return false; // 시작 날짜보다 이전의 데이터는 제외
		}
		if (end !== null && member > end) {
			return false; // 종료 날짜보다 이후의 데이터는 제외
		}
			
		if (selectedStatusLabel === '미답변' && collator.compare(inquiryStatus, '미완료') !== 0) {
			return false; // "미답변" 상태가 아닌 경우 제외
		} else if (selectedStatusLabel === '처리완료' && collator.compare(inquiryStatus, '처리완료') !== 0) {
			return false; // "처리완료" 상태가 아닌 경우 제외
		}

		// 모든 조건이 맞는 경우 true 반환
		return true;
	});

	// 상태 변경 시 테이블 다시 정렬 및 필터링
	$('input[name="order_status"]').on('change', function() {
		table.draw(); // 상태 변경 시 테이블 다시 그리기
	});

	// 검색 컬럼 변경 시 이벤트 핸들러 추가
	$('#searchColumn').on('change', function() {
		applySearchFilter(); // 검색 필터 적용
	});

	// 검색 필터 적용 함수
	function applySearchFilter() {
		var selectedColumn = parseInt($('#searchColumn').val()); // 선택한 열의 인덱스

		// 선택한 열에 대해 검색 필터를 적용하고 테이블을 다시 그립니다.
		table.order([selectedColumn, 'asc']).draw();
	}


	document.querySelector('[onclick="resetFilters()"]').addEventListener('click', resetFilters);

	// 모든 date-option 버튼에 클릭 이벤트 리스너 추가
	document.querySelectorAll('.date-option').forEach(function(button) {
		button.addEventListener('click', function() {
			setActive(this);  // 클릭된 버튼에 'active' 클래스 설정
		});
	});

	datepicker("startDate", "endDate");
	
});

function postToDetailPage(inquiry_num) {
	let params = {
		inquiry_num : inquiry_num
	}
	
	fnPostMovePage('/admin/inquiries/details', params);
}

function fnMovePage(method, url, params) {
	// 폼 생성
	var form = $('<form>', {
		method: method
		, action: url  // 서버의 상세 페이지 URL로 설정
	});

	// 데이터를 숨김 필드로 추가
	for(key in params) {
		form.append($('<input>', { type: 'hidden', name: key, value: params[key] }));
	}

	// 폼을 body에 추가하고 제출
	form.appendTo('body').submit();
	form.remove();	
}

function fnPostMovePage(url, params) {
	fnMovePage('POST', url, params);
}

function fnGetMovePage(url, params) {
	fnMovePage('GET', url, params);
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

	$('input[name="order_status"]').prop('checked', false); // 모든 라디오 버튼 체크 해제
	$('#order-status-all').prop('checked', true); // '전체' 상태로 체크

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

