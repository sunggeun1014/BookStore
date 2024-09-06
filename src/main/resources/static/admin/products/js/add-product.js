var table;
$(document).ready(function() {
    // 테이블이 이미 초기화되어 있는지 확인
    if (!$.fn.DataTable.isDataTable('#inventory')) {
        table = $('#inventory').removeAttr('width').DataTable({
            autoWidth: false,
            "pageLength": 5,
            "paging": true,
            columnDefs:
                [
                    { targets: 0, orderable: false }, // 첫 번째 컬럼(체크박스 컬럼)에서 정렬 비활성화
                    // 가운데정렬
                    {
                        className: 'table-center',
                        targets: '_all'
                    },
                    {
                        width: '40px',
                        targets: 0
                    },
                ],
            order: [[0, 'asc']], // 리뷰 작성 날짜 컬럼을 최신 날짜순으로 정렬 (내림차순)
            ajax: {
                url: '/admin/products/inventory/json',
                dataSrc: 'data',
            },
            columns: [
                {
                    data: null,  // 이 컬럼은 데이터베이스에서 가져오는 데이터를 사용하지 않음
                    render: function(data, type, row, meta) {
                        return meta.row + 1;  // meta.row는 0부터 시작하는 행 인덱스이므로 +1 해줌
                    },
                    orderable: false,  // 이 컬럼에 대해 정렬을 비활성화
                },
                {
                    data: 'inv_isbn',
                    width: '200px',
                    className: 'select-td'
                },
                {
                    data: 'inv_title',
                    className: 'text-ellipsis',
                },
                {
                    data: 'inv_qty',
                },
                {
                    data: 'zone_num',
                },

            ],
            "info": false,
            lengthChange: false,
            dom: 'lrtip',
            language: {
                searchPanes: {
                    i18n: {
                        emptyMessage: "조회된 정보가 없습니다."
                    }
                },
                infoEmpty: "조회된 정보가 없습니다.",
                zeroRecords: "조회된 정보가 없습니다.",
                emptyTable: "조회된 정보가 없습니다.",
            },
        });
    }

    // 테이블 행 클릭 시 book_isbn, book_name 입력 필드에 값 설정
    getBookInfo();


    // 책 검색 입력 필드에 입력 시 실시간으로 테이블 검색 필터링 적용
    searchWord();

    // 날짜선택
    datepicker("singleDate");

    // 재고리스트 출력
    showInvenList();

    // 폼전송
    checkForm();

    // 이미지 보이도록
    previewImg();
});

function searchWord() {
    $('#search-keyword').on('keyup', function() {
        var value = this.value;

        // 입력된 값이 숫자로만 이루어져 있으면 ISBN으로 검색
        if ($.isNumeric(value)) {
            table.column(1).search(value).draw();  // 1번 컬럼인 ISBN 열에서 검색
        } else {
            // 숫자가 아니면 제목 열에서 검색
            table.column(2).search(value).draw();  // 2번 컬럼인 제목 열에서 검색
        }
    });
}

let selectedISBN = null;
let invQty = null;
function getBookInfo() {
    $('#inventory tbody').on('click', 'tr', function() {
        var data = table.row(this).data(); // 클릭한 행의 데이터 가져오기

        // inv_isbn과 inv_title을 각각 input에 채워 넣음
        $('input[name="book_isbn"]').val(data.inv_isbn);
        $('input[name="book_name"]').val(data.inv_title);
        $('input[name="book_qty"]').val(data.inv_qty);

        selectedISBN = data.inv_isbn;
        invQty = data.inv_qty;

        $('#search-book-modal').removeClass('on');
        enableScroll();
    });
}

function getCountryValue() {
    const countryRadio = document.querySelector('input[name="book_country_type"]:checked');
    return countryRadio ? countryRadio.value : null;
}

function getStateValue() {
    const stateRadio = document.querySelector('input[name="book_state"]:checked');
    return stateRadio ? stateRadio.value : null;
}

