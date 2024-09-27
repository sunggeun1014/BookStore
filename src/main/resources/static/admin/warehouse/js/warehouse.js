var talbe;
$(document).ready(function() {

    table = $('#warehouse').DataTable({
	ajax: {
	    // 값을 받아오는 url, data타입 작성
	    url: '/admin/warehouse/json'
	},
	
	// 모든 컬럼을 가운데 정렬
	columnDefs: [
	    { targets: '_all', className: 'dt-center' }
	],
		
	
	// html에서 컬럼 순서대로 db에 저장되어있는 컬럼 이름으로 매핑
	columns: [
	    {
	        data: null,
	        render: function() {
	            return '';
	        },
	        orderable: false,  // 이 컬럼에 대해 정렬을 비활성화
	        searchable: false  // 이 컬럼에 대해 검색을 비활성화
	    },
	    { 
	        data: 'inv_isbn',
	        render: function(data) {
	            return '<a href="#" class="isbn-link" style="color: inherit; text-decoration: underline; cursor: pointer;">' + data + '</a>';
	        }
	    },
	    { 
			data: 'inv_title', 
			render : function(data){
					
				 if (data.length > 10) {
	                    return data.substring(0, 10) + '...';
	             } else {
					
	               return data;
	             }
	
			}
		},
	    
	    { data: 'inv_qty' },
	    {
	        data: 'inv_registration_date',
			render: function(data, type) {
				if (type === 'display' || type === 'filter') {
					var date = new Date(data);
					var year = date.getFullYear();
					var month = String(date.getMonth() + 1).padStart(2, '0'); // 월은 0부터 시작하므로 +1
					var day = String(date.getDate()).padStart(2, '0');
					var formattedDate = `${year}-${month}-${day}`;
					return formattedDate;
				}
				return data;
			}
	    },
	  	{ data: 'zone_num' }
	],
	
	drawCallback: function(settings) {
	    // 페이지 내 항목의 순서 번호를 업데이트합니다.
	    let api = this.api();
		let filteredRecords = api.rows({ search: 'applied' }).count();
		
		$('#total-row').text(`총 ${filteredRecords}건`);
	    api.column(0, { page: 'current' }).nodes().each(function(cell, i) {
	        // 페이지의 첫 번째 항목 인덱스를 기준으로 순서 번호를 계산합니다.
	        var pageStart = api.settings()[0]._iDisplayStart;
	        $(cell).html(pageStart + i + 1);
	    });
	},
	
	"info": false, // 기본 적용 텍스쳐 숨기기
	lengthChange: false, // 기본 적용 텍스쳐 숨기기
	dom: 'lrtip', // 기본 검색 필드 숨기기 (f를 제거)
	language: {
	    searchPanes: {
	        i18n: {
	            emptyMessage: "조회된 정보가 없습니다."
	        }
	    },
	    infoEmpty: "조회된 정보가 없습니다.",
	    zeroRecords: "조회된 정보가 없습니다.",
	    emptyTable: "조회된 정보가 없습니다.",
	}
        
        
    
    });
    
	// 날짜 필터링 로직 추가
	$.fn.dataTable.ext.search.push(
	    function(settings, data, dataIndex) {
	        var startDate = $('#startDate').val();
	        var endDate = $('#endDate').val();
	        var warehousingDate = data[4];
	        
	        // 날짜 형식을 Date 객체로 변환
	        var start = startDate ? new Intl.DateTimeFormat('ko-KR', { dateStyle: 'medium' }).format(new Date(startDate)) : null;
	        var end = endDate ? new Intl.DateTimeFormat('ko-KR', { dateStyle: 'medium' }).format(new Date(endDate)) : null;
	        var manager = new Intl.DateTimeFormat('ko-KR', { dateStyle: 'medium' }).format(new Date(warehousingDate));

	        if ((start === null && end === null) ||
	            (start <= manager && (end === null || manager <= end))) {
	            return true;
	        }
	        return false;
	    }
	);

	
    $('#warehouse tbody').on('click', '.isbn-link', function(e) {
        e.preventDefault(); // 기본 링크 동작 방지

        // 클릭된 링크의 행 데이터 가져오기
        var data = table.row($(this).parents('tr')).data();

        // 데이터를 POST 방식으로 전송
        postToDetailPage(data);
    });
    
    // 검색 버튼 클릭 이벤트 핸들러
    $('#searchButton').on('click', function() {
        var selectedColumn = $('#searchColumn').val();
        var keyword = $('#searchKeyword').val();
        // 선택된 컬럼과 입력된 키워드로 필터링
		table.ajax.reload(); 
        table.column(selectedColumn).search(keyword).draw();
    });

    // searchKeyword에서 Enter 키를 누를 때 searchButton 클릭 이벤트 실행
    $('#searchKeyword').on('keypress', function(event) {
        if (event.key === 'Enter') {
            $('#searchButton').click();
        }
    });

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
    // 기본 첫 번째 옵션으로 설정 html쪽 select 첫번째로 초기화 시켜준다
    $('#searchColumn').val('1');

    // 날짜 필터 초기화
    $('#startDate').val('');
    $('#endDate').val('');
	$(".date-btn").removeClass("active");
	
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

function setActive(element) {
    // 모든 date-option 버튼에서 'active' 클래스를 제거
    var options = document.querySelectorAll('.date-option');
    options.forEach(function(option) {
        option.classList.remove('active');
    });

    // 클릭된 요소에 'active' 클래스를 추가
    element.classList.add('active');
}

//값 전달하는 곳
function postToDetailPage(data) {
    // 폼 생성
    var existingForm = $('#postToDetailForm');

    if (existingForm.length) {
        existingForm.remove();
    }
    
    var form = $('<form>', {
        method: 'POST',
        action: '/admin/warehouse/details'  // 서버의 상세 페이지 URL로 설정
    });

    // 데이터를 숨김 필드로 추가
    form.append($('<input>', { type: 'hidden', name: 'inv_isbn', value: data.inv_isbn }));

    // 폼을 body에 추가하고 제출
    form.appendTo('body').submit();
} 