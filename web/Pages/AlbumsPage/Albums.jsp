
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1">
    <title>UserAlbums</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.2.10/semantic.css"/>
    <link rel="stylesheet" href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.2.10/semantic.js"></script>
    <script src="${pageContext.request.contextPath}/Pages/NavigationBar/NavigationBar.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Pages/NavigationBar/NavigationBar.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Pages/AlbumsPage/Albums.css">
    <script src="${pageContext.request.contextPath}/Pages/AlbumsPage/Albums.js"></script>
</head>
<body onload="loadUserImage()">
<div>
    <jsp:include page="../NavigationBar/NavigationBar.jsp">
        <jsp:param name="NavigationBar" value=""/>
    </jsp:include>
    <div class="ui text container my" id="imageGallery">
        <div class="ui segment" id="gallery">
            <div class="ui pointing menu">
                <a class="item active" id="showUsers">
                    Yours
                </a>
                <a class="item" id="showSpotlight">
                    Spotlight
                </a>
            </div>
            <div id="verticalMenu" class="ui right close rail">
                <div class="ui vertical labeled borderless icon menu">
                    <a class="item" onclick="loadUserImage()">
                        <img class="ui centered image" src="Icons/photo.png">
                    </a>
                    <a class="item" onclick="loadUserAudio()">
                        <img class="ui centered image" src="Icons/music.png">
                    </a>
                    <a class="item" onclick="loadUserVideo()">
                        <img class="ui centered image" src="Icons/video.png">
                    </a>
                    <a class="item" onclick="loadUserYoutube()">
                        <img class="ui centered image" src="Icons/youtube.png">
                    </a>
                </div>
            </div>
            <div id="content">
            </div>
        </div>
        <div class="ui center aligned vertical segment" id="loading">
            <div class="ui icon message">
                <i class="notched circle loading icon"></i>
                <div class="content">
                    <div class="header">
                        Just one second
                    </div>
                    <p>We're loading the content for you.</p>
                </div>
            </div>
        </div>
        <div class="ui icon message" id="noContentInfo">
            <i class="frown icon"></i>
            <div class="content">
                <div class="header">
                    You don't have any content here!
                </div>
                <a href='Article?action=create'><h4>Let's write some articles!</h4></a>
            </div>
        </div>
    </div>
    <div id="fixedMenu">
        <div class="ui four item labeled borderless icon bottom fixed menu">
            <a class="item" onclick="loadUserImage()">
                <img class="ui centered image" src="Icons/photo.png">
            </a>
            <a class="item" onclick="loadUserAudio()">
                <img class="ui centered image" src="Icons/music.png">
            </a>
            <a class="item" onclick="loadUserVideo()">
                <img class="ui centered image" src="Icons/video.png">
            </a>
            <a class="item" onclick="loadUserYoutube()">
                <img class="ui centered image" src="Icons/youtube.png">
            </a>
        </div>
    </div>
</div>
</body>
</html>
