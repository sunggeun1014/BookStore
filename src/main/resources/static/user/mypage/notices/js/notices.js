$(document).ready(function () {
    loadNotices('getList');

    function loadNotices(type) {
        let url = '';
        let requestData = {};

        if (type === 'getList') {
            url = '/user/mypage/notices-page/get-notices-list';
        }

        $.ajax({
            url: url,
            method: 'GET',
            data: requestData,
            success: function (data) {
                let $noticeList = $('#notices-list');
                $noticeList.empty();

                let noticeQty = 0;

                $.each(data, function (index, notice) {
                    let noticeNum = (index + 1).toString().padStart(2, '0');

                    if (type === 'getList') {
                        let noticeHtml = `
                            <li class="notice-li">
                                <span class="notice-no">${noticeNum}</span>
                                <span class="notice-title">${notice.notice_title}</span>
                                <input type="hidden" name="noticeNum" value="${notice.notice_num}" />
                                <span class="notice-date">${notice.notice_write_date}</span>
                            </li>
                        `;
                        $noticeList.append(noticeHtml);
                        noticeQty++;
                    }
                });

                $('#notice-qty').html('<span class="qty-number">' + noticeQty + '</span>건');

                $('.notice-title').on('click', function () {
                    let clickedIndex = $(this).parent().index(); 
                    let noticeNum = $(this).siblings('input[name="noticeNum"]').val();

                    let prevNotice = data[clickedIndex - 1] ? data[clickedIndex - 1] : null;
                    let nextNotice = data[clickedIndex + 1] ? data[clickedIndex + 1] : null;

                    let prevNoticeNum = prevNotice ? prevNotice.notice_num : '';
                    let nextNoticeNum = nextNotice ? nextNotice.notice_num : '';

                    let detailUrl = `/user/mypage/notices-detail?noticeNum=${noticeNum}&prevNoticeNum=${prevNoticeNum}&nextNoticeNum=${nextNoticeNum}`;
                    window.location.href = detailUrl;
                });
            },
            error: function (error) {
                getCheckModal('공지사항을 불러오는 중 오류가 발생했습니다.');
                return;
            }
        });
    }
});
