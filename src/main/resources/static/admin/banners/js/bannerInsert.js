$(document).ready(function() {
	datepicker("startDate", "endDate");

	$('#insert-button').on('click', function(e) {
		e.preventDefault(); // 기본 폼 제출 동작 방지

		if (validateForm()) {
			showModal('등록이 완료되었습니다.', true);
		}
	});

	// 모달 확인 버튼 클릭 시 폼 제출 또는 모달 닫기
	$('#confirm-insert').on('click', function() {
	    $('#myModal').hide();
		// 성공 시 폼 제출
	    if ($('#confirm-insert').data('isSuccess')) {
	        $('form').off('submit').submit(); 
	    }
	});

	function showModal(message, isSuccess) {
	    $('#modal-message').html(message);
	    $('#myModal').show();

		if (isSuccess) {
	        $('#confirm-insert').show(); 
	        $('#confirm-insert').data('isSuccess', true); 
	    } else {
	        $('#confirm-insert').show();
	        $('#confirm-insert').data('isSuccess', false); 
	    }
		
	    // 모달 외부 클릭 이벤트 핸들러 제거
	    $('#myModal').off('click');
	}

	function validateForm() {
		let isValid = true;

		if ($('input[name="banner_title"]').val().trim() === '') {
			showModal('제목을 입력해주세요.');
			isValid = false;
			return false;
		}
		if (!$('input[name="banner_position"]:checked').val()) {
			showModal('유형을 선택해주세요.');
			isValid = false;
			return false;
		}

		if (!$('input[name="banner_visible"]:checked').val()) {
			showModal('노출 여부를 선택해주세요.');
			isValid = false;
			return false;
		}

		let startDate = $('#startDate').val().trim();
		let endDate = $('#endDate').val().trim();
		if (startDate === '' || endDate === '') {
			showModal('노출 기간을 입력해주세요.');
			isValid = false;
			return false;
		}

		if ($('#input-file').get(0).files.length === 0) {
			showModal('이미지를 선택해주세요.');
			isValid = false;
			return false;
		}
		return isValid;
	}

});