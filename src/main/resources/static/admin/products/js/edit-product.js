// 날짜선택
datepicker("singleDate");

checkEditForm();

// function getCountryValue() {
//     const countryRadio = document.querySelector('input[name="book_country_type"]:checked');
//     return countryRadio ? countryRadio.value : null;
// }
//
// function getStateValue() {
//     const stateRadio = document.querySelector('input[name="book_state"]:checked');
//     return stateRadio ? stateRadio.value : null;
// }
//
// function getCategoryValue() {
//     const categorySelect = document.querySelector('select[name="book_category"]');
//     return categorySelect ? categorySelect.value : null;
// }

function checkEditForm() {
    const editForm = document.querySelector("#edit-form")
    const inputName = document.querySelector("#book_name");
    const inputPublisher = document.querySelector("#book_publisher");
    const inputAuthor = document.querySelector("#book_author");
    const inputPublishDate = document.querySelector("#singleDate");
    const inputPrice = document.querySelector("#book-price");
    const inputQty = document.querySelector("#book-qty");
    const inputFile = document.querySelector("#input-file");
    const inputIntro = document.querySelector("#book_intro");

    editForm.addEventListener("submit", function(event) {
        event.preventDefault();

        if (inputISBN.value === "") {
            getCheckModal("ISBN을 입력해주세요");
            inputISBN.focus();
            return;
        }

        if (inputName.value === "") {
            getCheckModal("책제목을 입력해주세요");
            inputName.focus();
            return;
        }

        if (inputPublisher.value === "") {
            getCheckModal("출판사를 입력해주세요");
            inputPublisher.focus();
            return;
        }

        if (inputAuthor.value === "") {
            getCheckModal("저자를 입력해주세요");
            inputAuthor.focus();
            return;
        }

        if (inputPublishDate.value === "") {
            getCheckModal("발행날짜를 입력해주세요");
            inputPublishDate.focus();
            return;
        }

        const countryValue = getCountryValue();
        if (!countryValue) {
            getCheckModal("국내/국외를 선택해 주세요.");
            return;
        }

        const categoryValue = getCategoryValue();
        if (!categoryValue) {
            getCheckModal("카테고리를 선택해 주세요");
            return;
        }

        if (inputPrice.value === "") {
            getCheckModal("가격을 입력해주세요");
            return;
        }

        if (inputQty.value === "") {
            getCheckModal("수량을 입력해주세요");
            return;
        }

        const stateValue = getStateValue();
        if (!stateValue) {
            getCheckModal("상품 상태를 선택해주세요");
            return;
        }

        if (!inputFile.value) {
            getCheckModal("파일을 첨부해주세요");
            return;
        }

        if (inputIntro.value === "") {
            getCheckModal("책 소개를 입력해주세요");
            return;
        }

        // 모든 유효성 검사를 통과한 경우
        if (bookISBN.length > 0) {
            getConfirmModal("수정하시겠습니까?", function() {
                addForm.submit(); // 폼 제출
            });
        } else {
            getErrorModal();
        }
    });
}

function datepicker(elementId) {
    flatpickr(`#${elementId}`, {
        dateFormat: "Y-m-d",
        enableTime: false,
        defaultDate: null,
        allowInput: true
    });
}
