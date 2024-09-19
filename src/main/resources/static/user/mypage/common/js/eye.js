$(document).ready(function() {
    $('#eye').on('click', function() {
        const input = $(this).siblings('input');
        if (input.attr('type') === 'password') {
            input.attr('type', 'text');
            $(this).attr('class', 'fa fa-eye-slash fa-lg');
        } else {
            input.attr('type', 'password');
            $(this).attr('class', 'fa fa-eye fa-lg');
        }
    });
});
