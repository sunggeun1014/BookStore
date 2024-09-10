showSideMenu();

// 마이페이지 사이드메뉴
function showSideMenu() {
    const menus = document.querySelectorAll(".sub-menu-list");

    menus.forEach((list) => {
        list.addEventListener("click", (e) => {
            const menuText = list.querySelector(".sub-menu-list > a");

            menuText.classList.add("on");

            menus.forEach((otherList) => {
                if (otherList !== list) {
                    const otherText = otherList.querySelector(".sub-menu-list > a");

                    otherText.classList.remove("on");
                }
            });
        });
    });
}
