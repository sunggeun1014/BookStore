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

function goToReviewList() {
    const reviewBox = document.querySelector(".review-avg-wrap");
    const reviewList = document.querySelector(".review-info");

    if(reviewBox && reviewList) {
        reviewBox.addEventListener('click', function () {
            reviewList.scrollIntoView({
                behavior: 'smooth',
            })
        })
    }

}

let getTotalPrice = 0;
let getTotalQty = 0;

function calcQty() {
    const inputQty = document.querySelector(".input-qty");
    const minus = document.querySelector("#decrease");
    const plus = document.querySelector("#increase");

    function initialzeQty() {
        getTotalQty = parseInt(inputQty.value) || 1;
        updateQtyDisplay();
        console.log('초기값 수량 ', getTotalQty)
    }

    function updateQtyDisplay() {
        inputQty.value = getTotalQty;
        drawTotalPrice();
    }

    minus.addEventListener("click", () => {
        if (getTotalQty > 1) {
            getTotalQty--;
            inputQty.value = getTotalQty;
            updateQtyDisplay();
        } else {
            getCheckModal("수량은 1개 이상 선택하셔야 합니다.")
        }
    });

    plus.addEventListener("click", () => {
        getTotalQty++;
        inputQty.value = getTotalQty;
        updateQtyDisplay();
    });

    // 개수 초기화
    initialzeQty();
}
calcQty();

function drawTotalPrice() {
    const priceText = document.querySelector(".total-price")
    const price = parseInt(priceText.getAttribute('data-price')) || 0;

    getTotalPrice = price * getTotalQty;
    priceText.innerText = getTotalPrice.toLocaleString();
}

isLoginMember();

function isLoginMember() {
    const cartBtn = document.getElementById("cart-btn")
    const directBtn = document.getElementById("direct-order-btn");

    const cartBtnIDCheck = cartBtn.getAttribute("data-memberid")
    const directBtnIDCheck = directBtn.getAttribute("data-memberid")

    console.log(cartBtnIDCheck)

    cartBtn.addEventListener("click", function (e) {
        e.preventDefault();
        if (cartBtnIDCheck === null) {
            getConfirmModal("로그인 하지 않으셨습니다. 로그인 페이지로 가시겠습니까?", function () {
                location.href = '/user/login'
            })
        } else {
            console.log('장바구니가격 ', getTotalPrice)
            console.log('장바구니수량 ', getTotalQty)
            console.log("장바구니담기")
            addCartForm();
        }
    })
}

function addCartForm() {
    const addForm = document.getElementById("add-cart-form")
    const bookISBN = document.getElementById("book_isbn")
    const cartQtyInput = document.getElementById("cart_purchase_qty")
    const bookPriceInput = document.getElementById("book_price")

    if(!addForm) {
        console.log('폼태그 못찾음??')
        return
    }
    console.log('폼태그 찾았냐??')

    cartQtyInput.value = getTotalQty;
    bookPriceInput.value = getTotalPrice;

    addForm.addEventListener("submit", function (e) {
        e.preventDefault();

        const data = {
            book_isbn: bookISBN.value,
            cart_purchase_qty: cartQtyInput.value,
            book_price: bookPriceInput.value,
        };

        console.log('data? ', data)

        $.ajax({
            url: '/user/cart/add',
            method: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function () {
                console.log('ajax 성공')
                addForm.submit();
                getConfirmModal("장바구니에 담겼습니다. 장바구니로 이동 하시겠습니까?", function () {
                    location.href = '/user/cart/list'
                })
            },
            error: function () {
                console.log("ajax 에러")
                getErrorModal();
            }
        })

    })
}

document.addEventListener('DOMContentLoaded', function () {
    goToReviewList();
})

window.onload = function () {
    markStarID();
    getWidthRating();
};
