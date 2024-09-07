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
        
        order: [[8, 'desc']], // 리뷰 작성 날짜 컬럼을 최신 날짜순으로 정렬 (내림차순)

        
        // html에서 컬럼 순서대로 db에 저장되어있는 컬럼 이름으로 매핑
        columns: [
            {
                data: null,
                render: function() {
                   return '<input type="checkbox" class="data-check row-checkbox"><label class="data-check-label"></label>';                },
                orderable: false,
            },
            {
                data: null,
                render: function() {
                    return '';
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
            { 
            data: 'manager_addr' ,
            
            render : function(data){
                  
                if (data.length > 10) {
                          return data.substring(0, 10) + '...';
                   } else {
                  
                      return data;
                   }

            }
         },
            {
                data: 'manager_join_date',
                render: function(data, type) {
                    // date값을 받아올때 -> YYYY-MM-DD HH:MM 식으로 포맷해서 출력해준다
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
            // 페이지 내 항목의 순서 번호를 업데이트합니다.
            var api = this.api();
            api.column(1, { page: 'current' }).nodes().each(function(cell, i) {
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
    
     // 체크박스
    $('#select-all').on('click', function() {
        const rows = $('#manager').DataTable().rows({ 'search': 'applied' }).nodes();
        $('input[type="checkbox"]', rows).prop('checked', this.checked);
    });

    // 개별 체크박스 선택 시 배경색 변경
    $('#manager tbody').on('change', '.row-checkbox', function() {
        const $row = $(this).closest('tr'); // 체크박스가 있는 행을 선택

        if (this.checked) {
            $row.addClass('selected-row'); // 배경색을 변경할 클래스 추가
        } else {
            $row.removeClass('selected-row'); // 배경색을 변경할 클래스 제거
        }

        // 전체 체크박스와 개별 체크박스의 선택 상태를 비교하여 '전체 선택' 체크박스 상태를 업데이트
        if ($('.row-checkbox:checked').length === $('.row-checkbox').length) {
            $('#select-all').prop('checked', true);
        } else {
            $('#select-all').prop('checked', false);
        }
    });

    // '전체 선택' 체크박스의 상태 변경 시
    $('#select-all').on('change', function() {
        const isChecked = $(this).prop('checked'); // '전체 선택' 체크박스의 상태

        // 모든 개별 체크박스를 '전체 선택'의 상태에 맞춰 변경
        $('.row-checkbox').prop('checked', isChecked);

        // 각 행에 대해 배경색을 업데이트
        if (isChecked) {
            $('#manager tbody tr').addClass('selected-row'); // 모든 행에 배경색 클래스 추가
        } else {
            $('#manager tbody tr').removeClass('selected-row'); // 모든 행에서 배경색 클래스 제거
        }
    });

    
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
            getConfirmModal(`${selectedIds.length}개의 항목을 변경하시겠습니까?`, deleteBtn);
         
            
        } else {
            // alert 대신 모달 메시지 변경
              getCheckModal(`변경할 항목을 선택해 주세요.`);

         
            modal.style.display = "block";
        }
    });
    
    // 삭제 확인 버튼
    const deleteBtn = function() {
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
                 getCheckModal(`변경이 완료 되었습니다.`);
                $('#manager').DataTable().ajax.reload();  // 테이블 새로고침
            },
            error: function(error) {
                 getCheckModal(`변경중 오류가 발생 했습니다.`);
             
            }
        });
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
            var managerDate = data[8];

            // 날짜 형식을 Date 객체로 변환
            var start = startDate ? new Intl.DateTimeFormat('ko-KR', { dateStyle: 'medium' }).format(new Date(startDate)) : null;
            var end = endDate ? new Intl.DateTimeFormat('ko-KR', { dateStyle: 'medium' }).format(new Date(endDate)) : null;
            var manager = new Intl.DateTimeFormat('ko-KR', { dateStyle: 'medium' }).format(new Date(managerDate));

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
    $('#searchColumn').val('3');

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

 function previewImage(event) {
       var input = event.target;

       if (input.files && input.files[0]) {
           var reader = new FileReader();

           reader.onload = function(e) {
               var preview = document.getElementById('preview');
               preview.src = e.target.result; 
           }

           reader.readAsDataURL(input.files[0]); 
       }
   }

