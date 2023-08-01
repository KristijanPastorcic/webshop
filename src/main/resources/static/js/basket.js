$(document).ready(function () {
    // Attach a click event handler to the element
    $('.icon_close').click(function () {
        let iconClose = $(this);
        let index = iconClose.data('index');
        $.ajax({
            url: '/index/basket/' + index, // Specify the URL for the request
            type: 'GET', // Choose the HTTP method (GET, POST, etc.)
            dataType: 'json', // Specify the expected data type
            success: function (data) {
                iconClose.closest('tr').remove();
                $('#total').text(data.total);
                $('div.header__cart__price span').text(data.total);
                $('.fa.fa-shopping-bag').next('span').text(data.count);
            },
            error: function (xhr, status, error) {
                // Handle errors
                console.log('Error:', error);
            }
        });
    });

    $('.qtybtn').click(function () {
        let quantityBtn = $(this);
        let tr = quantityBtn.closest('tr');
        let value = tr.find('input').val();
        if (value === 0 || isNaN(value))
            return;
        let action = quantityBtn.text();
        let index = quantityBtn.siblings('input').data('index');
        $.ajax({
            url: '/index/basket/quantity/' + index + '?action=' + action,
            type: 'GET',
            dataType: 'json',
            success: function (data) {
                $('#total').text(data.total);
                $('div.header__cart__price span').text(data.total);
                $('.fa.fa-shopping-bag').next('span').text(data.count);
            },
            error: function (xhr, status, error) {
                console.log(error);
            }
        });
    });

});
