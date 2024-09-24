window.onload = function () {
    showSideMenu();
    getProfileImg();
}

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

function getProfileImg() {
    const imgs = ['홍길동.png', '고길동.jpg'];

    const randomIndex = Math.floor(Math.random() * imgs.length);

    const randomImg = imgs[randomIndex];

    const userImg = document.querySelector(".user-img");
    userImg.setAttribute("src", `/user/common/imgs/${randomImg}`);
}