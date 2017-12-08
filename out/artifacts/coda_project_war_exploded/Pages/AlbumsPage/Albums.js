
function loadUserImage() {
    loadInfo('Image');
}

function loadUserVideo() {

    loadInfo('Video');

    refresh();
}

function loadUserAudio() {

    loadInfo('Audio');

}
function loadUserYoutube() {

    loadInfo('Youtube');

}

function refresh() {
    handler = {
        activate: function () {
            if (!$(this).hasClass('dropdown browse')) {
                $(this)
                    .addClass('active')
                    .closest('.ui.menu')
                    .find('.item')
                    .not($(this))
                    .removeClass('active')
                ;
            }
        }
    };
    $('.menu .item').on('click', handler.activate)
    ;
}
function loadInfo(info) {
    $('#gallery').hide();
    $('#loading').show();
    $.ajax({
        url: 'Albums',
        type: 'POST',
        data: {action: info},
        success: function (data) {
            var content = data.substring(data.indexOf("<div id='" + info + "'"), data.indexOf("<div id='" + info + "End'>"));
            $('#content').html(content);
            var userContentID = "#user" + info + "List";
            var spotlightContentID = "#spotlight" + info + "List";
            var user = $(".user");
            var spotlight = $(".spotlight");
            if (!$.trim($(userContentID).html()).length) {
                $(".noUserInfo").show();
                $(userContentID).parent().hide();
            }

            if (!$.trim($(spotlightContentID).html()).length) {
                $(".noSpotlightInfo").show();
                $(spotlightContentID).parent().hide();
            }

            $("#showUsers").addClass("active").off().click(function () {
                user.show();
                spotlight.hide();
            });
            $("#showSpotlight").removeClass("active").off().click(function () {
                user.hide();
                spotlight.show();
            });
            refresh();
            $('#gallery').show();
            $('#loading').hide();
            user.show();
            spotlight.hide();
        }
    });
}
