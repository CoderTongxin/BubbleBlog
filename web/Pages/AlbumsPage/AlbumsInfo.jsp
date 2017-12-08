
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>AlbumsInfo</title>
</head>
<body>
<div id='Image' class="listContainer">
    <div class="user">
        <div class="ui segment">
            <div id="userImageList" class="imageList">
                <c:forEach var="albumsImage" items="${albumsImageInfoList}">
                    <div class="responsive">
                        <div class="gallery">
                            <a target="_blank" href="${albumsImage.address}">
                                <img src="${albumsImage.address}" alt="${albumsImage.fileName}">
                            </a>
                            <div class="desc">${albumsImage.postTime} <a
                                    href="AlbumsChange?imageFileName=${albumsImage.fileName}&imageID=${albumsImage.id}&action=deleteImage"
                                    class="btn btn-info btn-sm">
                                <i class="trash icon"></i>
                            </a>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
        <div class="ui icon message noUserInfo" >
            <i class="frown icon"></i>
            <div class="content">
                <div class="header">
                    You don't have any Images!
                </div>
                <a href='Article?action=create'><h4>Let's write some articles!</h4></a>
            </div>
        </div>
    </div>
    <div class="spotlight">
        <div class="ui segment">
            <div id="spotlightImageList" class="imageList">
                <c:forEach var="image" items="${imageSpotlightList}">
                    <div class="responsive">
                        <div class="gallery">
                            <a target="_blank" href="${image.address}">
                                <img src="${image.address}" alt="${image.fileName}">
                            </a>
                            <div class="desc"><img class="ui avatar image" src="${image.userAvatar}"
                                                   title="${image.username}"></div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
        <div class="ui icon message noSpotlightInfo" >
            <i class="frown icon"></i>
            <div class="content">
                <div class="header">
                    Whoops!No image here!
                </div>
                <a href='Article?action=create'><h4>Let's write some articles!</h4></a>
            </div>
        </div>
    </div>
</div>
<div id='ImageEnd'></div>
<div id='Video' class="listContainer">
    <div class="user">
        <div class="ui segment">
            <div id="userVideoList" class="videoList">
                <c:forEach var="albumsVideo" items="${albumsVideoInfoList}">
                    <div class="responsiveVideo">
                        <div class="gallery">
                            <a target="_blank" href="${albumsVideo.address}">
                                <video controls>
                                    <source src="${albumsVideo.address}" type="video/mp4">
                                    <source src="${albumsVideo.address}" type="video/ogg">
                                    Your browser does not support HTML5 video.
                                </video>
                            </a>
                            <div class="desc">${albumsVideo.postTime} <a
                                    href="AlbumsChange?videoFileName=${albumsVideo.fileName}&videoID=${albumsVideo.id}&action=deleteVideo"
                                    class="btn btn-info btn-sm">
                                <i class="trash icon"></i>
                            </a>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
        <div class="ui icon message noUserInfo">
            <i class="frown icon"></i>
            <div class="content">
                <div class="header">
                    You don't have any Videos!
                </div>
                <a href='Article?action=create'><h4>Let's write some articles!</h4></a>
            </div>
        </div>
    </div>
    <div class="spotlight">
        <div class="ui segment">
            <div id="spotlightVideoList" class="videoList">
                <c:forEach var="video" items="${videoSpotlightList}">
                    <div class="responsiveVideo">
                        <div class="gallery">
                            <a target="_blank" href="${video.address}">
                                <video controls>
                                    <source src="${video.address}" type="video/mp4">
                                    <source src="${video.address}" type="video/ogg">
                                    Your browser does not support HTML5 video.
                                </video>
                            </a>
                            <div class="desc"><img class="ui avatar image" src="${video.userAvatar}"
                                                   title="${video.username}">
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
        <div class="ui icon message noSpotlightInfo">
            <i class="frown icon"></i>
            <div class="content">
                <div class="header">
                    Whoops!No video here!
                </div>
                <a href='Article?action=create'><h4>Let's write some articles!</h4></a>
            </div>
        </div>
    </div>
