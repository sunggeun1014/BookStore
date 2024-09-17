// 카카오 주소 api
window.onload = function() {
    let addressInput = document.getElementById("address_kakao");

    if (addressInput) {
        addressInput.addEventListener("click", function() {
            new daum.Postcode({
                oncomplete: function(data) {
                    // 주소 입력 필드에 값 설정
                    addressInput.value = data.address;

                    // 상세주소 입력 필드에 포커스 주기 (약간의 지연 추가)
                    setTimeout(function() {
                        let detailAddressInput = document.getElementById("member_detail_addr");
                        if (detailAddressInput) {
                            detailAddressInput.focus();
                        }
                    }, 100);  // 100ms 지연
                }
            }).open(); // 팝업 창을 엽니다
        });
    }
};

Kakao.init('4f23d110021dacd702bf22df50d94c73'); // 여기서 YOUR_KAKAO_APP_KEY는 카카오에서 발급받은 앱 키로 대체하세요
console.log(Kakao.isInitialized()); // SDK 초기화가 제대로 되었는지 확인 (true가 나와야 함)

document.getElementById('kakao_login_btn').addEventListener('click', function() {
	event.preventDefault();  // 폼 전송 방지

    Kakao.Auth.login({
        success: function(authObj) {
            // 카카오 API 호출하여 사용자 정보 가져오기
            Kakao.API.request({
                url: '/v2/user/me',
                success: function(response) {
                    const kakaoId = response.id; // 카카오에서 받은 고유 ID
                    const email = response.kakao_account.email; // 카카오에서 받은 이메일

                    // 숨겨진 input 필드에 kakao ID 저장
                    document.getElementById('kakao_login_cd').value = kakaoId;

                    // 값 확인용 log
                    console.log('카카오 로그인 성공! ID:', kakaoId, '이메일:', email);
                    
                    let kakaoCheckbox = document.querySelector('.check-box.kakao');
                    if (kakaoCheckbox) {
                        kakaoCheckbox.checked = true; 
                    }

                },
                fail: function(error) {
                    console.log(error);
                }
            });
        },
        fail: function(err) {
            console.log(err);
        }
    });
});

let naverLogin = new naver.LoginWithNaverId({
    clientId: 'sutMcxM8vDfiUuQgTc15',  // 네이버에서 발급받은 클라이언트 ID
    callbackUrl: 'http://localhost:9080/user/members/join',  // 네이버에서 설정한 콜백 URL
    isPopup: true,  // 팝업 모드 사용
    loginButton: { color: 'green', type: 5, height: 45 }  // 로그인 버튼 스타일 설정
});

// 네이버 로그인 초기화
naverLogin.init();

// 로그인 성공 시 상태 확인 후 ID와 이메일 받아오기
naverLogin.getLoginStatus(function(status) {
    if (status) {
        let naverId = naverLogin.user.getId();  // 네이버 유저의 고유 ID
        let email = naverLogin.user.getEmail();  // 네이버 유저의 이메일

        // 값확인용 lgo
        console.log('네이버 로그인 성공! ID:', naverId, '이메일:', email);

        if (window.opener) {
            window.opener.document.getElementById('naver_login_cd').value = naverId;  
            let naverCheckbox = window.opener.document.querySelector('.check-box.naver');
            if (naverCheckbox) {
                naverCheckbox.checked = true;  
            }
            window.opener.console.log('부모 창으로 전달된 네이버 ID:', naverId);
        }

        window.close(); 
    } else {
        console.log('네이버 로그인 실패');
    }
});

