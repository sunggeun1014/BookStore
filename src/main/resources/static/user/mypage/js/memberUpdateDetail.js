window.onload = function() {
	document.getElementById("address_kakao").addEventListener("click", function() {
    	new daum.Postcode({
        	oncomplete: function(data) {
            	document.getElementById("address_kakao").value = data.address;
            	document.querySelector("input[name=address_detail]").focus();
            }
        }).open();
    });
		   
	document.getElementById("userPart1").addEventListener("input", function() {
		limitLength(this, 4); // userPart1 필드는 최대 4자리 숫자
	});
	
	document.getElementById("userPart2").addEventListener("input", function() {
		limitLength(this, 4); // userPart2 필드는 최대 4자리 숫자
	});
		   
		   
	function limitLength(input, maxLength) {
		if (input.value.length > maxLength) {
			input.value = input.value.slice(0, maxLength);
		}
	}
	
	document.querySelectorAll('.input-box input').forEach(function(input) {
		input.addEventListener('focus', function() {
			this.value = '';
		});
	});
		
	   
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
			  
		
	
	document.getElementById("input-pw-check").addEventListener('focus', function() {
		const errorElement = document.getElementById("pwError");
		if (errorElement.innerText !== "※ 특수문자, 영문자, 숫자 포함, 최소 8자") {
			errorElement.textContent = "※ 특수문자, 영문자, 숫자 포함, 최소 8자";
		}
	});
	
	document.getElementById("userPart1").addEventListener('focus', function () {
		const errorElement = document.getElementById("phoneError");
		errorElement.style.display = "none";  // 에러 메시지를 숨김
	});
	
	document.getElementById("emailUser").addEventListener('focus', function () {
		const errorElement = document.getElementById("emailError");
		errorElement.style.display = "none";  // 에러 메시지를 숨김
	});
	
	function validateForm() {
		let isValid = true;
		// 비밀번호 검증
		const inputPw = document.getElementById("input-pw").value.trim();
		const inputPwCheck = document.getElementById("input-pw-check").value.trim();
		const pwError = document.getElementById("pwError");
		   
		if (!(inputPw === inputPwCheck)) {
			pwError.textContent = "비밀번호가 일치하지 않습니다.";
			isValid = false;
				
			return isValid;
		}
		   
		if (inputPwCheck && !validatePassword(inputPwCheck)) {
			pwError.textContent = "비밀번호 양식에 맞춰 작성해주세요"
				
			isValid = false;
				
			return isValid;
		}
		   
		   
		   
	   // 이메일 검증
	   const emailUser = document.getElementById("emailUser").value.trim();
	   const emailDomainSelect = document.getElementById("emailDomain").value;
	   const emailError = document.getElementById("emailError");
	  
	   if (!validateEmail(emailUser)) {
			emailError.style.display = "block";
			emailError.textContent = "영문자와 숫자만 입력하세요"
			isValid = false;
			
			return isValid;
	   }
	   
	   if ((emailUser !== "" && !emailUser)) {
			if (emailDomainSelect === "" && !emailDomainSelect) {
	           emailError.style.display = "block";
	           emailError.textContent = "이메일을 모두 입력해 주세요.";
	           isValid = false;
		
			   return isValid;		
			}
	   }
	   
	
	   // 전화번호 검증
	   const countryNum = document.getElementById("countryNum").value;
	   const userPart1 = document.getElementById("userPart1").value.trim();
	   const userPart2 = document.getElementById("userPart2").value.trim();
	   const phoneError = document.getElementById("phoneError");
	
	   if ((countryNum && (!userPart1 || !userPart2)) || ((!countryNum || countryNum === "") && (userPart1 || userPart2))) {
	       phoneError.style.display = "block";
	       phoneError.textContent = "전화번호를 올바르게 입력해 주세요.";
	       isValid = false;
	   	   
		   
		   return isValid;
	   }
	
	   return isValid; // 폼 제출을 허용하거나 중지
	}
	
	
	function prepareSubmit() {
	   // 이메일 조합
	   const emailUser = document.getElementById("emailUser").value.trim();
	   const emailDomainSelect = document.getElementById("emailDomain").value;
	   const fullEmail = emailUser && emailDomain ? `${emailUser}@${emailDomainSelect}` : "";
		
	   const address = document.getElementById("address_kakao").value.trim();
	   const addressDetail = document.getElementById("address_detail").value.trim();
	  
	
	   // 전화번호 조합
	   const countryNum = document.getElementById("countryNum").value;
	   const userPart1 = document.getElementById("userPart1").value.trim();
	   const userPart2 = document.getElementById("userPart2").value.trim();
	   const fullPhone = countryNum && userPart1 && userPart2 ? `${countryNum}-${userPart1}-${userPart2}` : "";
		
	   // 폼 유효성 검사 실행
	   if (!validateForm()) {
	       return false;
	   }
	
	   // 서버로 AJAX 요청 전송
	   const data = {
	       member_email: fullEmail,
	       member_phoneNo: fullPhone,
	       member_pw: document.getElementById("input-pw").value.trim(),
	       member_addr: address,
	       member_detail_addr: addressDetail
	   };
	
	   fetch('/user/mypage/updatedata', {
	       method: 'POST',
	       headers: {
	           'Content-Type': 'application/json',
	       },
	       body: JSON.stringify(data)
	   })
	   .then(response => response.json())
	   .then(result => {
	       if (result.success) {
		   	   showSuccessModal('회원 정보가 수정되었습니다.')
			   return;
	       } else {
			   getErrorModal('수정에 실패했습니다 : ' + result.message);
			   return;
		   }
	   })
	   .catch(error => {
	       getErrorModal('오류가 발생했습니다.');
		   return;
	   });
	
	   return false; // 페이지 새로고침 방지
	}
	
	function showSuccessModal(msg) {
		getCheckModal(msg);

		$(document).on('click', '#confirm-delete', function() {
			window.location.href = '/user/mypage/updatePage';
		});
	}
	
	
}
	
	   
	   
