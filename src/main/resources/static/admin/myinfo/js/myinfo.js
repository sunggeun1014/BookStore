window.onload = function() {
	document.getElementById("address_kakao").addEventListener("click", function() { //주소입력칸을 클릭하면
		//카카오 지도 발생
		new daum.Postcode({
			oncomplete: function(data) { //선택시 입력값 세팅
				document.getElementById("address_kakao").value = data.address; // 주소 넣기
				document.querySelector("input[name=address_detail]").focus(); //상세입력 포커싱
			}
		}).open();
	});
}

function limitLength(input, maxLength) {
	if (input.value.length > maxLength) {
		input.value = input.value.slice(0, maxLength);
	}
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


function prepareSubmit() {
	// 이메일 조합
	const emailUser = document.getElementById("emailUser").value.trim();
	const emailDomainSelect = document.getElementById("emailDomain").value;
	let emailDomain = emailDomainSelect === "custom" ? document.getElementById("customEmailDomain").value.trim() : emailDomainSelect;
	const fullEmail = emailUser && emailDomain ? emailUser + "@" + emailDomain : "";
	document.getElementById("fullEmail").value = fullEmail;

	// 전화번호 조합
	const countryNum = document.getElementById("countryNum").value;
	const userPart1 = document.getElementById("userPart1").value.trim();
	const userPart2 = document.getElementById("userPart2").value.trim();
	const fullPhone = countryNum && userPart1 && userPart2 ? `${countryNum}-${userPart1}-${userPart2}` : "";
	document.getElementById("fullPhone").value = fullPhone;

	// 폼 유효성 검사
	return validateForm();
}

function toggleCustomDomain() {
	const emailDomainSelect = document.getElementById("emailDomain");
	const customEmailDomain = document.getElementById("customEmailDomain");
	customEmailDomain.style.display = emailDomainSelect.value === "custom" ? "inline-block" : "none";
}

function validateForm() {
	let isValid = true;

	// 이메일 검증
	const emailUser = document.getElementById("emailUser").value.trim();
	const emailDomainSelect = document.getElementById("emailDomain").value;
	const customEmailDomain = document.getElementById("customEmailDomain").value.trim();
	const emailError = document.getElementById("emailError");

	if ((emailUser && (emailDomainSelect === "" || (emailDomainSelect === "custom" && customEmailDomain === ""))) ||
		(!emailUser && (emailDomainSelect !== "" && emailDomainSelect !== "custom"))) {
		emailError.style.display = "block";
		emailError.textContent = "이메일을 모두 입력해 주세요.";
		isValid = false;
	} else {
		emailError.style.display = "none";
	}

	// 전화번호 검증
	const countryNum = document.getElementById("countryNum").value;
	const userPart1 = document.getElementById("userPart1").value.trim();
	const userPart2 = document.getElementById("userPart2").value.trim();
	const phoneError = document.getElementById("phoneError");

	if ((countryNum && (!userPart1 || !userPart2)) || ((!countryNum || countryNum === "") && (userPart1 || userPart2))) {
		phoneError.style.display = "block";
		phoneError.textContent = "전화번호를 모두 입력해 주세요.";
		isValid = false;
	} else {
		phoneError.style.display = "none";
	}

	return isValid; // 폼 제출을 허용하거나 중지
}

function previewImage(event) {
       var input = event.target;

       if (input.files && input.files[0]) {
           var reader = new FileReader();

           reader.onload = function(e) {
               var preview = document.getElementById('preview');
               var imgIn = document.getElementById('img-in');
               preview.src = e.target.result; // 업로드된 파일을 미리 보기 이미지로 설정
               imgIn.style.display = 'none';
               
               preview.style.display = 'block';
           }

           reader.readAsDataURL(input.files[0]); // 파일을 읽어 Data URL로 변환
       }
   }