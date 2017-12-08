
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
    <title>Register</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <script src="https://code.jquery.com/jquery-3.2.1.min.js" type="text/javascript"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.2.10/semantic.css"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/fullPage.js/2.9.4/jquery.fullpage.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Pages/RegisterPage/Register.css" type="text/css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.2.10/semantic.js" type="text/javascript"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/fullPage.js/2.9.4/jquery.fullpage.js"></script>
    <script src="${pageContext.request.contextPath}/Pages/RegisterPage/Register.js"></script>
</head>

<body>
<div id="container">
    <form method="post">
        <div id="loginBlock" class="login-block">
            <h1>Register</h1>
            <input id="registerUsername" required pattern="[A-Za-z0-9-]{3,16}" type="text" name="username" class="username" placeholder="Username" title="Must be alphanumeric in 3-16 chars"/>
            <input id="registerPassword" required type="password" name="password" class="password"
                   placeholder="Password"/>
            <div class="ui checked checkbox">
                <input id="terms" type="checkbox" checked required>
                <label>I agree to the <a
                        href="${pageContext.request.contextPath}/Pages/TermsConditionsPage/Terms&Conditions.html">terms
                    and conditions</a></label>
            </div>
            <br><br>
            <button class="ui green submit fluid button" id="registerButton" type="button"
                    name="action">Submit</button>
            <br>
            <p>Already registered? Login <a href="${pageContext.request.contextPath}/Login?action=login">HERE</a>
            </p>
            <div id="messageContainer" class="ui message hidden">
                <p id="message">${message}</p>
            </div>
        </div>
    </form>
</div>
</body>

</html>
