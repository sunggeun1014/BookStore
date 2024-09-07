// document.addEventListener("DOMContentLoaded", () => {
//     const menuItems = document.querySelectorAll('.menu-item');
//
//     // 메뉴 클릭 시 하위메뉴 열고 닫기
//     menuItems.forEach(item => {
//         item.addEventListener('click', () => {
//             const submenuID = item.getAttribute('data-submenu');
//             const submenu = document.getElementById(submenuID);
//
//             // 모든 서브메뉴 닫기
//             document.querySelectorAll('.sub-menu').forEach(sub => {
//                 if (sub !== submenu) {
//                     sub.style.display = "none";
//                     sub.closest('.menu-item').querySelector('.menu-content').classList.remove('open');
//                 }
//             });
//
//             // 현재 클릭한 메뉴 토글
//             if (submenu.style.display === "block") {
//                 submenu.style.display = "none";
//                 item.querySelector('.menu-content').classList.remove('open');
//             } else {
//                 submenu.style.display = "block";
//                 item.querySelector('.menu-content').classList.add('open');
//             }
//         });
//     });
//
//     // 페이지 로드 시 현재 페이지와 매칭되는 하위메뉴 열기 및 강조
//     const currentPage = window.location.pathname.split("/").pop();
//     const menuLinks = document.querySelectorAll('.submenu-link');
//
//     menuLinks.forEach(link => {
//         const href = link.getAttribute('href').split("/").pop();
//         if (currentPage === href) {
//             link.classList.add('open');
//             const submenu = link.closest('.sub-menu');
//             submenu.style.display = "block";
//             submenu.closest('.menu-item').querySelector('.menu-content').classList.add('open');
//         }
//     });
// });


document.addEventListener("DOMContentLoaded", function() {
    const menuItems = document.querySelectorAll(".menu-item");

    // 로컬 스토리지에서 메뉴 상태를 불러와 복원
    menuItems.forEach((item, index) => {
        const subMenu = item.querySelector(".sub-menu");
        const barImg = item.querySelector(".bar-img");
        const arrowRight = item.querySelector(".fa-chevron-right");
        const iconImg = item.querySelector(".icon-img");
        const listText = item.querySelector(".menu-text > p");

        const isOpen = localStorage.getItem(`menu-open-${index}`) === "true";

        if (isOpen) {
            if (subMenu) subMenu.classList.add("open");
            if (barImg) barImg.classList.add("open");
            if (arrowRight) arrowRight.classList.add("open");
            if (iconImg) iconImg.classList.add("open");
            if (listText) listText.classList.add("open");
        }
    });

    menuItems.forEach((item, index) => {
        item.addEventListener("click", function(e) {
            const subMenu = this.querySelector(".sub-menu");
            const barImg = this.querySelector(".bar-img");
            const arrowRight = this.querySelector(".fa-chevron-right");
            const iconImg = this.querySelector(".icon-img");
            const listText = this.querySelector(".menu-text > p");

            const isCurrentlyOpen = subMenu && subMenu.classList.contains("open");

            // 현재 상태를 로컬 스토리지에 저장
            if (!isCurrentlyOpen) {
                localStorage.setItem(`menu-open-${index}`, true);
            }

            // 하위 메뉴와 이미지가 존재하는지 확인하고 토글
            if (subMenu) subMenu.classList.toggle("open");
            if (barImg) barImg.classList.toggle("open");
            if (arrowRight) arrowRight.classList.toggle("open");
            if (iconImg) iconImg.classList.toggle("open");
            if (listText) listText.classList.toggle("open");

            // 다른 메뉴 초기화
            menuItems.forEach((otherItem, otherIndex) => {
                if (otherItem !== item) {
                    const otherSubMenu = otherItem.querySelector(".sub-menu");
                    const otherBarImg = otherItem.querySelector(".bar-img");
                    const otherArrowRight = otherItem.querySelector(".fa-chevron-right");
                    const otherIconImg = otherItem.querySelector(".icon-img");
                    const otherListText = otherItem.querySelector(".menu-text > p");

                    if (otherSubMenu) otherSubMenu.classList.remove("open");
                    if (otherBarImg) otherBarImg.classList.remove("open");
                    if (otherArrowRight) otherArrowRight.classList.remove("open");
                    if (otherIconImg) otherIconImg.classList.remove("open");
                    if (otherListText) otherListText.classList.remove("open");

                    // 다른 메뉴 상태를 로컬 스토리지에서 삭제
                    localStorage.removeItem(`menu-open-${otherIndex}`);
                }
            });
        });
    });
});


const clock = document.querySelector(".clock")

function getClocks() {
    const date = new Date();
    const nowYear = date.getFullYear();
    const nowMonth = String(date.getMonth() + 1).padStart(2, "0");
    const nowDate = String(date.getDate()).padStart(2, "0");

    const days = `${nowYear}-${nowMonth}-${nowDate}`;

    const hours = String(date.getHours()).padStart(2, "0")
    const minutes = String(date.getMinutes()).padStart(2, "0")
    const seconds = String(date.getSeconds()).padStart(2, "0")
    clock.innerText = `${days} ${hours}:${minutes}:${seconds}`
}

getClocks();
setInterval(getClocks, 1000)

function datepicker(start, end) {
   const checkDates = () => {
      const startDate = startDatePicker.selectedDates[0];
      const endDate = endDatePicker.selectedDates[0];

      if (startDate && endDate && startDate > endDate) {
          startDatePicker.clear();
      }
   }
   
   const startDatePicker = flatpickr(`#${start}`, {
        dateFormat: "Y-m-d",
        enableTime: false,
        defaultDate: null,
         onChange: checkDates
    });
   
   const endDatePicker = flatpickr(`#${end}`, {
        dateFormat: "Y-m-d",
        enableTime: false,
          defaultDate: null,
         onChange: checkDates
    });

}

// 날짜 옵션 기능
function setDateOption(day, obj) {
    const now_date = new Date();
    const new_date = new Date(); 
    
    new_date.setDate(now_date.getDate() - day);

    const start_year = new_date.getFullYear();
    const start_month = (new_date.getMonth() + 1).toString().padStart(2, '0');
    const start_day    = new_date.getDate().toString().padStart(2, '0');
    
    const end_year = now_date.getFullYear();
    const end_month = (now_date.getMonth() + 1).toString().padStart(2, '0');
    const end_day    = now_date.getDate().toString().padStart(2, '0');
    
    $(".startDate").val(`${start_year}-${start_month}-${start_day}`);
    $(".endDate").val(`${end_year}-${end_month}-${end_day}`);
    
    $(".date-btn").removeClass("active");
    $(obj).addClass("active");
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

/**
 * Ajax post 요청 공용함수
 * @param {string} url ajax 요청 보낼 주소
 * @param {object} params ajax에 담아보낼 파라미터
 * @param {function} successFc 통신 성공 시 실행할 콜백함수
 */
function fnPostAjax(url, params, successFc) {
	$.ajax({
		url: url,  
		type: 'POST',
		contentType: 'application/json',
		data: JSON.stringify(params),  
		success: function(jsonData) {
			if(typeof successFc === 'function') {
				successFc(jsonData);
			}
		},
		error: function() {
			getCheckModal('통신 중 오류가 발생했습니다.');
		}
	});
}