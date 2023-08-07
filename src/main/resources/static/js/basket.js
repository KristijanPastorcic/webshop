$(document).ready(function () {
    // Attach a click event handler to the element
    $('.icon_close').click(function () {
        let iconClose = $(this);
        let index = iconClose.data('index');
        $.ajax({
            url: '/index/basket/rm/ ' + index, // Specify the URL for the request
            type: 'GET', // Choose the HTTP method (GET, POST, etc.)
            dataType: 'json', // Specify the expected data type
            success: function (data) {
                iconClose.closest('tr').remove();
                $('#total').text(data.total);
                $('div.header__cart__price span').text(data.total);
                $('.fa.fa-shopping-bag').next('span').text(data.count);
                $('.icon_close').each(function (index) {
                    $(this).attr('data-index', index++);
                });
            },
            error: function (xhr, status, error) {
                // Handle errors
                console.log('Error:', error);
            }
        });
    });

    $('.qtybtn').click(function (e) {
        let quantityBtn = $(this);
        let op = quantityBtn.text();
        let tr = quantityBtn.closest('tr');
        let input = tr.find('input');
        const currentValue = parseInt(input.val());
        if (currentValue < 1 && op === '-') {
            return;
        }

        let index = quantityBtn.siblings('input').data('index');

        let url;
        if (op === '+') {
            url = '/index/basket/inc/' + index;
        } else {
            url = '/index/basket/dec/' + index;
        }

        $.ajax({
            url: url,
            type: 'GET',
            dataType: 'json',
            success: function (data) {
                $('#total').text(data.total + '€');
                tr.find('td.shoping__cart__total > span:nth-child(1)').text(data.item);
                $('.fa.fa-shopping-bag').next('span').text(data.count);
                $('div.header__cart__price span').text(data.total + '€');
            },
            error: function (xhr, status, error) {
                input.val(currentValue);
                console.log(error);
            }
        });
    });

});
