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

calcQty(".total-price", "data-price");

window.onload = function() {
    markStarID();
    getWidthRating();
};

const memid = document.querySelector("#member-id");
console.log(memid);