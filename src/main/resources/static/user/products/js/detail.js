function markStarID() {
    const idElements = document.querySelectorAll(".reviewer-id");

    idElements.forEach(idP => {
        const getID = idP.dataset.reviewerid;
        const setID = getID ? getID.trim() : ''; // 데이터가 없을 경우를 대비해 기본값 설정

        if (setID.length > 2) {
            const makeID = setID.substring(0, 2) + '******';
            idP.textContent = makeID;
        }
    });
}

function getWidthRating() {
    const ratingBar = document.querySelectorAll(".rating-bar")

    ratingBar.forEach(rating => {
        const percent = rating.getAttribute('data-percent');
        const inner = rating.querySelector(".rating-bar-inner")

        if (rating.classList.contains("s5")) {
            inner.style.width = percent;
        }
        if (rating.classList.contains("s4")) {
            inner.style.width = percent;
        }
        if (rating.classList.contains("s3")) {
            inner.style.width = percent;
        }
        if (rating.classList.contains("s2")) {
            inner.style.width = percent;
        }
        if (rating.classList.contains("s1")) {
            inner.style.width = percent;
        }
    })
}

let getTotalPrice = 0;
let getTotalQty = 0;

function calcQty() {
    const inputQty = document.querySelector(".input-qty");
    const minus = document.querySelector("#decrease");
    const plus = document.querySelector("#increase");
    getTotalQty = parseInt(inputQty.value);

    drawTotalPrice();

    minus.addEventListener("click", () => {
        if (getTotalQty > 1) {
            getTotalQty--;
            inputQty.value = getTotalQty;
            drawTotalPrice();
        } else {
            getCheckModal("수량은 1개 이상 선택하셔야 합니다.")
        }
    });

    plus.addEventListener("click", () => {
        getTotalQty++;
        inputQty.value = getTotalQty;
        drawTotalPrice();
    });

    // function handleQtyChange() {
    //     let newQty = parseInt(inputQty.value);
    //     if (isNaN(newQty) || newQty < 1) {
    //         newQty = 1;
    //     }
    //     getTotalQty = newQty;
    //     inputQty.value = getTotalQty;
    //
    // }

    // inputQty.addEventListener("change", handleQtyChange);
    // inputQty.addEventListener("input", handleQtyChange);

    return getTotalQty;
}

calcQty();
console.log(getTotalQty)

function drawTotalPrice() {
    const priceText = document.querySelector(".total-price")
    const price = parseInt(priceText.getAttribute('data-price')) || 0;

    priceText.innerText = (price * getTotalQty).toLocaleString();

    return getTotalPrice = price * getTotalQty;

}

isLogin();

function isLogin() {
    const memID = document.getElementById("cart-btn").getAttribute("data-memberid")
    const cartBtn = document.querySelector("#cart-btn")
    const directBtn = document.querySelector("#direct-order-btn");

    console.log(memID)

    cartBtn.addEventListener("click", function() {
        if (memID == null) {
            getConfirmModal("로그인이 되어있지 않습니다. 로그인페이지로 이동 하시겠습니까?", function () {
                location.href = '/user/login'
            })
        } else {
            addCartForm();
            getConfirmModal("장바구니에 담았습니다.", function () {
                location.href = '/user/cart/list'
            })
        }
    })

    directBtn.addEventListener("click", function () {
        if (memID == null) {
            getConfirmModal("로그인이 되어있지 않습니다. 로그인페이지로 이동 하시겠습니까?", function () {
                location.href = '/user/login'
            })
        }
    })
}

function addCartForm() {
    const addForm = document.getElementById("add-cart-form")
    const bookISBN = document.getElementById("book_isbn")
    const cartQtyInput = document.getElementById("cart_purchase_qty")
    const bookPrice = parseInt(document.querySelector(".total-price").getAttribute("data-price")) || 0;

    console.log(bookPrice)

    let cartQty = cartQtyInput.value

    addForm.addEventListener("submit", function (e) {
        e.preventDefault();
        cartQty = getTotalQty;
        const data = [{
            book_isbn: bookISBN.value,
            cart_purchase_qty: cartQty,
            book_price: bookPrice * cartQty
        }];

        $.ajax({
            url: '/user/productsRest/productBasketSave',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function () {
                addForm.submit();
            },
            error: function () {
                getErrorModal();
            }
        })

    })
}

window.onload = function() {
    markStarID();
    getWidthRating();
};
