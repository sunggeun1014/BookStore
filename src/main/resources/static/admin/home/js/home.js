console.log('하이')

var table;
$(document).ready(function() {
    // 테이블이 이미 초기화되어 있는지 확인
    // 문의내역 테이블
    if (!$.fn.DataTable.isDataTable('#inquiries')) {
        table = $('#inquiries').removeAttr('width').DataTable({
            "pageLength": 5,
            "paging": true,
            "lengthChange": false,
            "searching": false,
            "ordering": true,
            "autoWidth": false,
            columnDefs:
                [
                    { targets: 0, orderable: false }, // 첫 번째 컬럼(체크박스 컬럼)에서 정렬 비활성화
                    // 가운데정렬
                    {
                        className: 'table-center',
                        targets: '_all'
                    },
                    {
                        width: '40px',
                        targets: 0
                    },
                ],
            order: [[0, 'asc']],
            ajax: {
                url: '/admin/home/inquiries/json',
                dataSrc: 'data',
            },
            columns: [
                {
                    data: null,  // 이 컬럼은 데이터베이스에서 가져오는 데이터를 사용하지 않음
                    render: function(data, type, row, meta) {
                        return meta.row + 1;  // meta.row는 0부터 시작하는 행 인덱스이므로 +1 해줌
                    },
                    orderable: false,  // 이 컬럼에 대해 정렬을 비활성화
                },
                {
                    data: 'inquiry_title',
                },
                {
                    data: 'inquiry_type',
                },
                {
                    data: 'inquiry_answer_status',
                },
                {
                    data: 'answer_write_date',
                },

            ],
            "info": false,
            dom: 't',
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
    }

    // 입출고내역 테이블
    if (!$.fn.DataTable.isDataTable('#stock')) {
        table = $('#stock').removeAttr('width').DataTable({
            "pageLength": 5,
            "paging": true,
            "lengthChange": false,
            "searching": false,
            "ordering": true,
            "autoWidth": false,
            columnDefs:
                [
                    { targets: 0, orderable: false }, // 첫 번째 컬럼(체크박스 컬럼)에서 정렬 비활성화
                    // 가운데정렬
                    {
                        className: 'table-center',
                        targets: '_all'
                    },
                    {
                        width: '40px',
                        targets: 0
                    },
                ],
            order: [[0, 'asc']],
            ajax: {
                url: '/admin/home/stocks/json',
                // dataSrc: 'data',
                dataSrc: function (response) {
                    console.log(response);
                    return response.data || [];
                }
            },
            columns: [
                {
                    data: null,  // 이 컬럼은 데이터베이스에서 가져오는 데이터를 사용하지 않음
                    render: function(data, type, row, meta) {
                        return meta.row + 1;  // meta.row는 0부터 시작하는 행 인덱스이므로 +1 해줌
                    },
                    orderable: false,  // 이 컬럼에 대해 정렬을 비활성화
                },
                {
                    data: 'log_transaction_num',
                },
                {
                    data: 'log_transaction_status',
                    createdCell: function(td, cellData) {
                        if (cellData === "반품입고" || cellData === "입고") {
                            $(td).addClass('text-color-blue');
                        } else {
                            $(td).addClass('text-color-green');
                        }
                    }
                },
                {
                    data: 'log_operation_date',
                    render: function (data) {
                        const date = new Date(data);
                        return date.toISOString().split('T')[0];
                    }
                },

            ],
            info: false,
            dom: 't',
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
    }
});