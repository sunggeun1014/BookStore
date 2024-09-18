function agreeClickHandler() {
    const agreeContent = document.querySelectorAll('.agree-content');

    agreeContent.forEach(content => {
        content.addEventListener('click', () => {
            agreeContent.forEach(otherContent =>
                otherContent !== content && otherContent.classList.remove('active')
            );
            content.classList.toggle('active');
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

function showEditAddrModal () {
    const editBtn = document.querySelector("#edit-btn")
    const closeBtn = document.querySelector("#close-btn")
    const addrModal = document.querySelector("#edit-addr-modal")
    const outside = document.querySelector("#payment-section")
    const outside2 = document.querySelector("#deliver-info")
    const confirmBtn = document.getElementById("confirm-btn")

    editBtn.addEventListener('click', function() {
        addrModal.classList.add("on")
    })

    closeBtn.addEventListener("click", function () {
        addrModal.classList.remove("on")
    })

    confirmBtn.addEventListener("click", function () {
        addrModal.classList.remove("on")
    })

    window.addEventListener('click', function (e) {
        if (e.target === outside || e.target === outside2) {
            addrModal.classList.remove("on")
        }
    })
}

const hyphenTel = (target) => {
    target.value = target.value
        .replace(/[^0-9]/g, '')
        .replace(/^(\d{2,3})(\d{3,4})(\d{4})$/, `$1-$2-$3`);
}

function inputAddr() {
    const editAddInput = document.getElementById("edit-addr");
    const editDetailAddrInput = document.getElementById("edit-addr-detail");

    const searchBtn = document.getElementById("search-btn")
    const addrBox = document.querySelector(".addr-input-box")

    function openAddrPopUP() {
        new daum.Postcode({
            oncomplete: function(data) {
                // 주소 입력 필드에 값 설정
                editAddInput.value = data.address;

                // 상세주소 입력 필드에 포커스 주기 (약간의 지연 추가)
                setTimeout(function() {
                    if (editDetailAddrInput) {
                        editDetailAddrInput.focus();
                    }
                }, 100);  // 100ms 지연
            }
        }).open(); // 팝업 창을 엽니다
    }

    if (searchBtn) {
        searchBtn.addEventListener("click", openAddrPopUP)
    }

    if (addrBox) {
        addrBox.addEventListener("click", openAddrPopUP)
    }

    const confirmBtn = document.getElementById("confirm-btn")
    const deliverName = document.getElementById("deliver-name");
    const deliverNumber = document.getElementById("deliver-number");
    const deliverAddr = document.getElementById("deliver-addr");
    const deliverAddrDe = document.getElementById("deliver-addr-detail")

    const nameInput = document.getElementById("edit-name")
    const numberInput = document.getElementById("edit-number");

    confirmBtn.addEventListener("click", function () {
        const newName = nameInput.value;
        const newNumber = numberInput.value;
        const newAddr = editAddInput.value;
        const newAddrDetail = editDetailAddrInput.value;

        deliverName.textContent = newName;
        deliverNumber.textContent = newNumber;
        deliverAddr.textContent = newAddr;
        deliverAddrDe.textContent = newAddrDetail
    })
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
    console.log('로그인? ', isLogin);
    console.log('로그인? ', typeof isLogin);

    if (isLogin === "false") {
        getDataCheckModal();
    }
}

function getDataCheckModal() {
    let divArea = $("<div id='myModal' class='modal' style='display: block;'></div>"); // 잘못된 따옴표 수정
    let contentArea = $("<div class='modal-content'></div>");

    let messageArea = $(`<div class='modal-text'><p>잘못된 접근 입니다.</p></div>`);
    let modalFotter = $("<div class='modal-footer'></div>");  // 문자열 수정
    let btnArea = $("<button id='confirm-delete' class='modal-btn confirm'>확인</button>");

    contentArea.append(messageArea);
    contentArea.append(modalFotter);

    modalFotter.append(btnArea);
    divArea.append(contentArea);

    $("body").append(divArea);
    $(".modal-btn.confirm").on("click", function() {
        location.href = '/user/login'
        divArea.remove(); // 모달 제거
    });

    $("#confirm-delete").focus();
}

document.addEventListener('DOMContentLoaded', function () {
    showDoorPWInput();
    agreeClickHandler();
    showEditAddrModal();
    inputAddr();
    toggleProductList();
});

window.onload = function () {
    showModal();
}
