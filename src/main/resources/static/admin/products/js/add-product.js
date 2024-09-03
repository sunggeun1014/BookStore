const searchInput = document.querySelector(".search-input")
const bookModal = document.querySelector("#search-book-modal")
const closeBtn = document.querySelector("#close-btn")

let scrollPosition = 0;

function disableScroll() {
    document.body.style.position = 'fixed';
    document.body.style.top = `-${scrollPosition}px`;
    document.body.style.overflow = 'hidden';
    document.body.style.width = '100%';
}

function enableScroll() {
    document.body.style.position = '';
    document.body.style.top = '';
    document.body.style.overflow = '';
    window.scrollTo(0, scrollPosition);
}


searchInput.addEventListener("click", function () {
    bookModal.classList.add("on");
    disableScroll();
})

closeBtn.addEventListener("click", function () {
    bookModal.classList.remove("on")
    enableScroll();
})

function datepicker(elementId) {
    const dateInput = document.querySelector(`#${elementId}`);

    flatpickr(dateInput, {
        dateFormat: "Y-m-d",
        enableTime: false,
        defaultDate: null,
        allowInput: true,
        onValueUpdate: function(selectedDates, dateStr, instance) {
            // When a date is selected, we validate the format
            validateDateInput(dateInput, dateStr);
        }
    });

    // 사용자가 input에 데이트값을 입혁할 때 정규표현식 체크
    dateInput.addEventListener('input', function() {
        validateDateInput(dateInput, dateInput.value);
    });
    dateInput.addEventListener('blur', function() {
        validateDateInput(dateInput, dateInput.value);
    });
}

function validateDateInput(inputElement, value) {
    const regex = /^\d{4}-\d{2}-\d{2}$/;

    if (!regex.test(value)) {
        inputElement.setCustomValidity('날짜 형식은 yyyy-mm-dd 입니다.');
    } else {
        inputElement.setCustomValidity('');
    }
}


datepicker("singleDate");
