$(document).ready(function () {
	
	loadNotices('getList');
	
	function loadNotices(type) {
		let url = '';
		let requestData = {};
		
		if(type === 'getList') {
			url = '/user/mypage/notices-page/get-notices-list';
		}
		
		$.ajax({
			url: url,
			method: 'GET',
			data: requestData,
			success: function(data) {
				let $noticeList = $('#notice-list');
				$noticeList.empty();
				
				let noticeQty = 0;
				
				$.each(data, function(index, notice) {
					let noticeNum = (index + 1).toString().padStart(2, '0');
					if(type === 'getList'){
						
						let noticeHtml = `
	                        <li class="notice-li">
	                            <span class="notice-no">${noticeNum}</span>
	                            <span class="notice-title">${notice.notice_title}</span>
	                            <span class="notice-date">${notice.notice_write_date}</span>
	                        </li>
	                    `;
	                    $('#notices-list').append(noticeHtml);             
	                    noticeQty++;
     
                    }
                    
				});
				
				$('#notice-qty').html('<span class="qty-number">' + noticeQty + '</span>건');
			},
			error: function(error) {
                getCheckModal('공지사항을 불러오는 중 오류가 발생했습니다.');
                return;
            }
			
		});
		
	}
});