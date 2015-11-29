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
    console.log(form.action);
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
    changeOrder();
    var direction = $('.direction');
    direction[0].innerText = direction[0].innerText == ascOrder ? descOrder : ascOrder;
    $('#direction')[0].value = direction[0].innerText;
}

function init() {
    stickyFunc();
    changeOrder();
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