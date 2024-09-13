window.onload = function() {
    if (performance.navigation.type === performance.navigation.TYPE_RELOAD) {
        document.querySelectorAll('.error-msg').forEach(function(el) {
            el.style.display = 'none';
        });
    }

};

$(document).ready(function() {
    // 아이디와 비밀번호 입력 필드에 이벤트 리스너 추가
    $('#id, #password').on('input', function() {
        var id = $('#id').val().trim();          // 아이디 필드 값 가져오기
        var password = $('#password').val().trim();  // 비밀번호 필드 값 가져오기

        // 아이디와 비밀번호가 모두 입력되었는지 확인
        if (id.length > 0 && password.length > 0) {
            $('#loginBtn').prop("disabled", false);  // 로그인 버튼 활성화
        } else {
            $('#loginBtn').prop("disabled", true);   // 로그인 버튼 비활성화
        }
    });

    // 초기 상태에서 아이디와 비밀번호 필드를 확인하여 버튼 상태 설정
    var id = $('#id').val().trim();
    var password = $('#password').val().trim();
    if (id.length > 0 && password.length > 0) {
        $('#loginBtn').prop("disabled", false);
    }
});
