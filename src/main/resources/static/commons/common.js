function price_formatter(number) {
	const formatter = new Intl.NumberFormat('en-US', {
	    style: 'decimal', // 또는 'currency'로 설정 가능
	    minimumFractionDigits: 0,
	    maximumFractionDigits: 0
	});
	
	return formatter.format(number) + "원";
}

function checkbox_handler() {
	$("#select_all").on("change", function() {
		$(".row_checkbox").prop("checked", $(this).prop("checked"));
	});
}

function reset_btn() {
	$(".reset_btn").on("click", function() {
		document.getElementById("filter_form").reset();
	});
}

function datepicker() {
	const checkDates = () => {
		const startDate = startDatePicker.selectedDates[0];
		const endDate = endDatePicker.selectedDates[0];
		if (startDate && endDate && startDate > endDate) {
		    startDatePicker.clear();
		}
	}
	
	const startDatePicker = flatpickr("#start_date", {
        dateFormat: "Y-m-d",
        enableTime: false,
        defaultDate: null,
		onChange: checkDates
    });
	
	const endDatePicker = flatpickr("#end_date", {
        dateFormat: "Y-m-d",
        enableTime: false,
	    defaultDate: null,
		onChange: checkDates
    });
	
    $("#calendarIcon").on('click', function() {
        startDatePicker.open();
    });
	
    $("#calendarIcon").on('click', function() {
        endDatePicker.open();
    });
}

