

$(document).ready(function () {
    $("#registerUsername").blur(function () {
        if ($('#registerUsername').val() != "") {
            verifyUsername($(this).val());
        }
    });

    $(this).keydown(function (event) {
        if (event.keyCode == 13) {
            $('#registerButton').click();
        }
    });

    $('#registerButton').click(function () {
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
});

function register(username, passowrd) {
    $.ajax({
        url: 'Login',
        type: 'post',
        data: {action: 'register', username: username, password: passowrd},
        success: function (message) {
            if (message == "login") {
                location.href = "Blog?page=home";
            } else if (message == "success") {
                $("#messageContainer").attr("class", "ui positive message");
                $("#message").text("You have successfully sign up");
                setTimeout(function () {
                    location.href = "Login?action=login"
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

function verifyUsername(username) {
    if ($('#registerUsername')[0].checkValidity()) {
        $.ajax({
            url: 'Login',
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
                // $("#message").text(message);
            }
        });
    }else{
        $('#loginBlock').transition('shake');
        $("#registerButton").attr('disabled', 'disabled');
        $("#messageContainer").attr("class", "ui negative message");
        $("#message").text("Username must be alphanumeric in 3-16 chars");
    }
}
