console.log("ggg")

function datepicker(elementId) {
    flatpickr(`#${elementId}`, {
        dateFormat: "Y-m-d",
        enableTime: false,
        defaultDate: null,
        allowInput: true
    });
}

datepicker("singleDate");
