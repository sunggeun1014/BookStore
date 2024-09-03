$(document).ready(function() {
    var idCheckPassed = false;  // 아이디 중복 확인 플래그
    var pwCheckPassed = false;  // 비밀번호 일치 확인 플래그

    // 중복 확인 버튼 클릭 시 Ajax 요청
    $('#checkIdBtn').on('click', function() {
        var manager_Id = $('#manager_id').val();
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
    $('#checkPwBtn').on('click', function() {
        var password = $('#manager_pw').val();
        var confirmPassword = $('#manager_pw_check').val();

        if (password === confirmPassword && password !== '') {
            // 비밀번호가 일치할 경우
            $('#pwCheckSuccess').show();
            $('#pwCheckResult').hide();
            pwCheckPassed = true; // 비밀번호 일치
        } else {
            // 비밀번호가 일치하지 않을 경우
            $('#pwCheckResult').show();
            $('#pwCheckSuccess').hide();
            pwCheckPassed = false; // 비밀번호 불일치
        }
    });

    // 등록 버튼 클릭 시 errorCode 확인
    $('#reg-button').on('click', function(event) {
        event.preventDefault(); // 폼 제출 방지

        if (!idCheckPassed || !pwCheckPassed) {
            // 아이디 중복 또는 비밀번호 불일치인 경우
            $('#modal-message').text('아이디 중복 또는 비밀번호가 일치하지 않습니다.');
            $('#myModal').show();
        } else {
            // 모든 검증 통과 시
            $('#modal-message').text('등록이 완료되었습니다.');
            $('#myModal').show();
        }
    });

    // 모달 확인 버튼 클릭 시
    $('#confirm-join').on('click', function() {
        $('#myModal').hide();
        if (idCheckPassed && pwCheckPassed) {
            $('#joinForm').submit();  // 모든 조건이 맞을 때만 폼 제출
        }
    });

    // 모달 외부 클릭 시 모달 닫기
    window.onclick = function(event) {
        if (event.target == $('#myModal')[0]) {
            $('#myModal').hide();
        }
    };
});
