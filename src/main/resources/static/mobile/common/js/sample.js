linkTabbar();

function linkTabbar() {
    const menuItems = document.querySelectorAll(".menu-item");

    menuItems.forEach((menu) => {
        menu.addEventListener("click", (e) => {
            const tabIcon = menu.querySelector(".tab-icon");
            const tabText = menu.querySelector(".menu-item > p");

            if (tabIcon) tabIcon.classList.add("on");
            if (tabText) tabText.classList.add("on");

            menuItems.forEach((other) => {
                if (other !== menu) {
                    const tabIcon = other.querySelector(".tab-icon");
                    const tabText = other.querySelector(".menu-item > p");

                    if (tabIcon) tabIcon.classList.remove("on");
                    if (tabText) tabText.classList.remove("on");
                }
            });
        });
    });
}
function getConfirmModal(msg, func) {
    let divArea = $("<div id='myModal' class='modal' style='display : block;'></div>");
    let contentArea = $("<div class='modal-content'></div>");

    let messageArea = $(`<div class='modal-text'><p>${msg}</p></div>`);
    let modalFotter = $(`<div class='modal-footer'></div>`)
    let btnArea = $("<button id='confirm-delete' class='modal-btn confirm'>확인</button><button id='cancel-delete' class='modal-btn cancel'>취소</button>");

    contentArea.append(messageArea);

    contentArea.append(modalFotter);
    modalFotter.append(btnArea);
    divArea.append(contentArea);


    $("body").append(divArea);
    $("#confirm-delete").on("click", function() {
        func();
        divArea.remove(); // 모달 제거
    });

    // 취소 버튼 클릭 이벤트
    $("#cancel-delete").on("click", function() {
        divArea.remove(); // 모달 제거
    });

    $("#confirm-delete").focus();
}

function getCheckModal(msg, focusElement) {
    let divArea = $("<div id='myModal' class='modal' style='display: block;'></div>"); // 잘못된 따옴표 수정
    let contentArea = $("<div class='modal-content'></div>");

    let messageArea = $(`<div class='modal-text'><p>${msg}</p></div>`);
    let modalFotter = $("<div class='modal-footer'></div>");  // 문자열 수정
    let btnArea = $("<button id='confirm-delete' class='modal-btn confirm'>확인</button>");

    contentArea.append(messageArea);
    contentArea.append(modalFotter);

    modalFotter.append(btnArea);
    divArea.append(contentArea);

    $("body").append(divArea);
    $(".modal-btn.confirm").on("click", function() {
        divArea.remove(); // 모달 제거
        if(focusElement) {
            focusElement.focus();
        }
    });

    $("#confirm-delete").focus();
}

function getErrorModal(focusElement) {
    let divArea = $("<div id='myModal' class='modal' style='display: block;'></div>"); // 잘못된 따옴표 수정
    let contentArea = $("<div class='modal-content'></div>");

    let messageArea = $(`<div class='modal-text error-text'><i class="fa-solid fa-triangle-exclamation"></i><p>오류가 발생했습니다.</p></div>`);
    let modalFotter = $("<div class='modal-footer'></div>");
    let btnArea = $("<button id='confirm-delete' class='modal-btn confirm error'>확인</button>");

    contentArea.append(messageArea);
    contentArea.append(modalFotter);

    modalFotter.append(btnArea);
    divArea.append(contentArea);

    $("body").append(divArea);
    $(".modal-btn.confirm").on("click", function() {
        divArea.remove(); // 모달 제거
        if(focusElement) {
            focusElement.focus();
        }
    });

    $("#confirm-delete").focus();
}