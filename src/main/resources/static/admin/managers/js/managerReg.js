$(document).ready(function() {
    var idCheckPassed = false;  // 아이디 중복 확인 플래그
    var pwCheckPassed = false;  // 비밀번호 일치 확인 플래그

    // ID 유효성 검사 함수
    function validateId(id) {
        var idPattern = /^[a-zA-Z0-9]{5,}$/;  // 영문자와 숫자, 최소 5자
        return idPattern.test(id);
    }

    // 비밀번호 유효성 검사 함수
    function validatePassword(password) {
        var pwPattern = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{8,}$/;  // 특수문자, 영문자, 숫자 포함, 최소 8자
        return pwPattern.test(password);
    }

    // 이메일 유효성 검사 함수 (특수문자 포함 불가)
    function validateEmail(email) {
        var emailPattern = /^[a-zA-Z0-9]+$/;  // 영문자와 숫자만 허용
        return emailPattern.test(email);
    }

    // 중복 확인 버튼 클릭 시 Ajax 요청
    $('#manager_id').on('change', function() {
        var manager_Id = $('#manager_id').val();

        // ID 유효성 검사 먼저 수행
        if (!validateId(manager_Id)) {
            $('#idCheckForm').show();
            $('#idCheckSuccess').hide();
            $('#idCheckResult').hide();
            idCheckPassed = false;
            return;  // 유효하지 않은 경우 Ajax 요청 중단
        } else {
            $('#idCheckForm').hide();
        }

        // 유효성 검사가 통과되면 Ajax 요청
        $.ajax({
            url: '/admin/managers/checkId',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({ "manager_id": manager_Id }),
            success: function(response) {
                if(response.isAvailable) {
                    // ID 사용 가능
                    $('#idCheckSuccess').show();
                    $('#idCheckResult').hide();
                    idCheckPassed = true; // 아이디 중복 검사 통과
                } else {
                    // ID 중복
                    $('#idCheckResult').show();
                    $('#idCheckSuccess').hide();
                    idCheckPassed = false; // 아이디 중복 검사 실패
                }
            }
        });
    });

    // 비밀번호 확인 버튼 클릭 시
    $('#manager_pw_check, #manager_pw').on('input', function() {
        var password = $('#manager_pw').val();
        var confirmPassword = $('#manager_pw_check').val();

        if (!validatePassword(password) || confirmPassword === '') {
            // 특수문자, 영문자, 숫자 포함, 최소 8자 조건 불충족
            $('#pwCheckForm').show();
            $('#pwCheckSuccess').hide();
            $('#pwCheckResult').hide();
            pwCheckPassed = false;
        } else if (password === confirmPassword) {
            // 비밀번호가 일치할 경우
            $('#pwCheckSuccess').show();
            $('#pwCheckResult').hide();
            $('#pwCheckForm').hide();
            pwCheckPassed = true;
        } else {
            // 비밀번호가 일치하지 않을 경우
            $('#pwCheckResult').show();
            $('#pwCheckSuccess').hide();
            $('#pwCheckForm').hide();
            pwCheckPassed = false;
        }
    });

    // 등록 버튼 클릭 시 검증
    $('#reg-button').on('click', function(event) {
        event.preventDefault(); // 폼 제출 방지

        var manager_name = $('#manager_name').val();
        var manager_emailUser = $('#emailUser').val();
        var phoneNo_part1 = $('#userPart1').val();
        var phoneNo_part2 = $('#userPart2').val();
        var manager_addr = $('#address_kakao').val();
        var manager_detail_addr = $('#manager_detail_addr').val();
        var manager_dept = $('#manager_dept').val();

        if (!idCheckPassed || !pwCheckPassed) {
            getCheckModal('아이디 중복 또는 비밀번호가 일치하지 않습니다.');
            return;
        } else if (!manager_name) {
            getCheckModal("이름을 입력해주세요.");
            return;
        } else if (!manager_emailUser) {
            getCheckModal("이메일을 입력해주세요.");
            return;
        } else if (!phoneNo_part1) {
            getCheckModal("핸드폰 번호를 입력해주세요.");
            return;
        } else if (!phoneNo_part2) {
            getCheckModal("핸드폰 번호를 입력해주세요.");
            return;
        } else if (!manager_addr) {
            getCheckModal("주소를 입력해주세요.");
            return;
        } else if (!manager_detail_addr) {
            getCheckModal("상세주소를 입력해주세요.");
            return;
        } else if (!manager_dept) {
            getCheckModal("부서를 선택해주세요.");
            return;
        } else if (!validateEmail(manager_emailUser)) {
            getCheckModal("이메일을 확인해주세요.");
            return;
        } else {
            // 모든 검증 통과 시 모달 표시
            getCheckModal("등록이 완료되었습니다.");
            
            $(document).on('click', '#confirm-delete', function() {
	       	 	if (idCheckPassed && pwCheckPassed) {
	            	$('#joinForm').submit();  // 모든 조건이 맞을 때만 폼 제출
	        	}
    		});
        }
    });

//    // 모달 확인 버튼 클릭 시 폼 제출
//    $(document).on('click', '#confirm-delete', function() {
//        if (idCheckPassed && pwCheckPassed) {
//            $('#joinForm').submit();  // 모든 조건이 맞을 때만 폼 제출
//        }
//    });

    // 모달 외부 클릭 시 모달 닫기
    window.onclick = function(event) {
        if (event.target == $('#myModal')[0]) {
            $('#myModal').hide();
        }
    };
});
