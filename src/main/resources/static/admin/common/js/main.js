// 사이드메뉴 클릭시 기능
document.addEventListener("DOMContentLoaded", function() {
    const menuItems = document.querySelectorAll(".menu-items");

    // 로컬 스토리지에서 메뉴 상태를 불러와 복원
    menuItems.forEach((item, index) => {
        const subMenu = item.querySelector(".sub-menu-list");
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
            const subMenu = this.querySelector(".sub-menu-list");
            const barImg = this.querySelector(".bar-img");
            const arrowRight = this.querySelector(".fa-chevron-right");
            const iconImg = this.querySelector(".icon-img");
            const listText = this.querySelector(".menu-text > p");

            const isCurrentlyOpen = subMenu && subMenu.classList.contains("open");

            // 현재 상태를 로컬 스토리지에 저장
            if (!isCurrentlyOpen) {
                localStorage.setItem(`menu-open-${index}`, true);
            } //else {
                //localStorage.removeItem(`menu-open-${index}`); // 메뉴가 닫히면 데이터 삭제
            //}

            // 하위 메뉴와 이미지가 존재하는지 확인하고 토글
            if (subMenu) subMenu.classList.toggle("open");
            if (barImg) barImg.classList.toggle("open");
            if (arrowRight) arrowRight.classList.toggle("open");
            if (iconImg) iconImg.classList.toggle("open");
            if (listText) listText.classList.toggle("open");

            // 다른 메뉴 초기화
            menuItems.forEach((otherItem, otherIndex) => {
                if (otherItem !== item) {
                    const otherSubMenu = otherItem.querySelector(".sub-menu-list");
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


// 헤더 시계
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

// 데이트피커
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
function setDateOption(day, obj, event) {
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
