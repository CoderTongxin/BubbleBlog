<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html class="full">
<head>

    <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1">
    <title>Login Page</title>
    <%--<meta name="google-signin-scope" content="profile email">--%>
    <meta name="google-signin-client_id"
          content="782826346139-b034vt93v6m8483o8m4jf2d94hdsbhq6.apps.googleusercontent.com">
    <script src="https://code.jquery.com/jquery-3.2.1.min.js" type="text/javascript"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.2.10/semantic.css"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/fullPage.js/2.9.4/jquery.fullpage.css"/>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.2.10/semantic.js" type="text/javascript"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/fullPage.js/2.9.4/jquery.fullpage.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Pages/LoginPage/Login.css" type="text/css">
    <script src="${pageContext.request.contextPath}/Pages/LoginPage/Login.js"></script>
</head>
<body>
<div id="container">
    <form id="form" class="ui basic segment">
        <div id="loginBlock" class="login-block">
            <div id="loginSegment" class="ui basic segment">
                <h1>Login</h1>
                <div class="loginDiv">
                    <input id="loginUsername" type="text" required name="username" class="username"
                           placeholder="Username"/>
                    <input id="loginPassword" type="password" required name="password" class="password"
                           placeholder="Password"/>
                </div>
                <input type="hidden" value="login" name="action"/>
                <button class="ui green submit fluid button" id="loginButton" type="button">Submit</button>
                <br/>
                <p>No account yet? Register <a href="${pageContext.request.contextPath}/Login?action=register">HERE</a>
                </p>

                <div id="messageContainer" class="ui message hidden">
                    <p id="message">${message}</p>
                </div>

                <div class="ui horizontal divider">Or</div>
                <div id="my-signin2" onclick="clickDetector()"></div>
            </div>
        </div>
    </form>
</div>
<script src="https://apis.google.com/js/platform.js?onload=renderButton" async defer></script>
</body>
</html>
