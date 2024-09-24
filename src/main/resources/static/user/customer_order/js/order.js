function agreeClickHandler() {
    const agreeTitles = document.querySelectorAll('.agree-title');
    const agreeTexts = document.querySelectorAll('.agree-content');

    agreeTitles.forEach((title, index) => {
        title.addEventListener('click', function () {
            const text = agreeTexts[index]; // Get the corresponding text element
            const isActive = title.classList.contains('active');

            agreeTitles.forEach((otherTitle, i) => {
                otherTitle.classList.remove('active');
                agreeTexts[i].classList.remove('on');
            });

            if (!isActive) {
                title.classList.add('active');
                text.classList.add('on');
            }
        });
    });
}


function showDoorPWInput() {
    const doorPW = document.querySelector("#door-pw");
    const doorFree = document.querySelector("#door-free");
    const doorPwInput = document.querySelector(".input-wrap");

    doorPW.addEventListener('click', function () {
        if (doorPW.checked) {
            doorPwInput.style.display = 'block';
        }
    });

    doorFree.addEventListener('click', function () {
        if (doorFree.checked) {
            doorPwInput.style.display = 'none';
        }
    });
}

const hyphenTel = (target) => {
    target.value = target.value
        .replace(/[^0-9]/g, '')
        .replace(/^(\d{2,3})(\d{3,4})(\d{4})$/, `$1-$2-$3`);
}

function showEditAddrModal() {
    const editBtn = document.querySelector("#edit-btn");
    const closeBtn = document.querySelector("#close-btn");
    const addrModal = document.querySelector("#edit-addr-modal");
    const confirmBtn = document.getElementById("confirm-btn");

    // 주소 입력 관련 요소
    const editAddInput = document.getElementById("edit-addr");
    const editDetailAddrInput = document.getElementById("edit-addr-detail");
    const searchBtn = document.getElementById("search-btn");
    const addrBox = document.querySelector(".addr-input-box");
    const deliverName = document.getElementById("deliver-name");
    const deliverNumber = document.getElementById("deliver-number");
    const deliverAddr = document.getElementById("deliver-addr");
    const deliverAddrDe = document.getElementById("deliver-addr-detail");
    const nameInput = document.getElementById("edit-name");
    const numberInput = document.getElementById("edit-number");
    const errorMsg = document.querySelector(".edit-error");

    editBtn.addEventListener('click', function () {
        addrModal.classList.add("on");
    });

    closeBtn.addEventListener("click", function () {
        addrModal.classList.remove("on");
    });

    // 주소 검색 팝업 열기
    function openAddrPopUP() {
        new daum.Postcode({
            oncomplete: function (data) {
                // 주소 입력 필드에 값 설정
                editAddInput.value = data.address;

                // 상세주소 입력 필드에 포커스 주기 (약간의 지연 추가)
                setTimeout(function () {
                    if (editDetailAddrInput) {
                        editDetailAddrInput.value = '';
                        editDetailAddrInput.focus();
                    }
                }, 100);  // 100ms 지연
            }
        }).open(); // 팝업 창을 엽니다
    }

    if (searchBtn) {
        searchBtn.addEventListener("click", openAddrPopUP);
    }

    if (addrBox) {
        addrBox.addEventListener("click", openAddrPopUP);
    }

    editDetailAddrInput.addEventListener("keyup", function () {
        editDetailAddrInput.classList.remove("error");
        errorMsg.textContent = '';
    })

    const consonantRegex = /^[ㄱ-ㅎ]*$/;
    const vowelRegex = /^[ㅏ-ㅣ]*$/;

    editDetailAddrInput.classList.remove("error");
    errorMsg.textContent = "";

    confirmBtn.addEventListener("click", function () {
        const detailAddrValue = editDetailAddrInput.value.trim();

        if (detailAddrValue === "") {
            editDetailAddrInput.classList.add("error");
            errorMsg.textContent = "⚠️ 상세주소를 입력하세요";
            return;
        }

        if (consonantRegex.test(detailAddrValue) || vowelRegex.test(detailAddrValue)) {
            editDetailAddrInput.classList.add("error");
            errorMsg.textContent = "⚠️ 유효한 주소를 입력하세요";
            return;
        }

        // 변경된 주소정보 업데이트
        const newName = nameInput.value;
        const newNumber = numberInput.value;
        const newAddr = editAddInput.value;
        const newAddrDetail = editDetailAddrInput.value;

        deliverName.textContent = newName;
        deliverNumber.textContent = newNumber;
        deliverAddr.textContent = newAddr;
        deliverAddrDe.textContent = newAddrDetail;

        addrModal.classList.remove("on");
    });
}

