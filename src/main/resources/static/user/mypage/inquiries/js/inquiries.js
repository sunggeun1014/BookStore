$(document).ready(function () {
    
    loadInquiries();
    
    
    datepicker('startDate', 'endDate');

    
    $('#searchBtn').on('click', function () {
        let startDate = $('#startDate').val();
        let endDate = $('#endDate').val();
        let inquiry_answer_status = $('.tab-btn.active').data('status');

        if (!startDate && !endDate) {
            getCheckModal('시작일 또는 종료일을 선택해주세요.');
            return;
        }

        if (!endDate) endDate = new Date();
        else endDate = new Date(endDate);

        if (!startDate) startDate = new Date('1970-01-01');
        else startDate = new Date(startDate);

        startDate = formatDateForDB(startDate, 'start');
        endDate = formatDateForDB(endDate, 'end');

        loadInquiries(inquiry_answer_status, startDate, endDate);
    });

    
    $('.tab-btn').on('click', function () {
        $('.tab-btn').removeClass('active');
        $(this).addClass('active');
        
        let inquiry_answer_status = $(this).data('status');
        loadInquiries(inquiry_answer_status);
    });

    
    function loadInquiries(inquiry_answer_status = '', startDate = '', endDate = '') {
        let url = '/user/mypage/inquiries-page/search';
        let requestData = {};

        if (startDate && endDate) {
            requestData.startDate = startDate;
            requestData.endDate = endDate;
        }

        if (inquiry_answer_status) {
            requestData.inquiry_answer_status = inquiry_answer_status;
        } else {
            requestData.inquiry_answer_status = '';
        }

        $.ajax({
            url: url,
            method: 'GET',
            data: requestData,
            success: function (data) {
                $('#inquiries-list').empty();
                if (data.length === 0) {
                    $('.result-wrap').show();
                    $('#inquiries-list').hide();
                } else {
                    $('.result-wrap').hide();
                    $('#inquiries-list').show();

                    $.each(data, function (index, inquiry) {
                        let writeDateFormatted = formatDate(inquiry.inquiry_write_date);
                        let inquiryTypeFormatted = formatInquiryType(inquiry.inquiry_type);
						let answerDateFormatted = inquiry.answer_write_date ? formatDate(inquiry.answer_write_date) : '';
						
						let imagePath = `/images/inquiries/${inquiry.inquiries_changed}.jpg`;
					    const defaultImagePath = '/user/common/imgs/book-default-img.png';
                        
						
						
                        let arrowIconHtml = `<i class="icon-arrow-off"></i>`;
						
						let imageHtml = inquiry.inquiries_changed ? `
						       <div class="image-container">
						           <img alt="사진" class="styled-image" src="${imagePath}" onerror="this.src='${defaultImagePath}'">
						       </div>` : '';
						   
                        
                        let inquiryHtml = `
                            <div class="inquiry-info">
                            	<div>
                            		<div class="inquiry-first-content">
                                    	<div class="inquiry-status">${inquiry.inquiry_answer_status === '01' ? '미답변' : '답변 완료'}</div>
                                    	<span class="btn-spacebetween">|</span>
                                    	<div class="solid">${inquiryTypeFormatted}</div>
                                    </div>
                                    <div class="inquiry-second-content">
                                    	<i class="icon-question"></i>
                                        <div class="inquiry-title">${inquiry.inquiry_title}</div>
                                    </div>
                                </div>
                                <div class="inquiry-third-content">
                                	<div>${writeDateFormatted}</div>
                                    ${arrowIconHtml}
                                </div>
                            </div>
                           
                            <!-- 문의 내용 -->
                            <div class="inquiry-detail-info" style="display:none;">
                                <div class="inquiry-detail-content">
									<div>
	                                    <p>${inquiry.inquiry_content ? inquiry.inquiry_content : '문의 내용이 없습니다.'}</p>
									</div>
									${imageHtml}
									<div class="inquiry-detail-action">
	                                	<button class="delete-btn" data-num="${inquiry.inquiry_num}">삭제</button>
									</div>
                                </div>
                           	</div>
							
	                        <!-- 답변 내용 -->
	                        ${inquiry.answer_content ? `
	                        <div class="inquiry-answer-info" style="display: none;">
	                           <div class="inquiry-answer-content">
	                               <i class="icon-answer-arrow"></i>
	                               <div>
	                                   <p>${inquiry.answer_content ? inquiry.answer_content : '답변 내용이 없습니다.'}</p>
	                                   <div style="margin-top: 20px; font-size: 13px;">${answerDateFormatted}</div>
	                               </div>
	                           </div>
	                        </div>` : ''}`;
                        
                        $('#inquiries-list').append(inquiryHtml);
                    });

                    
                    $('.icon-arrow-off').on('click', function () {
                        let $icon = $(this);
                        let $detailContent = $icon.closest('.inquiry-info').next('.inquiry-detail-info');
                        let $answerContent = $detailContent.next('.inquiry-answer-info');
						
                        if ($icon.hasClass('icon-arrow-off')) {
                            $icon.removeClass('icon-arrow-off').addClass('icon-arrow-on');
                            $detailContent.slideDown();
                            if ($answerContent.length) {
                                $answerContent.slideDown();
                            }
                        } else {
                            $icon.removeClass('icon-arrow-on').addClass('icon-arrow-off');
                            $detailContent.slideUp();
                            if ($answerContent.length) {
                                $answerContent.slideUp();
                            }
                        }
                    });

                    
                    $('.delete-btn').on('click', function () {
                        let inquiryNum = $(this).data('num');
						getConfirmModal('정말로 삭제하시겠습니까?', function () {
					        deleteInquiry(inquiryNum); 
					    });
                    });
                }
            },
            error: function () {
                getErrorModal('문의 내역을 불러오는 중 오류가 발생했습니다.');
				return;
            }
        });
    }

    function formatDateForDB(date, type) {
        let formattedDate = new Date(date).toISOString().split('T')[0];
        return type === 'start' ? formattedDate + ' 00:00:00' : formattedDate + ' 23:59:59';
    }

    function formatInquiryType(inquiryType) {
        let inquiryTypeText = '';
        switch (inquiryType) {
            case '01':
                inquiryTypeText = '상품 문의';
                break;
            case '02':
                inquiryTypeText = '배송 문의';
                break;
            case '03':
                inquiryTypeText = '결제 문의';
                break;
            case '04':
                inquiryTypeText = '취소 문의';
                break;
            case '05':
                inquiryTypeText = '교환 문의';
                break;
            case '06':
                inquiryTypeText = '반품 문의';
                break;
            default:
                inquiryTypeText = '기타 문의';
                break;
        }
        return inquiryTypeText;
    }

    function formatDate(timestamp) {
        let date = new Date(timestamp);
        let year = date.getFullYear();
        let month = (date.getMonth() + 1).toString().padStart(2, '0');
        let day = date.getDate().toString().padStart(2, '0');
        return `${year}.${month}.${day}`;
    }

    function deleteInquiry(inquiry_num) {
        $.ajax({
            url: `/user/mypage/inquiries/delete/${inquiry_num}`,
            method: 'DELETE',
            success: function () {
                getCheckModal('문의가 삭제되었습니다.');
                loadInquiries();
				return;
            },
            error: function () {
                getErrorModal('문의 삭제에 실패했습니다.');
				return;
            }
        });
    }
});
