
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Article Page</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.2.10/semantic.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Pages/ArticlePage/Article.css">
    <link rel="stylesheet" href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">
    <script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.2.10/semantic.js"></script>
    <script src="${pageContext.request.contextPath}/Pages/ArticlePage/Article.js"></script>
    <script src="${pageContext.request.contextPath}/Pages/NavigationBar/NavigationBar.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Pages/NavigationBar/NavigationBar.css">
    <script>
        var articleID = '${articleInfo.articleID}';
    </script>
</head>
<body>

<div>
    <jsp:include page="../NavigationBar/NavigationBar.jsp">
        <jsp:param name="NavigationBar" value=""/>
    </jsp:include>
    <div id="ArticleContainer" class="ui text justified container">
        <div class="ui segment keepContent">
            <div class="ui top attached segment">
                <h2 class="ui header"> ${articleInfo.title}
                    <div class="sub header"><span class="ui right">${articleInfo.postTime}</span></div>
                </h2>
            </div>
            <div class="ui attached segment">
                ${articleInfo.content}
            </div>
            <div id="id">${articleInfo.articleID}</div>
            <div class="ui attached segment">
                ${articleInfo.editArticle}
                ${articleInfo.deleteArticle}
                <div class="ui right floated labeled button" tabindex="0">
                    <a class="ui basic right pointing label">
                        ${articleInfo.likeNum}
                    </a>
                    <button id="likeButton" class="ui button ${articleInfo.ifLiked}">
                        <i class="heart icon ${articleInfo.ifRed}"></i>
                        <span id="ifLiked">${articleInfo.ifLiked}</span>
                    </button>
                </div>
            </div>
            <div class="ui comments">
                <h3 class="ui dividing header">Comments</h3>
                <c:forEach var="comment" items="${commentInfoList}">
                <div class="comment">
                    <a class="ui avatar">
                        <img src="${comment.userAvatar}">
                    </a>
                    <div class="content">
                        <a class="author">${comment.username}</a>
                        <div class="metadata">
                            <span class="date">${comment.postTime}</span>
                        </div>
                        <div class="text commentContent">${comment.content}</div>
                        <div class="actions">
                                ${comment.replyBtn}
                                ${comment.editBtn}
                                ${comment.deleteBtn}
                        </div>
                    </div>
                        <%--Display Replied Comments--%>
                    <div class="comments">
                        <c:forEach var="commentReply" items="${comment.commentReplyInfoList}">
                            <div class="comment">
                                <a class="avatar">
                                    <img src="${commentReply.userAvatar}">
                                </a>
                                <div class="content">
                                    <a class="author">${commentReply.username}</a>
                                    <div class="metadata">
                                        <span class="date">${commentReply.postTime}</span>
                                    </div>
                                    <div class="text">
                                            ${commentReply.content}
                                    </div>
                                    <div class="actions">
                                            ${commentReply.deleteCommentBtn}
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                    </c:forEach>
                    <form class="ui reply form" action="Comment" method="POST">
                        <div class="field">
                            <textarea name="comment"></textarea>
                            <input type="hidden" name="articleID" value="${articleInfo.articleID}">
                        </div>
                        <button id="commentSubmitBtn" class="ui icon button" type="submit" name="action" value="create">
                            Comment
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <%--Edit Box--%>
    <div id="editor" class="ui standard modal reply form editComment">
        <div class="ui basic segment">
            <form action="Comment" method="POST">
                <div class="field">
                    <label id="editorLabel" for="editor"></label>
                    <textarea id="editorTextArea" name="content" rows="10"
                              cols="100"></textarea>
                </div>
                <input id="commentIDInput" type="hidden" name="commentID" value="">
                <input id="articleIDInput" type="hidden" name="articleID" value="">

                <div class="content">
                    <button id="editorSubmitBtn" class="ui button" type="submit" name="action"
                            value="">Submit
                    </button>
                </div>
            </form>
        </div>
    </div>
</body>
</html>

