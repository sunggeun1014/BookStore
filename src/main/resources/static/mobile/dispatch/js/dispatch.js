$(document).ready(function() {
    // 페이지 로드 시 A-01로 기본 설정
    let defaultZoneValue = 'A-01';
    $('#zone-num-display').text(defaultZoneValue);
        
        // 클릭된 요소에 선택된 클래스 추가
    $('#A-01').addClass('selected');

    
    // 로드 시 기본 영역의 데이터 가져오기
    $.ajax({
        type: "POST",
        url: "/mobile/admin/get-inventoryList",
        data: { zoneNum: defaultZoneValue },
        success: function(response) {
            console.log(response);
            
            // 데이터를 렌더링할 #dispatch-list 영역 초기화
            $('#dispatch-list').empty();
            
            // 응답 받은 데이터 처리
            if (response.status === 'success') {
                const dataList = response.data;
                
                // 받은 데이터를 순회하면서 HTML 추가
                dataList.forEach(function(item) {
                    let truncatedTitle = item.inv_title.length > 20 ? item.inv_title.substring(0, 12) + "..." : item.inv_title;

                    const listItem = `
                        <div class="content-dispatch-list">
                            <div class="checkBox" style="text-align: first;">
                                <input type="checkbox" name="selectItem" value=${item.inv_isbn}> 
                            </div>
                            <div class="content-text-wrap">
                                <p class="content-text"><span>${truncatedTitle}</span></p>
                                <p class="content-text">ISBN: <span>${item.inv_isbn}</span></p>
                                <p class="content-sub-txt">요청날짜: <span>${item.inv_registration_date}</span></p>
                            </div>
                            <div style="text-align: end;" class="inventoryQty">
                                <p class="content-text">총 <span>${item.inv_qty} 권</span></p>
                            </div>
                        </div>
                    `;
                    $('#dispatch-list').append(listItem);
                });
            }
        },
        error: function(xhr, status, error) {
            console.error("Error: " + error);
        }
    });

    // 클릭 시 해당 zoneNum으로 변경
    $('.cnt').on('click', function() {
        let zoneValue = $(this).attr('value');
        
        $('.cnt').removeClass('selected');
        
    	$(this).addClass('selected');
    	
        $('#zone-num-display').text(zoneValue);
        
        // AJAX로 서버에 전송
        $.ajax({
            type: "POST",
            url: "/mobile/admin/get-inventoryList",
            data: { zoneNum: zoneValue },
            success: function(response) {
                console.log(response);
                
                // 데이터를 렌더링할 #dispatch-list 영역 초기화
                $('#dispatch-list').empty();
                
                // 응답 받은 데이터 처리
                if (response.status === 'success') {
                    const dataList = response.data;
                    
                    // 받은 데이터를 순회하면서 HTML 추가
                    dataList.forEach(function(item) {
                        let truncatedTitle = item.inv_title.length > 20 ? item.inv_title.substring(0, 12) + "..." : item.inv_title;

                        const listItem = `
                            <div class="content-dispatch-list">
                                <div class="checkBox" style="text-align: first;">
                                    <input type="checkbox" name="selectItem" value=${item.inv_isbn}> 
                                </div>
                                <div class="content-text-wrap">
                                    <p class="content-text"><span>${truncatedTitle}</span></p>
                                    <p class="content-text">ISBN: <span>${item.inv_isbn}</span></p>
                                    <p class="content-sub-txt">요청날짜: <span>${item.inv_registration_date}</span></p>
                                </div>
                                <div style="text-align: end;" class="inventoryQty">
                                    <p class="content-text">총 <span>${item.inv_qty} 권</span></p>
                                </div>
                            </div>
                        `;
                        $('#dispatch-list').append(listItem);
                    });
                }
            },
            error: function(xhr, status, error) {
                console.error("Error: " + error);
            }
        });
    });
});