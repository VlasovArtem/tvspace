/**
 * Created by artemvlasov on 22/11/15.
 */
var ascOrder = "ASC";
var descOrder = "DESC";

function sayHello() {
    console.log($('input[type=date]').val());
    var data = $('form').serializeArray();
    for(var i = 0; i < data.length; i++) {
        console.log(data[i].value);
    }

}

function searchByGenre(form) {
    var genre = $('#genres')[0].value;
    form.action = "/series/" + genre;
}

function searchSeries() {
    var title = $('#title');
    if(title[0].value == "") {
        title.attr("disabled", true);
    }
    $('input[name=_hideFinished]').attr("disabled", true);
    $('#searchForm').submit();
}

function stickyFunc() {
    var stickyElements = document.getElementsByClassName('sticky');

    for (var i = stickyElements.length - 1; i >= 0; i--) {
        Stickyfill.add(stickyElements[i]);
    }

    $("#series-search").css("height", $("#series").height());
}

function changeSort() {
    var ascOrder = "ASC";
    var descOrder = "DESC";
    var direction = $('.direction');
    direction[0].innerText = direction[0].innerText == ascOrder ? descOrder : ascOrder;
    $('#direction')[0].value = direction[0].innerText;
    changeOrder();
}

function init() {
    stickyFunc();
    changeOrder();
    sortChange();
}

function changeOrder () {
    var currentDirection = $('#direction')[0].value;
    if (_.isEqual(currentDirection, ascOrder)) {
        $('.asc-img').show();
        $('.desc-img').hide();
    } else {
        $('.asc-img').hide();
        $('.desc-img').show();
    }
}

function sortChange () {
    var sort = $('#sort')[0].value;
    if(_.isEqual(sort, "Next Episode")) {
        $('.sort-info').show()
    } else {
        $('.sort-info').hide()
    }
}

$(function() {
    $('a[href*=#]:not([href=#])').click(function() {
        if (location.pathname.replace(/^\//,'') == this.pathname.replace(/^\//,'') && location.hostname == this.hostname) {
            var target = $(this.hash);
            target = target.length ? target : $('[name=' + this.hash.slice(1) +']');
            if (target.length) {
                $('html,body').animate({
                    scrollTop: target.offset().top
                }, 1000);
                return false;
            }
        }
    });
});

function login() {
    $.post
}