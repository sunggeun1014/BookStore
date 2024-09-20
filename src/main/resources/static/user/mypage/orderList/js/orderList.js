$(document).ready(function() {
    datepicker('startDate', 'endDate');

    // 시작 날짜 선택 시 종료 날짜 초기화
    $('#startDate').on('change', function() {
        $('#endDate').val('');
        flatpickr("#endDate").clear();
    });

    $('#searchBtn').on('click', function() {
        filterOrders();
    });

    // 각 주문 상태 클릭 이벤트
    $('.history-val').on('click', function() {
        $('.history-val').removeClass('selected');
        $(this).addClass('selected');

        // 날짜 초기화
        $('#startDate').val('');
        $('#endDate').val('');
        flatpickr("#startDate").clear();
        flatpickr("#endDate").clear();

        $('#searchBtn').trigger('click'); 
    });

    function filterOrders() {
        var startDate = $('#startDate').val();
        var endDate = $('#endDate').val();
        var selectedStatus = $('.history-val.selected').next('.history-status').text(); // 선택된 상태 텍스트

        console.log("시작 날짜:", startDate);
        console.log("종료 날짜:", endDate);

        $('.order-history-table-list').hide();
        var hasOrders = false;

        $('.order-history-table-list').each(function() {
            var orderDateText = $(this).find('.order-date span').text();
            var orderDate = new Date(orderDateText);
            var orderStatus = $(this).find('.order-status span').text();
            var deliveryStatus = $(this).find('.delivery-status span').text();

            if (startDate) {
                var start = new Date(startDate);
                start.setHours(0, 0, 0, 0);
                if (orderDate < start) {
                    return;
                }
            }

            if (endDate) {
                var end = new Date(endDate);
                end.setHours(23, 59, 59, 999);
                if (orderDate > end) {
                    return;
                }
            }

            if (selectedStatus && !(orderStatus === selectedStatus || deliveryStatus === selectedStatus)) {
                return;
            }

            $(this).show();
            hasOrders = true;
        });

        // 주문내역이 없을 경우 메시지 표시
        $('.result-wrap').remove(); 
        if (!hasOrders) {
            drawNoResultDefault('.order-history-table', '주문 내역이 존재하지 않습니다.');
        }
    }
});

function drawNoResultDefault(parentEl, msg) {
    const parentElement = document.querySelector(`${parentEl}`);

    const resultWrap = document.createElement("div");
    const p = document.createElement("p");
    const titleText = document.createElement("span");

    resultWrap.classList.add("result-wrap");

    p.textContent = "!";
    titleText.textContent = `${msg}`;

    resultWrap.appendChild(p);
    resultWrap.appendChild(titleText);

    parentElement.appendChild(resultWrap);
}