function getCategoryValue() {
    const categorySelect = document.querySelector('select[name="book_category"]');
    return categorySelect ? categorySelect.value : null;
}


function checkForm() {
    const addForm = document.getElementById("add-form");
    const inputISBN = document.querySelector("#book_isbn");
    const inputName = document.querySelector("#book_name");
    const inputPublisher = document.querySelector("#book_publisher");
    const inputAuthor = document.querySelector("#book_author");
    const inputPublishDate = document.querySelector("#singleDate");
    const inputPrice = document.querySelector("#book-price");
    const inputQty = document.querySelector("#book-qty");
    const inputFile = document.querySelector("#input-file");
    const inputIntro = document.querySelector("#book_intro");

    addForm.addEventListener("submit", function(event) {
        event.preventDefault(); // 폼 제출 중단

        if (inputISBN.value === "") {
            getCheckModal("ISBN을 입력해주세요");
            inputISBN.focus();
            return;
        }
        // ISBN 중복 체크를 위한 AJAX 요청
        $.ajax({
            url: '/admin/products/checkISBN',  // 서버의 ISBN 중복 체크 엔드포인트
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify({ book_isbn: inputISBN.value }),
            success: function(response) {
                if (response.exists) {
                    if (response.deleteState === '02') {
                        getConfirmModal("숨김 처리된 ISBN입니다. 수정 페이지로 돌아가시겠습니까?", function () {
                            // 페이지 이동
                            location.href = `/admin/products/editProduct?book_isbn=${inputISBN.value}`;
                        });
                    } else {
                        // 중복된 ISBN인 경우
                        getCheckModal("이미 존재하는 ISBN입니다.");
                        inputISBN.focus();
                    }
                } else {
                    // 중복되지 않은 경우 나머지 검사를 진행
                    if (inputName.value === "") {
                        getCheckModal("책 제목을 입력해주세요");
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
                        getCheckModal("발행 날짜를 입력해주세요");
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
                        inputPrice.focus();
                        return;
                    }

                    if (inputQty.value === "") {
                        getCheckModal("수량을 입력해주세요");
                        inputQty.focus();
                        return;
                    }

                    if (inputQty.value > invQty) {
                        getCheckModal("재고의 수량보다 많습니다")
                        inputQty.focus();
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
                        inputIntro.focus();
                        return;
                    }

                    getConfirmModal("등록하시겠습니까?", function() {
                        addForm.submit();
                    });
                }
            },
            error: function(jqXHR, textStatus, errorThrown) {
                console.error("AJAX Error: ", textStatus, errorThrown);
                getErrorModal()
            }
        });
    });
}




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


function showInvenList() {
    const searchInput = document.querySelector(".search-input")
    const bookModal = document.querySelector("#search-book-modal")
    const closeBtn = document.querySelector("#close-btn")

    // 검색창 입력(keyup) 시 모달 자동 표시
    searchInput.addEventListener("keyup", function () {
        const inputValue = searchInput.value.trim();

        // 입력된 값이 있을 때 모달이 뜨도록 설정
        if (inputValue.length > 0 && !bookModal.classList.contains("on")) {
            bookModal.classList.add("on");
            disableScroll();
        }

        // DataTables 검색 기능 호출 (검색어에 해당하는 항목을 필터링)
        table.search(inputValue).draw();
    });

    closeBtn.addEventListener("click", function () {
        bookModal.classList.remove("on")
        enableScroll();
    })
}

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


function previewImg() {
    const inputFile = document.getElementById('input-file');
    const preview = document.getElementById('preview');

    inputFile.addEventListener('change', function (event) {
        var input = event.target;

        if (input.files && input.files[0]) {
            var reader = new FileReader();

            reader.onload = function (e) {
                preview.src = e.target.result;
            }

            reader.readAsDataURL(input.files[0]);
        }
    });
}