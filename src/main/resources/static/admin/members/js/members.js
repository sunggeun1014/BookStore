var table;
$(document).ready(function() {

    table = $('#member').DataTable({
        ajax: {
            // 데이터를 받아오는 URL과 데이터 타입을 지정합니다.
            url: '/admin/members/json',
            dataSrc: function(json) {
                $('#total-row').text('총 ' + json.size + '건');
                return json.data;
            }
        },
        
        // 모든 컬럼을 가운데 정렬합니다.
        columnDefs: [
            { targets: '_all', className: 'dt-center' }
        ],
        
        order: [[5, 'desc']], // 가입 날짜 컬럼을 최신순으로 정렬합니다.
        
        // 각 컬럼을 데이터와 매핑합니다.
        columns: [
            {
                data: null,
                render: function() {
                    return '';
                },
                orderable: true,
                searchable: false
            },
            { data: 'member_name' },
            { 
                data: 'member_id',
                render: function(data, type, row) {
                    return '<a href="#" class="member-id-link" style="color: inherit; text-decoration: underline; cursor: pointer;">' + data + '</a>';
                }
            },
            { data: 'member_email' },
            { data: 'member_phoneNo' },
            {
                data: 'member_date',
                render: function(data, type) {
                    if (type === 'display' || type === 'filter') {
                        var date = new Date(data);
                        var formattedDate = new Intl.DateTimeFormat('ko-KR', { dateStyle: 'medium' }).format(date);
                        return formattedDate;
                    }       
                    return data;
                }
            }
        ],
        
        drawCallback: function(settings) {
            var api = this.api();
            var startIndex = api.page.info().start; // 현재 페이지의 시작 인덱스를 가져옵니다.
    
            // 번호 열을 다시 설정합니다.
            api.column(0, { search: 'applied', order: 'applied' }).nodes().each(function(cell, i) {
                cell.innerHTML = startIndex + i + 1;  // 각 행에 번호를 설정합니다.
            });
        },
        
        "info": false, // 기본 정보 텍스트를 숨깁니다.
        lengthChange: false, // 페이지 길이 변경 옵션을 숨깁니다.
        dom: 'lrtip', // 기본 검색 필드를 숨깁니다.
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
    
    $('#member tbody').on('click', '.member-id-link', function(e) {
        e.preventDefault(); 

        var data = table.row($(this).parents('tr')).data();

        postToDetailPage(data);
    });
    
    // 검색 버튼 클릭 이벤트 핸들러
    $('#searchButton').on('click', function() {
        var selectedColumn = $('#searchColumn').val();
        var keyword = $('#searchKeyword').val();

        // 이전 검색어를 초기화합니다.
        table.columns().search('');

        // 선택된 컬럼과 검색어로 필터링합니다.
        table.column(selectedColumn).search(keyword);

        // 테이블을 다시 그려 필터를 적용합니다 (날짜 필터와 검색어 필터 모두 적용)
        table.draw();
    });

    // 검색어 입력란에서 Enter 키를 누를 때 검색 버튼 클릭 이벤트를 실행합니다.
    $('#searchKeyword').on('keypress', function(event) {
        if (event.key === 'Enter') {
            $('#searchButton').click();
        }
    });
    
    // 날짜 필터링 로직을 추가합니다.
    $.fn.dataTable.ext.search.push(
        function(settings, data, dataIndex) {
            var startDate = $('#startDate').val();
            var endDate = $('#endDate').val();
            var membersDate = data[5]; // 가입 날짜 컬럼 인덱스
            
            // 날짜를 Date 객체로 변환합니다.
            var start = startDate ? new Date(startDate) : null;
            var end = endDate ? new Date(endDate) : null;
            var memberDate = new Date(membersDate);

            // memberDate가 유효한 날짜인지 확인합니다.
            if (isNaN(memberDate)) {
                return false;
            }

            // 시간 부분을 제거합니다.
            memberDate.setHours(0,0,0,0);

            if ((start === null && end === null) ||
                (start === null && memberDate <= end) ||
                (start <= memberDate && end === null) ||
                (start <= memberDate && memberDate <= end)) {
                return true;
            }
            return false;
        }
    );
    
    // 입력 필드 클릭 시 기존 값을 제거합니다.
    document.querySelectorAll('.input-box input').forEach(function(input) {
        input.addEventListener('focus', function() {
            this.value = '';
        });
    });

    // 초기화 버튼 클릭 시 필터를 초기화합니다.
    document.querySelector('[onclick="resetFilters()"]').addEventListener('click', resetFilters);
    
    // 모든 date-option 버튼에 클릭 이벤트 리스너를 추가합니다.
    document.querySelectorAll('.date-option').forEach(function(button) {
        button.addEventListener('click', function() {
            setActive(this);  // 클릭된 버튼에 'active' 클래스를 설정합니다.
        });
    });
    
    datepicker("startDate", "endDate");

});

// 오늘 날짜를 설정하는 함수에서 테이블을 다시 그리지 않습니다.
function setToday() {
    var today = new Date().toISOString().split('T')[0];
    $('#startDate').val(today);
    $('#endDate').val(today);
}

// 날짜 범위를 설정하는 함수에서 테이블을 다시 그리지 않습니다.
function setDateRange(days) {
    var startDate = new Date();
    startDate.setDate(startDate.getDate() - days);
    $('#startDate').val(startDate.toISOString().split('T')[0]);
    $('#endDate').val(new Date().toISOString().split('T')[0]);
}

// 필터를 초기화하는 함수입니다.
function resetFilters() {
    // 검색어 필터를 초기화합니다.
    $('#searchKeyword').val('');
    // 기본 첫 번째 옵션으로 설정합니다.
    $('#searchColumn').val('2');

    // 날짜 필터를 초기화합니다.
    $('#startDate').val('');
    $('#endDate').val('');

    // DataTables 검색 및 필터링을 초기화합니다.
    table.search('').columns().search('').draw();

    table.draw();
}

// 클릭된 date-option 버튼에 'active' 클래스를 설정합니다.
function setActive(element) {
    var options = document.querySelectorAll('.date-option');
    options.forEach(function(option) {
        option.classList.remove('active');
    });

    element.classList.add('active');
}

// 상세 페이지로 데이터를 전달하는 함수입니다.
function postToDetailPage(data) {
    var existingForm = $('#postToDetailForm');

    if (existingForm.length) {
        existingForm.remove();
    }
    
    var form = $('<form>', {
        method: 'POST',
        action: '/admin/members/details'
    });

    form.append($('<input>', { type: 'hidden', name: 'member_id', value: data.member_id }));

    form.appendTo('body').submit();
}
