// 함수실행
/*activeButton();
filterTextActive();
dateFilterActive();
calcQty();
tabBarActive();*/

// 인풋에 밸류 들어가면 버튼 활성화 (로그인, 회원가입때)
function activeButton() {
    const loginInput = document.querySelector(".input-long");
    const longBtn = document.querySelector(".default-btn.long");
    loginInput.addEventListener("keyup", (e) => {
        longBtn.disabled = loginInput.value === "";
    });
}

// 검색페이지 필터 조건 클릭효과
function filterTextActive() {
    const textBtn = document.querySelectorAll(".filter-text-btn");

    textBtn.forEach((btn) => {
        btn.addEventListener("click", (e) => {
            btn.classList.toggle("on");
        });
    });
}

// 주문번호 검색 최근1개월 버튼클릭 효과
function dateFilterActive() {
    const latestDate = document.querySelectorAll(".latest-date");

    latestDate.forEach((btn) =>
        btn.addEventListener("click", () => {
            latestDate.forEach((otherBtn) => otherBtn.classList.remove("active"));
            btn.classList.add("active");
        })
    );
}

// 인풋 넘버 숫자 (상품 상세페이지 주문수량 인풋)
function calcQty() {
    const inputQty = document.querySelector(".input-qty");
    const minus = document.querySelector(".fa-minus");
    const plus = document.querySelector(".fa-plus");

    let i = parseInt(inputQty.value) || 0;

    // 마이너스 버튼 클릭 시
    minus.addEventListener("click", () => {
        if (i > 1) {
            i--;
            inputQty.value = i;
        } else {
            alert("1보다 작을 수 없습니다."); // 모달로 알림 넣으면 됨
        }
    });

    // 플러스 버튼 클릭 시
    plus.addEventListener("click", () => {
        i++;
        inputQty.value = i;
    });
}

// 마이페이지 (리뷰, 1:1문의 등 탭 버튼)
function tabBarActive() {
    const tabBtn = document.querySelectorAll(".tab-btn");

    tabBtn.forEach((btn) => {
        btn.addEventListener("click", () => {
            // 모든 버튼에서 active 클래스 제거
            tabBtn.forEach((otherBtn) => {
                if (otherBtn !== btn) {
                    otherBtn.classList.remove("active");
                }
            });

            // 클릭된 버튼에 active 클래스 추가
            btn.classList.add("active");
        });
    });
}