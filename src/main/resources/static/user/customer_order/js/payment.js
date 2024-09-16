function agreeClickHandler() {
    const agreeContent = document.querySelectorAll('.agree-content');

    agreeContent.forEach(content => {
        content.addEventListener('click', () => {
            agreeContent.forEach(otherContent =>
                otherContent !== content && otherContent.classList.remove('active')
            );
            content.classList.toggle('active');
        });
    });
}

function showDoorPWInput() {
    const doorPW = document.querySelector("#door-pw");
    const doorFree = document.querySelector("#door-free");
    const doorPwInput = document.querySelector(".input-wrap");

    doorPW.addEventListener('click', function () {
        if (doorPW.checked) {
            doorPwInput.style.display = 'block';
        }
    });

    doorFree.addEventListener('click', function () {
        if (doorFree.checked) {
            doorPwInput.style.display = 'none';
        }
    });
}

function showEditAddrModal () {
    const editBtn = document.querySelector("#edit-btn")
    const closeBtn = document.querySelector("#close-btn")
    const addrModal = document.querySelector("#edit-addr-modal")
    const outside = document.querySelector("#payment-section")
    const outside2 =document.querySelector("#deliver-info")

    editBtn.addEventListener('click', function() {
        addrModal.classList.add("on")
    })

    closeBtn.addEventListener("click", function () {
        addrModal.classList.remove("on")
    })

    window.addEventListener('click', function (e) {
        if (e.target === outside || e.target === outside2) {
            addrModal.classList.remove("on")
        }
    })
}


document.addEventListener('DOMContentLoaded', function () {
    showDoorPWInput();
    agreeClickHandler();
    showEditAddrModal();
});
