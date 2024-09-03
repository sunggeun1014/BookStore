const searchInput = document.querySelector(".search-input")
const bookModal = document.querySelector("#search-book-modal")
const closeBtn = document.querySelector("#close-btn")

searchInput.addEventListener("click", function () {
    bookModal.classList.add("on");
})

closeBtn.addEventListener("click", function () {
    bookModal.classList.remove("on")
})
