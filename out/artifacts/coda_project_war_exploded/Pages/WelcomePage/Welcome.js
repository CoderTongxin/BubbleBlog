

var userClicked = false;
$('#fullpage').fullpage({
    continuousVertical: true,
    navigationPosition: 'left',
    navigation: true,

    afterLoad: function (anchorLink, index) {

        if (index == 2) {
            $('.sequenced.images .image').transition({
                animation: 'jiggle',
                duration: 500,
                interval: 200
            });
        }
        if (index == 3) {
            $('.sequenced.icons .icon').transition({
                animation: 'bounce',
                duration: 500,
                interval: 200
            });
        }
    }
});


$(document).ready(function () {

    $(window).on('resize', function () {

        if ($(window).width() < 700) {
            $('.fouritem').addClass('container');
        } else {
            $('.fouritem').removeClass('container');
        }
    });
    $('#loginBtn').click(function () {
        checkLoginStatus("login");
    });

    $('#registerBtn').click(function () {
        checkLoginStatus("register");
    });

    $('#login').on('blur', '#registerUsername', function () {
        if ($('#registerUsername').val() != "") {
            verifyUsername($(this).val());
        }

    });

    $('#login').on('click', '#loginButton', function () {
        var username = $('#loginUsername').val();
        var password = $('#loginPassword').val();
        if (username!= ""&& password!= "") {
            $("#loginButton").attr({"class":"ui loading green submit fluid button","disabled":"disabled"});
            login(username,password);
        }else if(username == ""){
            $('#loginBlock').transition('shake');
            $("#messageContainer").attr("class","ui negative message");
            $("#message").text("Please enter your username");
        }else if(password == ""){
            $('#loginBlock').transition('shake');
            $("#messageContainer").attr("class","ui negative message");
            $("#message").text("Please enter your password");
        }
    });


    $('#login').on('click', '#registerButton', function () {
        var username = $('#registerUsername').val();
        var validity = $('#registerUsername')[0].checkValidity();
        var password = $('#registerPassword').val();
        var checked = $("#terms").prop("checked") == true;

        if (username != "" && password != "" && checked && validity) {
            $("#registerButton").attr({"class": "ui loading green submit fluid button", "disabled": "disabled"});
            register(username, password);
        } else if (username == "") {
            $('#loginBlock').transition('shake');
            $("#messageContainer").attr("class", "ui negative message");
            $("#message").text("Please enter your username");
        } else if (password == "") {
            $('#loginBlock').transition('shake');
            $("#messageContainer").attr("class", "ui negative message");
            $("#message").text("Please enter your password");
        } else if (!checked) {
            $('#loginBlock').transition('shake');
            $("#messageContainer").attr("class", "ui negative message");
            $("#message").text("Please agree the terms and conditions");
        } else if (!validity) {
            $('#loginBlock').transition('shake');
            $("#messageContainer").attr("class", "ui negative message");
            $("#message").text("Username must be alphanumeric in 3-16 chars");
        }
    });

    $('#login').on('change', function () {
        $(this).keydown(function (event) {
            if (event.keyCode == 13) {
                $('#loginButton').click();
                $('#registerButton').click();
            }
        })

    })
});

function getPage(action) {
    $.ajax({
        url: '../../Login',
        type: 'post',
        data: {action: action},
        success: function (results) {
            var form = results.substring(results.indexOf('\<body\>') + 6, results.indexOf("\</body\>"));
            $('#login').html(form).modal('show');
        }
    });
}

function checkLoginStatus(action) {
    $.ajax({
        url: '../../Login',
        type: 'post',
        data: {action: 'check'},
        success: function (status) {
            if (status == "login") {
                location.href = "../../Blog?page=home";
            } else {
                getPage(action);
            }
        }
    });
}

function verifyUsername(username) {
    if ($('#registerUsername')[0].checkValidity()) {
        $.ajax({
            url: '../../Login',
            type: 'post',
            data: {action: 'verify', username: username},
            success: function (message) {
                var message = message;
                if (message.includes("available")) {
                    $("#registerButton").removeAttr('disabled');
                    $("#messageContainer").attr("class", "ui positive message");
                    $("#message").text(message);
                } else {
                    $('#loginBlock').transition('shake');
                    $("#registerButton").attr('disabled', 'disabled');
                    $("#messageContainer").attr("class", "ui negative message");
                    $("#message").text(message);
                }
            }
        });
    }else{
        $('#loginBlock').transition('shake');
        $("#registerButton").attr('disabled', 'disabled');
        $("#messageContainer").attr("class", "ui negative message");
        $("#message").text("Username must be alphanumeric in 3-16 chars");
    }

}

function login(username, passowrd) {
    $.ajax({
        url: '../../Login',
        type: 'post',
        data: {action: 'login', username: username, password: passowrd},
        success: function (message) {

            if (message == "login") {
                location.href = "../../Blog?page=home";
            } else {
                $("#loginButton").attr("class", "ui green submit fluid button").removeAttr("disabled");
                $('#login').transition('shake');
                $("#messageContainer").attr("class", "ui negative message");
                $("#message").text(message);
            }
        }
    });
}

function register(username, passowrd) {
    $.ajax({
        url: '../../Login',
        type: 'post',
        data: {action: 'register', username: username, password: passowrd},
        success: function (message) {
            if (message == "login") {
                location.href = "../../Blog?page=home";
            } else if (message == "success") {
                $("#messageContainer").attr("class", "ui positive message");
                $("#message").text("You have successfully signed up");
                setTimeout(function () {
                    location.href = "../../Login?action=login"
                }, 1000);
            } else {
                $('#loginBlock').transition('shake');
                $("#messageContainer").attr("class", "ui negative message");
                $("#message").text(message);
                $("#registerButton").attr("class", "ui green submit fluid button").removeAttr("disabled");
            }
        }
    });
}

function signOut() {
    var auth2 = gapi.auth2.getAuthInstance();
    auth2.signOut().then(function () {
    });
}

function clickDetector() {
    userClicked = true;
}

function onSuccess(googleUser) {
    onSignIn(googleUser)
}
function onFailure(error) {
    console.log(error);
}
function renderButton() {
    gapi.signin2.render('my-signin2', {
        'scope': 'profile email',
        'width': 292,
        'height': 36,
        'longtitle': true,
        'theme': 'dark',
        'onsuccess': onSuccess,
        'onfailure': onFailure
    });
}

function onSignIn(googleUser) {

    // Useful data for your client-side scripts:
    var profile = googleUser.getBasicProfile();


    // The ID token you need to pass to your backend:
    var idToken = googleUser.getAuthResponse().id_token;
    $("#loginSegment").addClass("loading");
    if (userClicked) {
        $.ajax({
            url: '../../GoogleLogin',
            type: 'post',
            data: {idToken: idToken},
            success: function (result) {
                if (result == "success") {
                    location.href = "../../Blog?page=home";
                } else {
                    $("#message").css("color", "red").text(result);
                    $("#loginSegment").removeClass("loading");
                }
            }
        });
    } else {
        signOut();
        $("#loginSegment").removeClass("loading");
    }
}