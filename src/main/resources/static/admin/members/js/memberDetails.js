function isNumberKey(evt) {
    var charCode = evt.which ? evt.which : evt.keyCode;
    
    // charCode 46은 소수점, 69는 'e' (소문자), 43은 '+', 45는 '-'를 의미합니다.
    // 이들 문자가 입력되지 않도록 차단합니다.
    if (charCode === 46 || charCode === 69 || charCode === 43 || charCode === 45) {
        return false; // e, ., +, -를 차단
    }
    return true; // 숫자는 허용
}

document.querySelectorAll('.input-box input').forEach(function(input) {
	input.addEventListener('focus', function() {
		// Input 박스를 클릭하면 기존 값을 제거
		this.value = '';
	});
});

function validateForm() {
	const userPart1 = document.getElementById('userPart1').value;
	const userPart2 = document.getElementById('userPart2').value;

	if (userPart1.length !== 4 || userPart2.length !== 4) {
		alert('각 부분에 4자리 숫자를 입력해주세요.');
		return false; // 폼 제출 중단
	}
	return true; // 폼 제출 진행
}

function displayEmail() {
	const emailUser = document.getElementById('emailUser').value;
	const emailDomain = document.getElementById('emailDomain').value;
	const completeEmail = `${emailUser}@${emailDomain}`;
	console.log(completeEmail);
}


function toggleCustomDomain() {
    var emailDomainSelect = document.getElementById("emailDomain");
    var customDomainInput = document.getElementById("customEmailDomain");

    if (emailDomainSelect.value === "custom") {
        customDomainInput.style.display = "inline-block";  // input 필드를 표시
        customDomainInput.name = "emailDomain";  // input 필드의 name을 emailDomain으로 변경
        emailDomainSelect.name = "";  // select의 name을 비워서 전송되지 않도록 함
    } else {
        customDomainInput.style.display = "none";  // input 필드를 숨김
        emailDomainSelect.name = "emailDomain";  // select의 name을 emailDomain으로 복구
        customDomainInput.name = "customEmailDomain";  // input 필드의 name을 customEmailDomain으로 복구
    }
}


