$(document).ready(function() {
	datepicker("startDate", "endDate");

	const editor = document.getElementById('editor');
	let tempImages = [];
	let imageFiles = []; 

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
			for (let i = 0; i < files.length; i++) {
				const file = files[i];
				if (file.type.startsWith('image/')) {
					
					const reader = new FileReader();
					reader.onload = function(e) {
						const img = document.createElement('img');
						img.src = e.target.result;
						img.style.maxWidth = '100%';
						img.style.height = 'auto';
						editor.appendChild(img);

						tempImages.push(img);
						imageFiles.push(file); 
					};
					reader.readAsDataURL(file);
				} else {
					getCheckModal('이미지 파일만 지원됩니다.');
					return;
				}
			}
		}
	});

	
	document.getElementById('save-button').addEventListener('click', () => {
		const formData = new FormData();
		const noticeNum = $('#notice_num').val();
		const noticeTitle = $('#notice-title').val();
		const noticeStartDate = $('#startDate').val() || new Date().toLocaleDateString('en-cA');
		const noticeEndDate = $('#endDate').val() || '2099-12-31';
		const startDateTimestamp = convertToTimestamp(noticeStartDate);
		const endDateTimestamp = convertToTimestamp(noticeEndDate);
		const noticeVisible = document.querySelector('input[name="order_status"]:checked').value;
		const noticeStatus = noticeVisible === 'exposed' ? '01' : '02';
		let noticeContent = editor.innerHTML; 
		
		const collator = new Intl.Collator('ko');

		if (collator.compare(noticeContent.trim(), '여기에 텍스트를 입력하거나 이미지를 드래그 앤 드롭하세요...') === 0|| !noticeContent.trim()) {
			getCheckModal('내용을 입력해주세요.');
			return;
		} else if (!noticeTitle.trim()){
			getCheckModal('공지글 제목을 입력해주세요.')
			return;
		}
		
		if (imageFiles.length > 0) {
			const formDataForImages = new FormData();
			imageFiles.forEach((file) => {
				formDataForImages.append('images', file); // 이미지 리스트로 추가
			});

			
			fetch('/admin/notice/imageUrl', {
				method: 'POST',
				body: formDataForImages
			})
			.then(response => response.json())
			.then(data => {
				if (data.success && data.imageUrls) {
					data.imageUrls.forEach((imageUrl, index) => {
						const oldSrc = tempImages[index].src;
						if (!oldSrc.startsWith('/images/notice/')) {
							tempImages[index].src = imageUrl;
							noticeContent = noticeContent.replace(oldSrc, imageUrl); // DB에 저장될 HTML의 <img src> 수정
						}
					});

					// 공지사항 수정 / 저장 처리
					handleNoticeSaveOrUpdate(noticeNum, formData, noticeTitle, noticeContent, startDateTimestamp, endDateTimestamp, noticeStatus);
				} else {
					getCheckModal('이미지 저장 실패');
					return;
				}
			})
			.catch(error => {
				console.error('이미지 업로드 중 에러 발생:', error);
			});
		} else {
			// 이미지 파일이 없을 때도 저장 또는 수정 호출
			handleNoticeSaveOrUpdate(noticeNum, formData, noticeTitle, noticeContent, startDateTimestamp, endDateTimestamp, noticeStatus);
		}
	});

	// 공지사항 저장 또는 수정 처리
	function handleNoticeSaveOrUpdate(noticeNum, formData, noticeTitle, noticeContent, startDate, endDate, noticeStatus) {
		if (noticeNum) {
			// 공지사항 수정 (update)
			updateNotice(formData, noticeNum, noticeTitle, noticeContent, startDate, endDate, noticeStatus);
		} else {
			// 공지사항 저장 (insert)
			saveNotice(formData, noticeTitle, noticeContent, startDate, endDate, noticeStatus);
		}
	}

	function saveNotice(formData, noticeTitle, noticeContent, startDate, endDate, noticeStatus) {
		formData.append('notice_title', noticeTitle);
		formData.append('notice_content', noticeContent);
		formData.append('notice_end_date', endDate);
		formData.append('notice_start_date', startDate);
		formData.append('notice_visible', noticeStatus);
		
		if (imageFiles.length > 0) {
			imageFiles.forEach((image) => {
				formData.append('images', image);
			});
		} 

		
		fetch('/admin/notice/save', {
			method: 'POST',
			body: formData
		})
		.then(response => {
			if (response.ok) {
				showSuccessModal('공지사항 저장되었습니다.');
				return;
			} else {
				getCheckModal('공지사항 저장 중 오류가 발생했습니다.');
				return;
			}
		});
	}

	function updateNotice(formData, noticeNum, noticeTitle, noticeContent, startDate, endDate, noticeStatus) {
		formData.append('notice_num', noticeNum);  
		formData.append('notice_title', noticeTitle);
		formData.append('notice_content', noticeContent);
		formData.append('notice_end_date', endDate);
		formData.append('notice_start_date', startDate);
		formData.append('notice_visible', noticeStatus);

		if (imageFiles.length > 0) {
			imageFiles.forEach((image) => {
				formData.append('images', image);
			});
		}

		
		fetch('/admin/notice/update', {
			method: 'POST',
			body: formData
		})
		.then(response => {
			if (response.ok) {
				showSuccessModal('공지사항이 수정되었습니다.');
			} else {
				getCheckModal('공지사항 수정 중 오류가 발생했습니다.');
			}
		});
	}

	
	editor.addEventListener('focus', () => {
		if (editor.innerText === '여기에 텍스트를 입력하거나 이미지를 드래그 앤 드롭하세요...') {
			editor.innerText = '';
		}
	});

	function convertToTimestamp(dateString) {
		const date = new Date(dateString);
		const year = date.getFullYear();
		const month = String(date.getMonth() + 1).padStart(2, '0'); 
		const day = String(date.getDate()).padStart(2, '0');
		const hours = '00'; 
		const minutes = '00';
		const seconds = '00';

		return `${year}-${month}-${day} ${hours}:${minutes}:${seconds}`;
	}

	function showSuccessModal(msg) {
		getCheckModal(msg);

		$(document).on('click', '#confirm-delete', function() {
			window.location.href = '/admin/notice/list';
		});
	}
});