function toggleProductList() {
    const head = document.querySelector(".product-head")
    const list = document.querySelector(".list-wrap")
    const arrow = document.querySelector(".product-info-arrow")

    head.addEventListener("click", function () {
        list.classList.toggle("off")
        arrow.classList.toggle("off")
        head.classList.toggle("off")
    })
}

function showModal() {
    const memberCheck = document.getElementById("member-check")
    let isLogin = memberCheck.getAttribute("data-member")

    if (isLogin === "false") {
        getDataCheckModal("잘못된 접근입니다.", function () {
            location.href = '/user/login'
        });
    }
}

let lastDate = "";
let orderTurn = 0;
const date = new Date();
const year = date.getFullYear();
const month = String(date.getMonth() + 1).padStart(2, "0");
const day = String(date.getDate()).padStart(2, "0");
let orderDate = year + month + day;

function createTempOrderNum() {
    if (lastDate !== orderDate) {
        lastDate = orderDate;
        orderTurn = 0;
    }

    orderNum = `${orderDate}-${String(++orderTurn).padStart(4, "0")}`;

    return orderNum;
}

function goToOrder() {
    const payBtn = document.getElementById("pay-btn");
    const entPwInput = document.getElementById("common_ent_pw");
    const errorMsg = document.querySelector(".error-message")

    entPwInput.addEventListener('keyup', function () {
        entPwInput.classList.remove("error")
        errorMsg.style.display = 'none';
    })

    const tempOrderNum = createTempOrderNum();
    const deliverName = document.getElementById("deliver-name").textContent;
    const deliverNumber = document.getElementById("deliver-number").textContent;
    const addr = document.getElementById("deliver-addr").textContent;
    const addrDetail = document.getElementById("deliver-addr-detail").textContent;
    const totalAmount = parseInt(document.querySelector(".price > p:first-child").textContent.replace(/,/g, ''));
    const orderNameInput = document.getElementById("order_name")
    const orderName = orderNameInput.getAttribute("data-order-name")
    // const paymentId = `payment-${crypto.randomUUID()}`;
    const paymentId = `${Date.now()}_${totalAmount}`

    payBtn.addEventListener('click', function (e) {
        const entPwInputValue = entPwInput.value.trim();
        const isCommonDoor = document.getElementById("door-pw").checked;

        if (isCommonDoor && entPwInputValue === "") {
            entPwInput.classList.add("error")
            errorMsg.textContent = '⚠️ 공동현관 비밀번호를 입력하세요'
            return
        }
        const data = {
            paymentId: paymentId,
            totalAmount: totalAmount,
        };

        PortOne.requestPayment({
            storeId: "store-5d54b2b0-bf88-45dd-8265-7018895b8a38",
            channelKey: "channel-key-db630549-a619-4ab8-8739-466edb7307c6",
            paymentId: paymentId,
            orderName: orderName,
            totalAmount: totalAmount,
            payMethod: "CARD",
            currency: "CURRENCY_KRW"
        }).then(response => {
            if (response.code != null) {
                console.log('결제오류발생', response.message);
                return;
            }
            $.ajax({
                url: '/user/payment/request',
                method: 'POST',
                contentType: 'application/json',
                data: JSON.stringify(data),
                success: function (response) {
                    if (response === 200) {
                        console.log('결제 성공')
                    } else if (response === 400) {
                        console.log('요청오류')
                    } else if (response === 401) {
                        console.log('인증오류')
                    }
                },
                error: function () {
                    console.log('결제실패')
                }
            })
        }).catch(error => {
            console.log('결제 요청 중 오류:', error);
        });

    })
}

document.addEventListener('DOMContentLoaded', function () {
    showDoorPWInput();
    agreeClickHandler();
    showEditAddrModal();
    toggleProductList();
    goToOrder();
});

window.onload = function () {
    showModal();
}
