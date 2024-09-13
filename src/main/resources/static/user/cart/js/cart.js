document.addEventListener("DOMContentLoaded", function() {
    const cartItems = document.querySelectorAll('.cart-item');
	const totalProductsPriceElement = document.querySelector('.total-products-price');
    const totalItemsElement = document.querySelector('.total-qty'); 
	const expectedPaymentAmountElement = document.querySelector('.expected-payment-amount');
	
	function updateTotal() {
	    let total = 0;
		let totalItems = 0;
		
	    cartItems.forEach(item => {
	        const inputQty = item.querySelector(".input-qty");
	        const totalPriceElement = item.querySelector(".total-price");
	        const price = parseInt(totalPriceElement.getAttribute("data-price")) || 0;
	        const qty = parseInt(inputQty.value) || 1;
	        
			total += price * qty;
			totalItems += qty;
	    });
		
	    if (totalProductsPriceElement) {
	        totalProductsPriceElement.innerText = total.toLocaleString();
	    }
		
	    if (totalItemsElement) {
	        totalItemsElement.innerText = totalItems;
	    }
		
		if (expectedPaymentAmountElement) {
			expectedPaymentAmountElement.innerText = total.toLocaleString();
		}
	}
	
	cartItems.forEach(item => {
        calcQty(item, updateTotal);
    });
	
	updateTotal();
});



function calcQty(item, updateTotal) {
    const inputQty = item.querySelector(".input-qty");
    const minus = item.querySelector(".fa-minus");
    const plus = item.querySelector(".fa-plus");
    const totalPriceElement = item.querySelector(".total-price");
    
    const price = parseInt(totalPriceElement.getAttribute("data-price")) || 0;
    
    let qty = parseInt(inputQty.value) || 1;

    function updatePrice() {
        totalPriceElement.innerText = (price * qty).toLocaleString();
		updateTotal();
    }

    updatePrice();

    minus.addEventListener("click", () => {
        if (qty > 1) {
            qty--;
            inputQty.value = qty;
            updatePrice();
        } else {
            getCheckModal("수량은 1개 이상 선택하셔야 합니다.");
        }
    });

    plus.addEventListener("click", () => {
        qty++;
        inputQty.value = qty;
        updatePrice();
    });

    function handleQtyChange() {
        let newQty = parseInt(inputQty.value);
        if (isNaN(newQty) || newQty < 1) {
            newQty = 1;
        }
        qty = newQty;
        inputQty.value = qty;
        updatePrice();
    }

    inputQty.addEventListener("change", handleQtyChange);
    inputQty.addEventListener("input", handleQtyChange);
}
