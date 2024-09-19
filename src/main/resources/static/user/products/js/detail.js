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

    if (reviewBox && reviewList) {
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

function drawTotalPrice() {
    const priceText = document.querySelector(".total-price")
    const price = parseInt(priceText.getAttribute('data-price')) || 0;

    getTotalPrice = price * getTotalQty;
    priceText.innerText = getTotalPrice.toLocaleString();
}

const cartBtn = document.getElementById("cart-btn")
const buyNowBtn = document.getElementById("buy-now-btn");

const addForm = document.getElementById("add-form")

function submitHandler() {
    const bookISBN = document.getElementById("book_isbn")
    const cartQtyInput = document.getElementById("cart_purchase_qty")
    const bookPriceInput = document.getElementById("book_price")
    const bookNameInput = document.getElementById("book_name");
    const bookThumbNailInput = document.getElementById("book_thumbnail_changed");

    addForm.addEventListener("submit", function (e) {
        e.preventDefault();
        const action = e.submitter.getAttribute("data-action")

        const memberID = getMemberID([cartBtn, buyNowBtn])

        if (memberID === null) {
            getConfirmModal("로그인 하지 않으셨습니다. 로그인 페이지로 가시겠습니까?", function () {
                location.href = '/user/login'
            })
        } else {
            cartQtyInput.value = getTotalQty;
            bookPriceInput.value = getTotalPrice;

            if (action === 'cart') {
                const data = [{
                    book_isbn: bookISBN.value,
                    cart_purchase_qty: cartQtyInput.value,
                }];

                $.ajax({
                    url: '/user/productsRest/productBasketSave',
                    method: 'POST',
                    contentType: 'application/json',
                    data: JSON.stringify(data),
                    success: function (response) {
                        if (response === 1) {
                            console.log("장바구니에 이미 있다")
                            getConfirmModal("장바구니에 이미 있는 상품입니다. 수량을 추가하여 장바구니에 추가 했습니다. 장바구니로 이동 하시겠습니까?", function () {
                                location.href = '/user/cart/list'
                            })
                        } else {
                            console.log('ajax 성공')
                            getConfirmModal("장바구니에 추가 되었습니다. 장바구니로 이동 하시겠습니까?", function () {
                                location.href = '/user/cart/list'
                            })
                        }
                    },
                    error: function (xhr) {
                        if (xhr.status === 409) {
                            getErrorModal("이미 장바구니에 있는 상품입니다.");
                        } else {
                            console.log(xhr)
                            getErrorModal("장바구니에 담기에 실패 했습니다.");
                        }
                    }
                })
            } else if (action === 'buyNow') {
                const orderData = [{
                    book_isbn: bookISBN.value,
                    book_name: bookNameInput.value,
                    cart_purchase_qty: cartQtyInput.value,
                    book_price: bookPriceInput.value,
                    book_thumbnail_changed: bookThumbNailInput.value,
                }]
                console.log('바로구매클릭')
                console.log('바로구매data? ', orderData)

                $.ajax({
                    url: '/user/productsRest/instantBuy',
                    method: 'POST',
                    contentType: 'application/json',
                    data: JSON.stringify(orderData),
                    success: function (response) {
                        console.log("바로구매 성공")
                        location.href = '/user/order';
                    },
                    error: function (jqXHR, textStatus, errorThrown) {
                        console.log("바로구매 에러", textStatus, errorThrown);
                        getErrorModal("바로구매 오류 입니다.")
                    }
                })
            }
        }
    });
}

function refundInfoHandler() {
    const refundBtn = document.getElementById("refund-btn");
    const inquiryBtn = document.getElementById("inquiry-btn");

    const memberID = getMemberID([refundBtn, inquiryBtn]);

    function handleClick(button, action) {
        button.addEventListener("click", function () {
            if (memberID === null) {
                getConfirmModal("로그인 하지 않으셨습니다. 로그인 페이지로 이동하시겠습니까?", function () {
                    location.href = '/user/login';
                });
            } else {
                // location.href = action;
                console.log(action);
            }
        });
    }

    handleClick(refundBtn, "반품신청으로 이동 (주문목록)");
    handleClick(inquiryBtn, "1:1문의로 이동 (접수화면)");
}

function getMemberID(buttons) {
    for (let btn of buttons) {
        const memberId = btn.getAttribute("data-memberid");
        if (memberId) return memberId;
    }
    return null;
}

function toggleReview() {
    const reviewContents = document.querySelectorAll('.review-content');

    reviewContents.forEach(content => {
        const reviewText = content.querySelector('.review-text');
        const toggleWrap = content.querySelector('.toggle-wrap');
        const toggleBtn = content.querySelector('.toggle-btn')

        // 컨텐츠 높이 측정
        const contentHeight = reviewText.scrollHeight;

        // 3줄 높이에 맞게 버튼 표시 여부 결정
        const lineHeight = parseFloat(window.getComputedStyle(reviewText).lineHeight);
        const maxHeight = lineHeight * 3;

        if (contentHeight > maxHeight) {
            // 3줄을 넘는 경우 버튼 표시
            toggleWrap.style.display = 'block';
        } else {
            // 3줄 이하인 경우 버튼 숨기기
            toggleWrap.style.display = 'none';
        }

        toggleWrap.addEventListener('click', function() {
            if (content.classList.contains('expanded')) {
                content.classList.remove('expanded');
                toggleBtn.textContent = '펼치기';
            } else {
                content.classList.add('expanded');
                toggleBtn.textContent = '접기';
            }
        });
    });
}

document.addEventListener('DOMContentLoaded', function () {
    goToReviewList();
    calcQty();
    submitHandler();
    refundInfoHandler();
    toggleReview();
})

window.onload = function () {
    markStarID();
    getWidthRating();
};
