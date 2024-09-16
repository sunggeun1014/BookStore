$(document).ready(function () {
    let selectedRating = 0; // 사용자가 선택한 별점 값을 저장할 변수
    
    $(".star").on("click", function () {
        selectedRating = $(this).data('rating'); // 클릭된 별의 rating 값을 가져옴
        $("input[name='reviewRating']").val(selectedRating); // 숨겨진 input에 별점 값을 저장

        // 별의 상태 업데이트
        $(".star").each(function () {
            let starValue = $(this).data('rating');
            if (starValue <= selectedRating) {
                $(this).removeClass('far').addClass('fas'); // 선택된 별과 그 이전 별들 활성화
            } else {
                $(this).removeClass('fas').addClass('far'); // 그 이후 별들 비활성화
            }
        });
    });
});