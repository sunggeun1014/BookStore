const isbnValues = document.querySelectorAll(".order-book-isbn");

let getISBN = [];

isbnValues.forEach(isbn => {
    getISBN.push(isbn.getAttribute("data-isbn"));
});

console.log(getISBN);

const clientKey = "test_ck_nRQoOaPz8LKMB7BEbGbNry47BMw6";
const secretKey = "test_sk_GePWvyJnrKj7J5P9lGLqVgLzN97E";


// const response = await PortOne.requestPayment({
//     // Store ID 설정
//     storeId: "store-5d54b2b0-bf88-45dd-8265-7018895b8a38",
//     // 채널 키 설정
//     channelKey: "channel-key-db630549-a619-4ab8-8739-466edb7307c6",
//     paymentId: `payment-${crypto.randomUUID()}`,
//     orderName: "나이키 와플 트레이너 2 SD",
//     totalAmount: 1000,
//     currency: "CURRENCY_KRW",
//     payMethod: "CARD",
// });