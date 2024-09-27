console.log("하이");

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
