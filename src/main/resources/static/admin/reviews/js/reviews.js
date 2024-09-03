var table;
$(document).ready(function() {
	// 테이블이 이미 초기화되어 있는지 확인
	if (!$.fn.DataTable.isDataTable('#reviews')) {
		table = $('#reviews').DataTable({
			columnDefs: [
				{ targets: 0, orderable: false }, // 첫 번째 컬럼(체크박스 컬럼)에서 정렬 비활성화

			],
			order: [[6, 'desc']], // 리뷰 작성 날짜 컬럼을 최신 날짜순으로 정렬 (내림차순)
			ajax: {
				url: '/admin/reviews/json',
				dataSrc: function(json) {
					$('#total-row').text('총 ' + json.size + '건');
					return json.data;
				}
			},
			columns: [
				{
					data: null,
					render: function(data, type, row) {
						return '<input type="checkbox" id="data-check" class="row-checkbox"><label for="data-check"></label>';
					},
					orderable: false,
				},
				{
					data: 'review_num', // 실제 데이터는 변경하지 않습니다.
					orderable: true // 이 컬럼은 정렬 가능
				},
				{ data: 'review_content' },
				{
					data: 'book_name',
					render: function(data, type, row) {
						return '<a href="#" class="book-title-link" style="color: inherit; text-decoration: underline; cursor: pointer;">' + data + '</a>';
					}
				},
				{ data: 'book_isbn' },
				{ data: 'member_id' },
				{
					data: 'review_write_date',
					render: function(data, type, row) {
						if (type === 'display' || type === 'filter') {
							var date = new Date(data);
							var formattedDate = new Intl.DateTimeFormat('ko-KR', { dateStyle: 'medium' }).format(date);
							return formattedDate;
						}
						return data;
					}
				},
				{
					data: 'review_rating',
					render: function(data, type, row) {
						if (type === 'display' || type === 'filter') {
							return '<span class="fas fa-star stars"></span>'.repeat(data) + '<span class="far fa-star empty-stars"></span>'.repeat(5 - data);
						}
						return data;
					}
				}

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
				$('td:eq(1)', row).html(rowIndex); // 행 번호를 이어서 설정

				$(row).attr('data-id', data.review_num); // 각 행에 고유 ID 설정

				// 제목 컬럼의 링크 클릭 이벤트 추가
				$(row).find('.book-title-link').on('click', function(event) {
					event.preventDefault(); // 링크 기본 동작 방지
					postToDetailPage(data); // 폼 생성 및 제	출 함수 호출
				});
			}
		});
	}

	$('#select-all').on('click', function() {
		var rows = $('#reviews').DataTable().rows({ 'search': 'applied' }).nodes();
		$('input[type="checkbox"]', rows).prop('checked', this.checked);
	});

	$('#reviews tbody').on('change', '.row-checkbox', function() {
		if (!this.checked) {
			$('#select-all').prop('checked', false);
		} else {
			if ($('.row-checkbox:checked').length === $('.row-checkbox').length) {
				$('#select-all').prop('checked', true);
			}
		}
	});

	// 모달 관련 변수
	var modal = document.getElementById("myModal");
	var confirmDeleteButton = document.getElementById("confirm-delete");
	var cancelDeleteButton = document.getElementById("cancel-delete");

	// 삭제 버튼 클릭 이벤트 핸들러
	$('#delete-button').on('click', function() {
		var selectedIds = [];
		$('#reviews').DataTable().$('.row-checkbox:checked').each(function() {
			var rowData = $('#reviews').DataTable().row($(this).closest('tr')).data();
			selectedIds.push(rowData.review_num); // 삭제할 리뷰 번호 수집
		});

		document.getElementById('confirm-delete').style.display = "inline-block";
		document.getElementById('cancel-delete').style.display = "inline-block";

		if (selectedIds.length > 0) {
			// 메시지를 기본 메시지로 리셋
			document.querySelector('#myModal .modal-content p').textContent = `${selectedIds.length}개의 항목을 삭제하시겠습니까?`;

			// Yes와 No 버튼을 보이게 설정
			modal.style.display = "block"; // 모달 표시
		} else {
			// alert 대신 모달 메시지 변경
			document.querySelector('#myModal .modal-content p').textContent = '삭제할 항목을 선택하세요.';
			modal.style.display = "block";
		}
	});

	// 모달 외부 클릭 시 닫기
	window.onclick = function(event) {
		if (event.target == modal) {
			modal.style.display = "none";
		}
	};

	// 삭제 확인 버튼
	confirmDeleteButton.onclick = function() {
		var selectedIds = [];
		$('#reviews').DataTable().$('.row-checkbox:checked').each(function() {
			var rowData = $('#reviews').DataTable().row($(this).closest('tr')).data();
			selectedIds.push(rowData.review_num);
		});

		$.ajax({
			url: '/admin/reviews/delete',  // 서버의 삭제 처리 URL
			type: 'POST',
			contentType: 'application/json',
			data: JSON.stringify(selectedIds),  // 선택된 리뷰 번호들을 JSON으로 전송
			success: function(response) {
				modal.style.display = "none";
				document.querySelector('#myModal .modal-content p').textContent = '삭제가 완료되었습니다.';
				$('#reviews').DataTable().ajax.reload();  // 테이블 새로고침
			},
			error: function(error) {
				document.querySelector('#myModal .modal-content p').textContent = '삭제 중 오류가 발생했습니다.';
				setTimeout(function() {
					modal.style.display = "none";

				}, 3000);
			}
		});
	};

	// 삭제 취소 버튼
	cancelDeleteButton.onclick = function() {
		modal.style.display = "none";
	};

	// 검색 버튼 클릭 이벤트 핸들러
	$('#searchButton').on('click', function() {
		applySearchFilter(); // 검색 필터 적용 함수 호출
	});

	// 검색 입력에서 Enter 키를 누를 때 검색 필터 적용
	$('#searchKeyword').on('keypress', function(event) {
		if (event.key === 'Enter') {
			applySearchFilter();
		}
	});

	// 검색 필터 적용 함수
	function applySearchFilter() {
		var selectedColumn = $('#searchColumn').val(); // 선택한 열의 인덱스
		var keyword = $('#searchKeyword').val(); // 입력된 검색어
		// 선택한 열로 검색 필터를 적용
		table.column(selectedColumn).search(keyword).draw();
	}


	$('#startDate, #endDate').on('change', function() {
		table.draw(); // 날짜 변경 시 테이블 다시 그리기
	});

	// 날짜 필터링 로직 추가
	$.fn.dataTable.ext.search.push(
		function(settings, data, dataIndex) {
			var startDate = $('#startDate').val();
			var endDate = $('#endDate').val();
			var memberDate = data[6];

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

	document.querySelectorAll('.input-box input').forEach(function(input) {
		input.addEventListener('focus', function() {
			// Input 박스를 클릭하면 기존 값을 제거
			this.value = '';
		});
	});

	document.querySelector('[onclick="resetFilters()"]').addEventListener('click', resetFilters);

	// 모든 date-option 버튼에 클릭 이벤트 리스너 추가
	document.querySelectorAll('.date-option').forEach(function(button) {
		button.addEventListener('click', function() {
			setActive(this);  // 클릭된 버튼에 'active' 클래스 설정
		});
	});

	datepicker("startDate", "endDate");
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
		action: '/admin/reviews/details'  // 서버의 상세 페이지 URL로 설정
	});


	// 데이터를 숨김 필드로 추가
	form.append($('<input>', { type: 'hidden', name: 'review_num', value: data.review_num }));

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
	$('#searchKeyword').val('');
	$('#searchColumn').val('5');

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