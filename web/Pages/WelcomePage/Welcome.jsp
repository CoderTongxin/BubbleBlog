
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1">
    <meta name="google-signin-client_id"
          content="782826346139-b034vt93v6m8483o8m4jf2d94hdsbhq6.apps.googleusercontent.com">
    <title>Welcome</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.2.10/semantic.css"/>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/fullPage.js/2.9.4/jquery.fullpage.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/Pages/WelcomePage/Welcome.css">
    <script src="https://code.jquery.com/jquery-3.2.1.min.js" type="text/javascript"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.2.10/semantic.js" type="text/javascript"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/fullPage.js/2.9.4/jquery.fullpage.js"></script>
</head>
<body>
<div id="fullpage">
    <div class="section active" id="section0">
        <div class="ui middle aligned center aligned grid">
            <div class="column">
                <div class="ui text container">
                    <h1 class="ui inverted header">
                        Bubble Blog
                    </h1>
                    <h2>Where great minds collide.</h2>

                    <div class="twoButtons">
                        <div id="loginBtn" class="huge ui animated  inverted button" tabindex="0">
                            <a class="visible content">Login</a>
                            <div class="hidden content">
                                <i class="right arrow icon"></i>
                            </div>
                        </div>
                        <div id="registerBtn" class="huge ui animated  inverted button" tabindex="0">
                            <a class="visible content">Sign Up</a>
                            <div class="hidden content">
                                <i class="right arrow icon"></i>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="ui small modal segment" id="login"></div>
    </div>
    <div class="section" id="section1">
        <div class="ui middle aligned center aligned grid">
            <div class="column">
                <div class="ui small sequenced images">
                    <img src="https://semantic-ui.com/images/avatar2/large/elyse.png" class="ui circular image">
                    <img src="https://semantic-ui.com/images/avatar2/large/matthew.png" class="ui circular image">
                    <span class="ui fouritem">
                        <img src="https://semantic-ui.com/images/avatar2/large/kristy.png" class="ui circular image">
                        <img src="https://semantic-ui.com/images/avatar/large/jenny.jpg" class="ui circular image">
                    </span>
                </div>
                <h1>Team Coda</h1>
                <h2>Your World. Delivered by us.</h2>
            </div>
        </div>
    </div>
    <div class="section" id="section2">
        <div class="ui middle aligned center aligned grid">
            <div class="column">
                <div class="ui small sequenced icons">
                    <i class="inverted massive talk outline icon"></i>
                    <i class="inverted massive image icon"></i>
                    <span class="ui fouritem">
                         <i class="inverted massive music icon"></i>
                         <i class="inverted massive record icon"></i>
                    </span>
                </div>
                <h2>Put anything you want here. </h2>
                <h1>Seriously.</h1>
            </div>
        </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/Pages/WelcomePage/Welcome.js"></script>
</body>
</html>
