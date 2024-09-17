window.onload = function() {
    if (performance.navigation.type === performance.navigation.TYPE_RELOAD) {
        document.querySelectorAll('.error-msg').forEach(function(el) {
            el.style.display = 'none';
        });
    }
    
    let savedId = localStorage.getItem('savedUserId');
    if (savedId) {
        $('#id').val(savedId);  
        $('#saveIdCheckbox').prop('checked', true);  
    }

    let id = $('#id').val().trim();
    let password = $('#password').val().trim();
    if (id.length > 0 && password.length > 0) {
        $('#loginBtn').prop("disabled", false);  
    } else {
        $('#loginBtn').prop("disabled", true);   
    }
};

$(document).ready(function() {
	
    $('#id, #password').on('input', function() {
        let id = $('#id').val().trim();         
        let password = $('#password').val().trim(); 

        if (id.length > 0 && password.length > 0) {
            $('#loginBtn').prop("disabled", false);  
        } else {
            $('#loginBtn').prop("disabled", true);  
        }
    });

    $('#saveIdCheckbox').on('change', function() {
        if ($(this).is(':checked')) {
            let id = $('#id').val().trim();
            if (id.length > 0) {
                localStorage.setItem('savedUserId', id);
                console.log('아이디 저장됨:', id);
            }
        } else {
            localStorage.removeItem('savedUserId');
            console.log('아이디 저장 안함');
        }
    });
});
