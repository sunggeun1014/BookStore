window.onload = function() {
    // URL의 # 이후 부분에서 access_token을 추출
    const fragment = window.location.hash.substring(1); // # 이후의 값을 가져옴
    const params = new URLSearchParams(fragment);

    const accessToken = params.get('access_token');
    
    if (accessToken) {
        // access_token이 존재하는 경우, 서버로 POST 요청
        sendTokenToServer(accessToken);
    } else {
        console.error("Access token이 존재하지 않습니다.");
    }
};
// 서버로부터 토큰에서 추출한 아이디를 받아오는 역할
function sendTokenToServer(accessToken) {
    fetch('/user/members/naver/callback', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ access_token: accessToken })
    })
    .then(response => response.json())
    .then(data => {
        console.log('서버 응답:', data);

        // 서버로부터 받은 네이버 ID를 hidden input 필드에 저장
        if (data.naverId) {            
            checkNaverIdAvailability(data.naverId);

        } else {
            console.error('Naver ID를 받아오지 못했습니다.');
        }
    })
    .catch(error => {
        console.error('서버로 access token 전송 중 오류 발생:', error);
    });
}

// 네이버 ID 중복 체크 함수
function checkNaverIdAvailability(naverId) {
    fetch('/user/members/check-naverId', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({ naver_login_cd: naverId })
    })
    .then(response => response.json())
    .then(data => {
        if (data.isAvailable) {
            console.log('네이버 ID는 사용 가능합니다.');
            window.close();
            if (window.opener) {
                window.opener.document.getElementById('naver_login_cd').value = naverId; // 부모창에 값 설정
                let naverCheckbox = window.opener.document.querySelector('.check-box.naver');
                if (naverCheckbox) {
                    naverCheckbox.checked = true; // 부모 창의 체크박스 체크
                }
                window.opener.getCheckModal("연동이 완료되었습니다.");

            }
            
        } else {
            window.close(); // 버튼 클릭 시 창 닫기
            
        	window.opener.getCheckModal("이미 등록되어있는 계정입니다.");
        }
    })
    .catch(error => {
        console.error('네이버 ID 중복 체크 중 오류 발생:', error);
    });
}