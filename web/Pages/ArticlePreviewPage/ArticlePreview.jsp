
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>ArticlePreview</title>
</head>
<body>
<c:forEach var="article" items="${articleInfoList}">
    <div class="ui center aligned container responAvatar">
        <a href="Blog?page=user&targetUser=${article.username}"><img
                class="ui raised tiny top aligned circular centered image userAvatar " src="${article.userAvatar}"></a>
        <div class="followBtn">${article.followButton}</div>
    </div>
    <div class="ui segment keepContent">
        <a href="Blog?page=tags&tags=${article.tags}" class="ui right ribbon label">${article.tags}</a>
        <div class="ui left close rail">
            <div class="ui sticky">
                <a href="Blog?page=user&targetUser=${article.username}"><img
                        class="ui raised tiny top aligned rounded image userAvatar " src="${article.userAvatar}"></a>
            </div>
            <div class="ui flowing popup top left transition hidden">
                <div class="content">
                    <div class="header username">${article.username}</div>
                    <div class="description">
                            ${article.followButton}
                    </div>
                </div>
            </div>
        </div>
        <div>
            <h3 class="ui header"><a href="${article.retrieveAddress}">${article.title}</a>
                <span class="sub header"> ${article.postTime}
                </span>
            </h3>
        </div>
        <div class="multimediaPreview">
                ${article.multimediaPreview}
        </div>
            ${article.preview}
        <div class="ui fitted divider"></div>
        <div class="ui attached right aligned segment">
            <span class="likeNum">${article.likeNum} </span> &nbsp;&nbsp;<i
                class="ion-android-favorite-outline"></i>
        </div>
    </div>
</c:forEach>
</body>
</html>
