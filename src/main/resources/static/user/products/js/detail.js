function calcQty() {
    const inputQty = document.querySelector(".input-qty");
    const minus = document.querySelector(".fa-minus");
    const plus = document.querySelector(".fa-plus");
    let totalPrice = document.querySelector(".total-price");
    const price = parseInt(totalPrice.dataset.price) || 0;

    let qty = parseInt(inputQty.value) || 1;

    function updatePrice() {
        totalPrice.innerText = (price * qty).toLocaleString();
    }

    updatePrice();

    minus.addEventListener("click", () => {
        if (qty > 1) {
            qty--;
            inputQty.value = qty;
            updatePrice();
        } else {
            getCheckModal("1보다 작을 수 없습니다.")
        }
    });

    plus.addEventListener("click", () => {
        qty++;
        inputQty.value = qty;
        updatePrice();
    });

    inputQty.addEventListener("change", () => {
        qty = parseInt(inputQty.value) || 1;
        if (qty < 1) {
            qty = 1;
            inputQty.value = 1;
        }
        updatePrice();
    });
}

function markStarID() {
    const idElements = document.querySelectorAll(".member-id");

    idElements.forEach(idP => {
        const getID = idP.dataset.memid;
        const setID = getID ? getID.trim() : ''; // 데이터가 없을 경우를 대비해 기본값 설정

        if (setID.length > 2) {
            const makeID = setID.substring(0, 2) + '******';
            idP.textContent = makeID;
        }
    });
}

window.onload = function() {
    calcQty();
    markStarID();
};
