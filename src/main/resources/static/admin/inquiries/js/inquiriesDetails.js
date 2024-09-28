document.addEventListener('DOMContentLoaded', function() {
	const maxByteLength = 1000;

		
	function getByteLength(str) {
		let byteLength = 0;
		for (let i = 0; i < str.length; i++) {
			let charCode = str.charCodeAt(i);
			if (charCode <= 0x007F) {
				byteLength += 1;
			} else if (charCode <= 0x07FF) {
				byteLength += 2;
			} else {
				byteLength += 3;
			}
		}
		return byteLength;
	}
	
	
	$("#inquiryContent").on("input", function (e) {
		let textarea = $(this);
		let content = textarea.val();
		let byteLength = getByteLength(content);

		if (byteLength > maxByteLength) {
			while (getByteLength(content) > maxByteLength) {
				content = content.substring(0, content.length - 1);
			}
			textarea.val(content);
		}

		$("#charCount").text(getByteLength(textarea.val()));
	});
});
