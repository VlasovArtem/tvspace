/**
 * Created by artemvlasov on 22/11/15.
 */
var ascOrder = "ASC";
var descOrder = "DESC";

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
    $('input[name=_showUserSeries]').attr("disabled", true);
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
    $(document).tooltip();
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
    var form = $('#login-form');
    var loginObject = {
        loginData : form.find('#loginData')[0].value,
        password: form.find('#password')[0].value
    };
    if(form.find('#rememberMe')[0].checked) {
        loginObject.rememberMe = true;
    }
    $.post("/login", loginObject)
        .done(function() {
            location.assign("/")
        })
        .error(function() {
            var error = $('.login-block .error');
            if(!_.isNull(error)) {
                error.addClass("form-error-info");
                error.html("User with provided email, username or password does not exists");
                form.find('#password')[0].value = null;
            }
        })
}

function watching(id) {
    var trackingBlock = $(id);
    trackingBlock.find('.watching').removeClass("hide").addClass("show");
    trackingBlock.find('.not-watching').removeClass("show").addClass("hide");
    trackingBlock.find('.choose-series-info').removeClass('hide').addClass('show');

}

function notWatching(id) {
    var trackingBlock = $(id);
    $.post("/series/notwatching", {id : trackingBlock[0].id})
        .done(function() {
            _.each(trackingBlock.find("#episode option"), function(episode) {
                if(episode.value != 1) {
                    $(episode).removeAttr("selected");
                } else {
                    $(episode).attr("selected", "selected");
                }
            });
            trackingBlock.find('.watching').removeClass("show").addClass("hide");
            trackingBlock.find('.not-watching').removeClass("hide").addClass("show");
            trackingBlock.find('.choose-series-info').removeClass('show').addClass('hide');
        });

}

function markWatching(id) {
    var trackingBlock = $(id);
    var selectedSeason = trackingBlock.find("#season")[0].value;
    var selectedEpisode = trackingBlock.find("#episode")[0].value;
    var watchingObject = {
        id : trackingBlock[0].id,
        season : selectedSeason,
        episode : selectedEpisode
    };
    $.post("/series/watching", watchingObject)
        .done(function() {
            location.reload();
        })
}

function changeSeason(seriesSeasonMap, seriesId) {
    console.log(seriesSeasonMap);
    var selectedSeason = $(seriesId).find("#season")[0].value;
    var episodeSelector = $(seriesId).find("#episode");
    var listOfOptions = episodeSelector.find("option");
    var episodeLength = listOfOptions.length;
    if(seriesSeasonMap[selectedSeason] > episodeLength) {
        console.log("New season is longer that previous");
        for(var i = episodeLength; i <= seriesSeasonMap[selectedSeason]; i++) {
            episodeSelector.append($("<option></option>")
                .attr("value", i)
                .text(i));
        }
    } else if (seriesSeasonMap[selectedSeason] < episodeLength) {
        console.log("New season is shorter that previous");
        for(var i = episodeLength; i > seriesSeasonMap[selectedSeason]; i--) {
            episodeSelector.find("option[value=" + i + "]").remove()
        }
    }
    episodeSelector.find("option[value=1]").attr("selected", "selected");
    console.log(episodeSelector);

}