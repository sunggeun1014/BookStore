var table;
$(document).ready(function() {

    table = $('#manager').DataTable({
        ajax: {
            // 값을 받아오는 url, data타입 작성
            url: '/admin/managers/json',
            dataSrc: function(json) {
                $('#total-row').text('총 ' + json.size + '건');
                return json.data;
            }
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
                    return '<input type="checkbox" id="data-check" class="row-checkbox"><label for="data-check"></label>';
                },
                orderable: false,
            },
            {
                data: null,  // 이 컬럼은 데이터베이스에서 가져오는 데이터를 사용하지 않음
                render: function(data, type, row, meta) {
                    if (meta && meta.row !== undefined) {
                        return meta.row + 1;  // meta.row가 유효하면 1을 더해 반환
                    } else {
                        return '';  // meta.row가 없을 경우 빈 문자열 반환
                    }
                },
                orderable: false,  // 이 컬럼에 대해 정렬을 비활성화
                searchable: false  // 이 컬럼에 대해 검색을 비활성화
            },
            { data: 'manager_name' },
            { 
                data: 'manager_id',
                render: function(data) {
                    return '<a href="#" class="manager-id-link" style="color: inherit; text-decoration: underline; cursor: pointer;">' + data + '</a>';
                }
            },
            { data: 'manager_email' },
            { 
                data: 'manager_dept',
                render: function(data){
                    if(data === '01') {
                        return '물류팀';						
                    } else {
                        return '운영팀';
                    }
                }				
            },
            { data: 'manager_phoneNo' },
            { data: 'manager_addr' },
            {
                data: 'manager_join_date',
                render: function(data, type, row) {
                    // date값을 받아올때 -> YYYY-MM-DD HH:MM 식으로 포맷해서 출력해준다
                    if (type === 'display' || type === 'filter') {
                        var date = new Date(data);
                        var formattedDate = date.toISOString().split('T')[0];
                        return formattedDate;
                    }
                    return data;
                }
            }
        ],

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
    
    $('#select-all').on('click', function() {
        var rows = $('#manager').DataTable().rows({ 'search': 'applied' }).nodes();
        $('input[type="checkbox"]', rows).prop('checked', this.checked);
    });
    
    var modal = document.getElementById("myModal");
    var confirmDeleteButton = document.getElementById("confirm-delete");
    var cancelDeleteButton = document.getElementById("cancel-delete");
    
    // 삭제 버튼 클릭 이벤트 핸들러
    $('#change-button').on('click', function() {
        var selectedIds = [];
        var selectedDept = $('#searchDept').val();  // 선택된 부서 값 가져오기 (문자열 그대로)
		console.log("Selected Dept:", selectedDept);  // 선택된 부서의 값을 콘솔에 출력

        $('#manager').DataTable().$('.row-checkbox:checked').each(function() {
            var rowData = $('#manager').DataTable().row($(this).closest('tr')).data();
            selectedIds.push(rowData.manager_id); // 변경할 매니저 id 수집
            
        });

        if (selectedIds.length > 0) {
            // 메시지를 기본 메시지로 리셋
            document.querySelector('#myModal .modal-content p').textContent = `${selectedIds.length}개의 항목을 변경하시겠습니까?`;

            // Yes와 No 버튼을 보이게 설정
            document.getElementById('confirm-delete').style.display = "inline-block";
            document.getElementById('cancel-delete').style.display = "inline-block";
            modal.style.display = "block"; // 모달 표시
        } else {
            // alert 대신 모달 메시지 변경
            document.querySelector('#myModal .modal-content p').textContent = '변경할 항목을 선택하세요.';
            document.getElementById('confirm-delete').style.display = "none";
            document.getElementById('cancel-delete').style.display = "none";
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
        var selectedDept = $('#searchDept').val();  // 선택된 부서 값 가져오기 (문자열 그대로)

        $('#manager').DataTable().$('.row-checkbox:checked').each(function() {
            var rowData = $('#manager').DataTable().row($(this).closest('tr')).data();
            selectedIds.push(rowData.manager_id);
            console.log("Selected IDs:", selectedIds);
            console.log("Selected IDs:", selectedDept);

        });
		
        $.ajax({
            url: '/admin/managers/update/dept',  // 서버의 삭제 처리 URL
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({
                managerId: selectedIds,
                managerDept: selectedDept
            }),  // 선택된 id들을 JSON으로 전송
            success: function(response) {
                modal.style.display = "none";
                document.querySelector('#myModal .modal-content p').textContent = '변경이 완료되었습니다.';
                $('#manager').DataTable().ajax.reload();  // 테이블 새로고침
            },
            error: function(error) {
                document.getElementById('confirm-delete').style.display = "none";
                document.getElementById('cancel-delete').style.display = "none";
                document.querySelector('#myModal .modal-content p').textContent = '변경 중 오류가 발생했습니다.';
                setTimeout(function() {
                    modal.style.display = "none";
                    document.getElementById('confirm-delete').style.display = "inline-block";
                    document.getElementById('cancel-delete').style.display = "inline-block";
                }, 3000);
            }
        });
    };
    
    // 삭제 취소 버튼
    cancelDeleteButton.onclick = function() {
        modal.style.display = "none";
    };
    
    $('#manager tbody').on('click', '.manager-id-link', function(e) {
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
        table.column(selectedColumn).search(keyword).draw(); 
    });

    // searchKeyword에서 Enter 키를 누를 때 searchButton 클릭 이벤트 실행
    $('#searchKeyword').on('keypress', function(event) {
        if (event.key === 'Enter') {
            $('#searchButton').click();
        }
    });

    $('#startDate, #endDate').on('change', function() {
        table.draw(); // 날짜 변경 시 테이블 다시 그리기
    });

    // 날짜 필터링 로직 추가
    $.fn.dataTable.ext.search.push(
        function(settings, data, dataIndex) {
            var startDate = $('#startDate').val();
            var endDate = $('#endDate').val();
            var managerDate = data[7];

            // 날짜 형식을 Date 객체로 변환
            var start = startDate ? new Date(startDate) : null;
            var end = endDate ? new Date(endDate) : null;
            var manager = new Date(managerDate);

            if ((start === null && end === null) ||
                (start <= manager && (end === null || manager <= end))) {
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

//값 전달하는 곳
function postToDetailPage(data) {
    // 폼 생성
    var existingForm = $('#postToDetailForm');

    if (existingForm.length) {
        existingForm.remove();
    }
    
    var form = $('<form>', {
        method: 'POST',
        action: '/admin/managers/details'  // 서버의 상세 페이지 URL로 설정
    });

    // 데이터를 숨김 필드로 추가
    form.append($('<input>', { type: 'hidden', name: 'manager_id', value: data.manager_id }));

    // 폼을 body에 추가하고 제출
    form.appendTo('body').submit();
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