$(document).ready(function() {
    let idCheckPassed = false;

    // 아이디 유효성 검사 함수(영문자와 숫자, 최소 5자)
    function validateId(id) {
        let idPattern = /^[a-zA-Z0-9]{5,}$/; 
        return idPattern.test(id);
    }

    // 비밀번호 유효성 검사 함수(특수문자 영어 숫자 조합 8자리 이상)
    function validatePassword(password) {
        let pwPattern = /^(?=.*[a-zA-Z])(?=.*\d)(?=.*[!@#$%^&*])[A-Za-z\d!@#$%^&*]{8,}$/;
        return pwPattern.test(password);
    }

    // 이메일 유효성 검사 함수
    function validateEmail(email) {
        let emailPattern = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.(com|net|org|edu|gov|mil|co\.kr|ac\.kr|biz|info)$/;
        return emailPattern.test(email);
    }
    // 전화번호 유효성 검사(010-XXXX-XXXX 형식만)
    function validatePhoneNumber(phoneNumber) {
	    let phonePattern = /^010-\d{4}-\d{4}$/;  
	    return phonePattern.test(phoneNumber);  
	}

    // 전화번호 자동 포맷팅 함수
    function formatPhoneNumber(phoneNumber) {
        phoneNumber = phoneNumber.replace(/[^0-9]/g, '');  // 숫자 외에 다른 문자 제거
        if (phoneNumber.length === 11) {
            return phoneNumber.replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3');
        } else if (phoneNumber.length === 10) {
            return phoneNumber.replace(/(\d{2})(\d{4})(\d{4})/, '$1-$2-$3');
        }
        return phoneNumber;
    }

    // 전화번호 입력 시 실시간으로 하이픈 추가
    $('#member_phoneNo').on('input', function() {
        this.value = formatPhoneNumber(this.value);
    });
    
    $('#member_id').on('change', function() {
        let member_id = $('#member_id').val();

        // ID 유효성 검사 먼저 수행
        if (!validateId(member_id)) {
            $('#idCheckForm').show();
            idCheckPassed = false;
            return;  // 유효하지 않은 경우 Ajax 요청 중단
        } else {
            $('#idCheckForm').hide();
        }

        // 유효성 검사가 통과되면 Ajax 요청
        $.ajax({
            url: '/user/members/checkId',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({ "member_id": member_id }),
            success: function(response) {
                if(response.isAvailable) {
                    $('#idCheckForm').hide();
					$('.form-item.id').removeClass('warning').css({
					    "border-color": "#C0C0C0", // 경계선 색상을 빨간색으로 변경
					    "z-index": "900",         // z-index를 높여서 앞으로 가져오기
					    "color": "#C0C0C0"
					}).addClass("nomal");
					$('#member_id').css({
					    "color": "#7E7E7E" 
					});
                    idCheckPassed = true; 
                } else {
                    // ID 중복
                    $('.form-item.id').removeClass('nomal').css({
					    "border-color": "#E54F53", // 경계선 색상을 빨간색으로 변경
					    "z-index": "1000",         // z-index를 높여서 앞으로 가져오기
					    "color": "#E54F53"
					}).addClass("warning");
					$('#member_id').css({
					    "color": "#E54F53"         // 입력 필드 안의 텍스트 색상을 빨간색으로 변경
					});
                    $('#idCheckForm').text('⚠아이디: 사용할 수 없는 아이디입니다. 다른 아이디를 입력해 주세요.').show();
                    
                    idCheckPassed = false; 
                }
            }
        });
    });

    // 각 필드에서 포인터가 떠날 때 유효성 검사 및 오류 메시지 숨기기
    $('#member_id').on('blur', function() {
        let member_Id = $(this).val();
        if (validateId(member_Id)) {
            $('#idCheckForm').hide();  
            $('.form-item.id').removeClass('warning').css({
			    "border-color": "#C0C0C0", // 경계선 색상을 빨간색으로 변경
			    "z-index": "900",         // z-index를 높여서 앞으로 가져오기
			    "color": "#C0C0C0"
			}).addClass("nomal");
			$('#member_id').removeClass('warning').addClass('nomal').css({
        	    "color": "#7E7E7E"         // 입력 필드 텍스트 색상 변경
        	});
        } else {
			$('.form-item.id').removeClass('nomal').css({
			    "border-color": "#E54F53",
			    "z-index": "1000",         
			    "color": "#E54F53"
			}).addClass("warning");
			$('#member_id').removeClass('nomal').addClass('warning').css({
	            "color": "#E54F53"         // 입력 필드 텍스트 색상 변경
	        });
			
			$('#idCheckForm').text('⚠아이디: 사용할 수 없는 아이디입니다. 영문자와 숫자, 최소 5자 이상').show();  
		}
    });

    $('#member_pw_check').on('blur', function() {
	    let password = $('#member_pw').val();
	    let confirmPassword = $(this).val();
	
	    // Validate password format first
	    if (!validatePassword(password)) {
			$('.form-item.pw').removeClass('nomal').css({
			    "border-color": "#E54F53", // 경계선 색상을 빨간색으로 변경
			    "z-index": "1000",         // z-index를 높여서 앞으로 가져오기
			    "color": "#E54F53"
			}).addClass("warning");
			$('#member_pw').removeClass('nomal').addClass('warning').css({
        	    "color": "#E54F53"         // 입력 필드 텍스트 색상 변경
        	});
        	
			$('.form-item.confirm-pw').removeClass('nomal').css({
			    "border-color": "#E54F53", // 경계선 색상을 빨간색으로 변경
			    "z-index": "1000",         // z-index를 높여서 앞으로 가져오기
			    "color": "#E54F53"
			}).addClass("warning");
			$('#member_pw_check').removeClass('nomal').addClass('warning').css({
        	    "color": "#E54F53"         // 입력 필드 텍스트 색상 변경
        	});
        	
	        $('#pwCheckForm').text('⚠비밀번호 : 특수문자, 영문자, 숫자 포함, 최소 8자').show(); 
	        
	    } else if (password !== confirmPassword) {
			$('.form-item.confirm-pw').removeClass('nomal').css({
			    "border-color": "#E54F53", // 경계선 색상을 빨간색으로 변경
			    "z-index": "1000",         // z-index를 높여서 앞으로 가져오기
			    "color": "#E54F53"
			}).addClass("warning");
			$('#member_pw_check').removeClass('nomal').addClass('warning').css({
        	    "color": "#E54F53"         // 입력 필드 텍스트 색상 변경
        	});
        	
	        $('#pwCheckForm').text('⚠비밀번호 : 비밀번호가 일치하지 않습니다.').show();  
	    } else {
			$('.form-item.pw').removeClass('warning').css({
			    "border-color": "#C0C0C0", // 경계선 색상을 빨간색으로 변경
			    "z-index": "900",         // z-index를 높여서 앞으로 가져오기
			    "color": "#C0C0C0"
			}).addClass("nomal");
			$('#member_pw').removeClass('warning').addClass('nomal').css({
        	    "color": "#C0C0C0"         // 입력 필드 텍스트 색상 변경
        	});
        	
			$('.form-item.confirm-pw').removeClass('warning').css({
			    "border-color": "#C0C0C0", // 경계선 색상을 빨간색으로 변경
			    "z-index": "900",         // z-index를 높여서 앞으로 가져오기
			    "color": "#C0C0C0"
			}).addClass("nomal");
			$('#member_pw_check').removeClass('warning').addClass('nomal').css({
        	    "color": "#C0C0C0"         // 입력 필드 텍스트 색상 변경
        	});
        	
	        $('#pwCheckForm').hide();
	    }
	});
	
	
	$('#member_name').on('blur', function() {
		let member_name = $(this).val();
		if(member_name.length > 0) {
			$('#nameCheck').hide();
			$('.form-item.name').removeClass('warning').css({
			    "border-color": "#C0C0C0", // 경계선 색상을 빨간색으로 변경
			    "z-index": "900",         // z-index를 높여서 앞으로 가져오기
			    "color": "#C0C0C0"
			}).addClass("nomal");
			$('#member_name').removeClass('warning').addClass('nomal').css({
        	    "color": "#C0C0C0"         // 입력 필드 텍스트 색상 변경
        	});
        	
		} else {
			$('#nameCheck').text('⚠이름: 필수 정보입니다.').show();
			$('.form-item.name').removeClass('nomal').css({
			    "border-color": "#E54F53", // 경계선 색상을 빨간색으로 변경
			    "z-index": "1000",         // z-index를 높여서 앞으로 가져오기
			    "color": "#E54F53"
			}).addClass("warning");
			$('#member_name').removeClass('nomal').addClass('warning').css({
        	    "color": "#E54F53"         // 입력 필드 텍스트 색상 변경
        	});
		}
	});

    $('#member_email').on('input', function() {
        let member_email = $(this).val();
        if (validateEmail(member_email)) {
	        $('#emailCheck').hide();
	       	$('.form-item.email').removeClass('warning').css({
			    "border-color": "#C0C0C0", // 경계선 색상을 빨간색으로 변경
			    "z-index": "900",         // z-index를 높여서 앞으로 가져오기
			    "color": "#C0C0C0"
			}).addClass("nomal");
			$('#member_email').removeClass('warning').addClass('nomal').css({
        	    "color": "#C0C0C0"         // 입력 필드 텍스트 색상 변경
        	});
        	$('#sendBtn').css({"background-color": "#845EC2"}).prop("disabled", false);
	    } else {
	        $('#emailCheck').text("⚠이메일:이메일을 확인해 주세요.").show();
	        $('.form-item.email').removeClass('nomal').css({
			    "border-color": "#E54F53", // 경계선 색상을 빨간색으로 변경
			    "z-index": "1000",         // z-index를 높여서 앞으로 가져오기
			    "color": "#E54F53"
			}).addClass("warning");
			$('#member_email').removeClass('nomal').addClass('warning').css({
        	    "color": "#E54F53"         // 입력 필드 텍스트 색상 변경
        	});
        	$('#sendBtn').css({"background-color": "#CCCCCC"}).prop("disabled", true);

	        return;

	    }
    });
    
    $('#member_phoneNo').on('input', function() {
        let phoneNo = $(this).val();
        if (validatePhoneNumber(phoneNo)) {
            $('#phoneNumCheck').hide();  // 전화번호가 입력되었으면 오류 메시지를 숨김
            $('.form-item.phoneNo').removeClass('warning').css({
			    "border-color": "#C0C0C0", // 경계선 색상을 빨간색으로 변경
			    "z-index": "900",         // z-index를 높여서 앞으로 가져오기
			    "color": "#C0C0C0"
			}).addClass("nomal");
			$('#member_phoneNo').removeClass('warning').addClass('nomal').css({
        	    "color": "#C0C0C0"         // 입력 필드 텍스트 색상 변경
        	});
        } else {
			$('#phoneNumCheck').text('⚠휴대전화번호: 휴대전화 정보를 확인해 주세요.').show();
			$('.form-item.phoneNo').removeClass('nomal').css({
			    "border-color": "#E54F53", // 경계선 색상을 빨간색으로 변경
			    "z-index": "1000",         // z-index를 높여서 앞으로 가져오기
			    "color": "#E54F53"
			}).addClass("warning");
			$('#member_phoneNo').removeClass('nomal').addClass('warning').css({
        	    "color": "#E54F53"         // 입력 필드 텍스트 색상 변경
        	});
		}
    });

    $('#address_kakao').on('blur', function() {
        let member_addr = $(this).val();
        if (member_addr.length > 0) {
            $('#addrCheck').hide();  // 주소가 입력되었으면 오류 메시지를 숨김
            $('.form-item.addr').removeClass('warning').css({
			    "border-color": "#C0C0C0", // 경계선 색상을 빨간색으로 변경
			    "z-index": "900",         // z-index를 높여서 앞으로 가져오기
			    "color": "#C0C0C0"
			}).addClass("nomal");
			$('#address_kakao').removeClass('warning').addClass('nomal').css({
        	    "color": "#C0C0C0"         // 입력 필드 텍스트 색상 변경
        	});
            
        }else {
			$('#addrCheck').text('⚠주소 : 필수 정보입니다 입력해주세요.').show();
			$('.form-item.addr').removeClass('nomal').css({
			    "border-color": "#E54F53", // 경계선 색상을 빨간색으로 변경
			    "z-index": "1000",         // z-index를 높여서 앞으로 가져오기
			    "color": "#E54F53"
			}).addClass("warning");
			$('#address_kakao').removeClass('nomal').addClass('warning').css({
        	    "color": "#E54F53"         // 입력 필드 텍스트 색상 변경
        	});
		}
        
    });

    $('#member_detail_addr').on('blur', function() {
        let member_detail_addr = $(this).val();
        if (member_detail_addr.length > 0) {
            $('#detailAddrCheck').hide();  // 상세주소가 입력되었으면 오류 메시지를 숨김
            $('.form-item.detail-addr').removeClass('warning').css({
			    "border-color": "#C0C0C0", // 경계선 색상을 빨간색으로 변경
			    "z-index": "900",         // z-index를 높여서 앞으로 가져오기
			    "color": "#C0C0C0"
			}).addClass("nomal");
			$('#member_detail_addr').removeClass('warning').addClass('nomal').css({
        	    "color": "#C0C0C0"         // 입력 필드 텍스트 색상 변경
        	});
        } else {
			$('#detailAddrCheck').text('⚠상세주소 : 필수 정보입니다 입력해주세요.').show();
			$('.form-item.detail-addr').removeClass('nomal').css({
			    "border-color": "#E54F53", // 경계선 색상을 빨간색으로 변경
			    "z-index": "1000",         // z-index를 높여서 앞으로 가져오기
			    "color": "#E54F53"
			}).addClass("warning");
			$('#member_detail_addr').removeClass('nomal').addClass('warning').css({
        	    "color": "#E54F53"         // 입력 필드 텍스트 색상 변경
        	});
		}
    });
    
    

    // 폼 제출 시 모든 유효성 검사 실행
    $('#joinForm').on('submit', function(event) {
        let member_id = $('#member_id').val();
        let password = $('#member_pw').val();
        let confirmPassword = $('#member_pw_check').val();
        let member_name = $('#member_name').val();
        let member_phoneNo = $('#member_phoneNo').val();
        let member_addr = $('#address_kakao').val();
        let member_detail_addr = $('#member_detail_addr').val();
        let certifiedEmail = $('#emailVerificationStatus').val();

        // 각 필드의 유효성 검사
        if (!validateId(member_id)) {
            $('#idCheckForm').text('⚠아이디: 필수 정보입니다 입력해주세요.').show();
            $('.form-item.id').removeClass('nomal').css({
			    "border-color": "#E54F53",
			    "z-index": "1000",         
			    "color": "#E54F53"
			}).addClass("warning");
			$('#member_id').removeClass('nomal').addClass('warning').css({
	            "color": "#E54F53"         // 입력 필드 텍스트 색상 변경
	        });
            event.preventDefault();  // 폼 전송 방지
        }

        if (!validatePassword(password)) {
            $('#pwCheckForm').text('⚠비밀번호: 필수 정보입니다 입력해주세요.').show();
            $('.form-item.pw').removeClass('nomal').css({
			    "border-color": "#E54F53", // 경계선 색상을 빨간색으로 변경
			    "z-index": "1000",         // z-index를 높여서 앞으로 가져오기
			    "color": "#E54F53"
			}).addClass("warning");
			$('#member_pw').removeClass('nomal').addClass('warning').css({
        	    "color": "#E54F53"         // 입력 필드 텍스트 색상 변경
        	});
        	
			$('.form-item.confirm-pw').removeClass('nomal').css({
			    "border-color": "#E54F53", // 경계선 색상을 빨간색으로 변경
			    "z-index": "1000",         // z-index를 높여서 앞으로 가져오기
			    "color": "#E54F53"
			}).addClass("warning");
			$('#member_pw_check').removeClass('nomal').addClass('warning').css({
        	    "color": "#E54F53"         // 입력 필드 텍스트 색상 변경
        	});
            event.preventDefault();  // 폼 전송 방지
        }

        if (password !== confirmPassword) {
            $('#pwCheckMiss').show();
            $('.form-item.confirm-pw').removeClass('nomal').css({
			    "border-color": "#E54F53", // 경계선 색상을 빨간색으로 변경
			    "z-index": "1000",         // z-index를 높여서 앞으로 가져오기
			    "color": "#E54F53"
			}).addClass("warning");
			$('#member_pw_check').removeClass('nomal').addClass('warning').css({
        	    "color": "#E54F53"         // 입력 필드 텍스트 색상 변경
        	});
            event.preventDefault();  // 폼 전송 방지
        }
        
        if(member_name.length === 0) {
			$('#nameCheck').text('⚠이름: 필수 정보입니다 입력해주세요.').show();
			$('.form-item.name').removeClass('nomal').css({
			    "border-color": "#E54F53", // 경계선 색상을 빨간색으로 변경
			    "z-index": "1000",         // z-index를 높여서 앞으로 가져오기
			    "color": "#E54F53"
			}).addClass("warning");
			$('#member_name').removeClass('nomal').addClass('warning').css({
        	    "color": "#E54F53"         // 입력 필드 텍스트 색상 변경
        	});
			event.preventDefault();
		}

        if (member_phoneNo.length === 0 || !validatePhoneNumber(member_phoneNo)) {
            $('#phoneNumCheck').text('⚠휴대전화번호 : 필수 정보입니다 입력해주세요.').show();
            $('.form-item.phoneNo').removeClass('nomal').css({
			    "border-color": "#E54F53", // 경계선 색상을 빨간색으로 변경
			    "z-index": "1000",         // z-index를 높여서 앞으로 가져오기
			    "color": "#E54F53"
			}).addClass("warning");
			$('#member_phoneNo').removeClass('nomal').addClass('warning').css({
        	    "color": "#E54F53"         // 입력 필드 텍스트 색상 변경
        	});
            event.preventDefault();  // 폼 전송 방지
        }

        if (member_addr.length === 0) {
            $('#addrCheck').text('⚠주소 : 필수 정보입니다 입력해주세요.').show();
			$('.form-item.addr').removeClass('nomal').css({
			    "border-color": "#E54F53", // 경계선 색상을 빨간색으로 변경
			    "z-index": "1000",         // z-index를 높여서 앞으로 가져오기
			    "color": "#E54F53"
			}).addClass("warning");
			$('#address_kakao').removeClass('nomal').addClass('warning').css({
        	    "color": "#E54F53"         // 입력 필드 텍스트 색상 변경
        	});
            event.preventDefault();  // 폼 전송 방지
        }

        if (member_detail_addr.length === 0) {
            $('#detailAddrCheck').text('⚠상세주소 : 필수 정보입니다 입력해주세요.').show();
			$('.form-item.detail-addr').removeClass('nomal').css({
			    "border-color": "#E54F53", // 경계선 색상을 빨간색으로 변경
			    "z-index": "1000",         // z-index를 높여서 앞으로 가져오기
			    "color": "#E54F53"
			}).addClass("warning");
			$('#member_detail_addr').removeClass('nomal').addClass('warning').css({
        	    "color": "#E54F53"         // 입력 필드 텍스트 색상 변경
        	});
            event.preventDefault();  // 폼 전송 방지
        }

        if (!certifiedEmail) {
            $('#emailCheck').text('⚠이메일 : 이메일 인증을 진행해 주세요.').show();
            $('.form-item.email').removeClass('nomal').css({
			    "border-color": "#E54F53", // 경계선 색상을 빨간색으로 변경
			    "z-index": "1000",         // z-index를 높여서 앞으로 가져오기
			    "color": "#E54F53"
			}).addClass("warning");
			$('#member_email').removeClass('nomal').addClass('warning').css({
        	    "color": "#E54F53"         // 입력 필드 텍스트 색상 변경
        	});
        	$('.form-item.email-confirm').removeClass('nomal').css({
			    "border-color": "#E54F53", // 경계선 색상을 빨간색으로 변경
			    "z-index": "1000",         // z-index를 높여서 앞으로 가져오기
			    "color": "#E54F53"
			}).addClass("warning");
			$('#number').removeClass('nomal').addClass('warning').css({
        	    "color": "#E54F53"         // 입력 필드 텍스트 색상 변경
        	});
            event.preventDefault();  // 폼 전송 방지
        }
    });
});


// 인증번호 발송 요청
function sendNumber() {	
	const email = $('#member_email').val()
    $("#divConfirmEmail").css({
	  "display": "flex",
	  "flex-direction": "row"
	});

    $.ajax({
        url: "/user/members/verify",  // 인증번호 발송 요청
        type: "post",
        dataType: "json",
        contentType: "application/json",  // JSON 데이터 전송
        data: JSON.stringify({"to": email}),  // EmailDTO의 'to' 필드에 매핑
        success: function (data) {
            getCheckModal("인증번호가 발송되었습니다.");
            $('.default-btn.short').css({
                "background-color": "white",  // 버튼 배경색을 회색으로 변경
                "border" : "1px solid #845EC2",
                "color": "#845EC2",
                "width": "180px"              // 버튼 텍스트 색상 변경
            }).prop("disabled", false).text("인증번호 재발송"); // 버튼 비활성화
        }
    });
}

// 인증번호 확인 요청
function confirmNumber() {
	const email = $('#member_email').val()
    let number = $("#number").val();

    $.ajax({
        url: "/user/members/verify/check",  // 인증번호 확인 요청
        type: "post",
        dataType: "json",
        contentType: "application/json",  // JSON 데이터 전송
        data: JSON.stringify({"to": email, "verifyCode": number}),  // 인증번호와 이메일을 함께 보냄
        success: function (data, status, xhr) {
			$("#emailVerificationStatus").val("success");
			$('#certifiedEmailSuccess').text(data.message);
            $('#certifiedEmailSuccess').show();
            $('#certifiedEmailFail').hide();
            $('.form-item.email-confirm').removeClass('warning').css({
			    "border-color": "#C0C0C0", // 경계선 색상을 빨간색으로 변경
			    "z-index": "900",         // z-index를 높여서 앞으로 가져오기
			    "color": "#C0C0C0"
			}).addClass("nomal");
			$('#number').removeClass('warning').addClass('nomal').css({
        	    "color": "#C0C0C0"         // 입력 필드 텍스트 색상 변경
        	});
        },
        error: function(xhr, status, error) {
            console.log("Error: " + error);
            $("#emailVerificationStatus").val("");
            $('#certifiedEmailFail').text("⚠인증에 실패하였습니다.");
            $('#certifiedEmailSuccess').hide();
            $('#certifiedEmailFail').show();
            
        }
    });
}
