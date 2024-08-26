function date_btn_click(day) {
	const now_date = new Date();
	const new_date = new Date(); 
	
	const start_year = now_date.getFullYear();
	const start_month = (now_date.getMonth() + 1).toString().padStart(2, '0');
	const start_day	= now_date.getDate().toString().padStart(2, '0');
	
	new_date.setDate(now_date.getDate() + day);
	
	const end_year = new_date.getFullYear();
	const end_month = (new_date.getMonth() + 1).toString().padStart(2, '0');
	const end_day	= new_date.getDate().toString().padStart(2, '0');
	
	
	
	$("#start_date").val(`${start_year}-${start_month}-${start_day}`);
	$("#end_date").val(`${end_year}-${end_month}-${end_day}`);
}