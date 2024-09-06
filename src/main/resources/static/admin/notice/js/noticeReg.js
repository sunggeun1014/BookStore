$(document).ready(function() {

	datepicker("startDate", "endDate");

	const editor = document.getElementById('editor');
	let images = []; // 업로드된 이미지들을 저장할 배열

	// 이미지 드래그 앤 드롭 기능
	editor.addEventListener('dragover', (event) => {
		event.preventDefault();
		editor.classList.add('dragover');
	});

	editor.addEventListener('dragleave', () => {
		editor.classList.remove('dragover');
	});

	editor.addEventListener('drop', (event) => {
		event.preventDefault();
		editor.classList.remove('dragover');

		const files = event.dataTransfer.files;
		if (files.length > 0) {
			const file = files[0];
			if (file.type.startsWith('image/')) {
				const reader = new FileReader();
				reader.onload = function(e) {
					const img = document.createElement('img');
					img.src = e.target.result;
					img.style.maxWidth = '100%';
					img.style.height = 'auto';
					editor.appendChild(img);

					images.push(file); // 이미지를 배열에 추가
				};
				reader.readAsDataURL(file);
			} else {
				alert('이미지 파일만 지원됩니다.');
			}
		}
	});

	document.getElementById('save-button').addEventListener('click', () => {
		const formData = new FormData();

		const noticeTitle = $('#notice-title').value();
		const noticeStartDate = $('#startDate').value();
		const noticeEndDate = $('#endDate').value();

		const noticeVisible = document.querySelector('input[name="order_status"]:checked').value;

		if (noticeStatus === 'exposed') {
			noticeStatus = '01';
		} else if (noticeStatus === 'nonexposed') {
			noticeStatus = '02';
		}
		
		// 텍스트 내용을 가져오기
		const noticeContent = editor.innerText;

		formData.append('notice_title', noticeTitle)
		formData.append('notice_content', noticeContent);
		formData.append('notice_end_date', noticeEndDate);
		formData.append('notice_start_date', noticeStartDate);
		formData.append('notice_visible', noticeVisible);

		// 이미지 파일들을 formData에 추가
		images.forEach((image, index) => {
			formData.append('image' + index, image);
		});

		// 서버로 데이터 전송
		fetch('/save', {
			method: 'POST',
			body: formData
		})
			.then(response => response.json())
			.then(data => {
				alert('저장되었습니다.');
			})
			.catch(error => {
				console.error('에러 발생:', error);
			});
	});

	// 초기 텍스트 지우기 (사용자가 편집을 시작하면 기본 텍스트 제거)
	editor.addEventListener('focus', () => {
		if (editor.innerText.trim() === '여기에 텍스트를 입력하거나 이미지를 드래그 앤 드롭하세요...') {
			editor.innerText = '';
		}
	});

});