</div>
<div id='VideoEnd'></div>

<div id='Audio' class="listContainer">
    <div class="user">
        <div class="ui segment">
            <div id="userAudioList" class="audioList">
                <c:forEach var="albumsAudio" items="${albumsAudioInfoList}">
                    <div class="responsive">
                        <div class="gallery">
                            <a target="_blank" href="${albumsAudio.address}">
                                <audio controls>
                                    <source src="${albumsAudio.address}" type="audio/ogg">
                                    <source src="${albumsAudio.address}" type="audio/mpeg">
                                    Your browser does not support the audio tag.
                                </audio>
                            </a>
                            <div class="desc">${albumsAudio.postTime} <a
                                    href="AlbumsChange?audioFileName=${albumsAudio.fileName}&audioID=${albumsAudio.id}&action=deleteAudio"
                                    class="btn btn-info btn-sm">
                                <i class="trash icon"></i>
                            </a>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
        <div class="ui icon message noUserInfo">
            <i class="frown icon"></i>
            <div class="content">
                <div class="header">
                    You don't have any Audios!
                </div>
                <a href='Article?action=create'><h4>Let's write some articles!</h4></a>
            </div>
        </div>
    </div>
    <div class="spotlight">
        <div class="ui segment">
            <div id="spotlightAudioList" class="audioList">
                <c:forEach var="audio" items="${audioSpotlightList}">
                    <div class="responsive">
                        <div class="gallery">
                            <a target="_blank" href="${audio.address}">
                                <audio controls>
                                    <source src="${audio.address}" type="audio/ogg">
                                    <source src="${audio.address}" type="audio/mpeg">
                                    Your browser does not support the audio tag.
                                </audio>
                            </a>
                            <div style="overflow: auto">${audio.fileName}</div>
                            <div class="desc"><img class="ui avatar image" src="${audio.userAvatar}"
                                                   title="${audio.username}">
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
        <div class="ui icon message noSpotlightInfo">
            <i class="frown icon"></i>
            <div class="content">
                <div class="header">
                    Whoops!No audio here!
                </div>
                <a href='Article?action=create'><h4>Let's write some articles!</h4></a>
            </div>
        </div>
    </div>
</div>
<div id='AudioEnd'></div>
<div id='Youtube' class="listContainer">
    <div class="user">
        <div class="ui segment">
            <div id="userYoutubeList" class="youtubeList">
                <c:forEach var="albumsYoutube" items="${albumsYoutubeList}">
                    <div class="responsiveYoutube">
                        <div class="gallery">
                            <div>${albumsYoutube.address}</div>
                            <div class="desc">${albumsYoutube.postTime} <a
                                    href="AlbumsChange?videoFileName=${albumsYoutube.fileName}&videoID=${albumsYoutube.id}&action=deleteYoutube"
                                    class="btn btn-info btn-sm">
                                <i class="trash icon"></i>
                            </a>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
        <div class="ui icon message noUserInfo">
            <i class="frown icon"></i>
            <div class="content">
                <div class="header">
                    You don't have any Youtube!
                </div>
                <a href='Article?action=create'><h4>Let's write some articles!</h4></a>
            </div>
        </div>
    </div>
    <div class="spotlight">
        <div class="ui segment">
            <div id="spotlightYoutubeList" class="youtubeList">
                <c:forEach var="youtube" items="${youtubeSpotlightList}">
                    <div class="responsiveYoutube">
                        <div class="gallery">
                            <div>${youtube.address}</div>
                            <div class="desc"><img class="ui avatar image" src="${youtube.userAvatar}"
                                                   title="${youtube.username}"></div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
        <div class="ui icon message noSpotlightInfo">
            <i class="frown icon"></i>
            <div class="content">
                <div class="header">
                    Whoops!No Youtube here!
                </div>
                <a href='Article?action=create'><h4>Let's write some articles!</h4></a>
            </div>
        </div>
    </div>
</div>
<div id='YoutubeEnd'></div>
</body>
</html>
