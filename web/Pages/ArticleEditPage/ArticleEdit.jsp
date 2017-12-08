
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Article Edit</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <link href="${pageContext.request.contextPath}/Pages/ArticleEditPage/google-code-prettify/prettify.css"
          rel="stylesheet">
    <link href="https://netdna.bootstrapcdn.com/twitter-bootstrap/2.3.1/css/bootstrap-combined.no-icons.min.css"
          rel="stylesheet">
    <link href="https://netdna.bootstrapcdn.com/twitter-bootstrap/2.3.1/css/bootstrap-responsive.min.css"
          rel="stylesheet">
    <link href="https://netdna.bootstrapcdn.com/font-awesome/3.0.2/css/font-awesome.css" rel="stylesheet">
    <link rel="stylesheet" href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.9.0/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/Pages/ArticleEditPage/jquery.hotkeys.js"></script>
    <script src="https://netdna.bootstrapcdn.com/twitter-bootstrap/2.3.1/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/Pages/ArticleEditPage/google-code-prettify/run_prettify.js"></script>
    <link href="${pageContext.request.contextPath}/Pages/ArticleEditPage/ArticleEdit.css" rel="stylesheet">
    <script src="${pageContext.request.contextPath}/Pages/ArticleEditPage/bootstrap-wysiwyg.js"></script>
    <script src="${pageContext.request.contextPath}/Pages/ArticleEditPage/ArticleEdit.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.2.10/semantic.css"/>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.2.10/semantic.js"></script>
    <script src="${pageContext.request.contextPath}/Pages/NavigationBar/NavigationBar.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Pages/NavigationBar/NavigationBar.css">
</head>
<body>
<div>
    <jsp:include page="../NavigationBar/NavigationBar.jsp">
        <jsp:param name="NavigationBar" value=""/>
    </jsp:include>
    <div class="ui text container edit">
        <form action="Article" method="post" id="form">
            <div class="ui text container edit">
                <div class="hero-unit">
                    <div id="alerts"></div>
                    <div class="ui form"> <%--Enter Title--%>
                        <div class="inline fields">
                            <lable>Title:</lable>
                            <input required type="text" id="title" name="title"
                                   placeholder="Your title here(max-length 50)"
                                   maxlength="50" value="${articleInfo.title}">
                        </div>
                    </div>
                    <%--Button in Editor--%>
                    <div class="btn-toolbar" data-role="editor-toolbar" data-target="#editor">
                        <div class="btn-group" id="firstBtn">
                            <a class="btn dropdown-toggle" data-toggle="dropdown" title="Font"><i class="icon-font"></i><b
                                    class="caret"></b></a>
                            <ul class="dropdown-menu">
                            </ul>
                        </div>
                        <div class="btn-group">
                            <a class="btn dropdown-toggle" data-toggle="dropdown" title="Font Size"><i
                                    class="icon-text-height"></i>&nbsp;<b class="caret"></b></a>
                            <ul class="dropdown-menu">
                                <li><a data-edit="fontSize 5"><font size="5">Huge</font></a></li>
                                <li><a data-edit="fontSize 3"><font size="3">Normal</font></a></li>
                                <li><a data-edit="fontSize 1"><font size="1">Small</font></a></li>
                            </ul>
                        </div>
                        <div class="btn-group">
                            <a class="btn" data-edit="bold" title="Bold (Ctrl/Cmd+B)"><i class="icon-bold"></i></a>
                            <a class="btn" data-edit="italic" title="Italic (Ctrl/Cmd+I)"><i
                                    class="icon-italic"></i></a>
                            <a class="btn" data-edit="strikethrough" title="Strikethrough"><i
                                    class="icon-strikethrough"></i></a>
                            <a class="btn" data-edit="underline" title="Underline (Ctrl/Cmd+U)"><i
                                    class="icon-underline"></i></a>
                        </div>
                        <div class="btn-group">
                            <a class="btn" data-edit="insertunorderedlist" title="Bullet list"><i
                                    class="icon-list-ul"></i></a>
                            <a class="btn" data-edit="insertorderedlist" title="Number list"><i
                                    class="icon-list-ol"></i></a>
                            <a class="btn" data-edit="outdent" title="Reduce indent (Shift+Tab)"><i
                                    class="icon-indent-left"></i></a>
                            <a class="btn" data-edit="indent" title="Indent (Tab)"><i class="icon-indent-right"></i></a>
                        </div>
                        <div class="btn-group">
                            <a class="btn" data-edit="justifyleft" title="Align Left (Ctrl/Cmd+L)"><i
                                    class="icon-align-left"></i></a>
                            <a class="btn" data-edit="justifycenter" title="Center (Ctrl/Cmd+E)"><i
                                    class="icon-align-center"></i></a>
                            <a class="btn" data-edit="justifyright" title="Align Right (Ctrl/Cmd+R)"><i
                                    class="icon-align-right"></i></a>
                            <a class="btn" data-edit="justifyfull" title="Justify (Ctrl/Cmd+J)"><i
                                    class="icon-align-justify"></i></a>
                        </div>
                        <div class="btn-group">
                            <a class="btn dropdown-toggle" data-toggle="dropdown" title="Hyperlink"><i
                                    class="icon-link"></i></a>
                            <div class="dropdown-menu input-append">
                                <input class="span2" placeholder="URL" type="text" data-edit="createLink"/>
                                <button class="btn" type="button">Add</button>
                            </div>
                            <a class="btn" data-edit="unlink" title="Remove Hyperlink"><i class="icon-cut"></i></a>
                        </div>

                        <div class="btn-group" id="youtube">
                            <a class="btn" title="Insert Youtube video"><img class="image iconImage"
                                                                             src="http://internetvi.ru/wp-content/uploads/2012/06/e33c1de5c8bd4c4c0bdaba9cd3657a6d.png"/></a>
                        </div>
                        <div class="btn-group">
                            <a class="btn" title="Insert image"><label for="image-input" class="myLabel">
                                <img class="image iconImage"
                                     src="https://cdn4.iconfinder.com/data/icons/ionicons/512/icon-image-128.png">
                            </label>
                                <input id="image-input" class="input" type="file" accept=".jpg, .gif,.png"></a>
                        </div>

                        <div class="btn-group">
                            <a class="btn" title="Insert audio"><label for="audio-input" class="myLabel">
                                <img class="image iconImage"
                                     src="https://upload.wikimedia.org/wikipedia/commons/thumb/2/21/Speaker_Icon.svg/1024px-Speaker_Icon.svg.png">
                            </label>
                                <input id="audio-input" class="input" type="file" accept=".mp3,.wav"></a>
                        </div>
                        <div class="btn-group">
                            <a class="btn" title="Insert video"><label for="video-input" class="myLabel">
                                <img class="image iconImage" src="http://simpleicon.com/wp-content/uploads/video.png">
                            </label>
                                <input id="video-input" class="input" type="file" accept=".ogg,.mp4"></a>
                        </div>

                        <div class="btn-group">
                            <a class="btn" data-edit="undo" title="Undo (Ctrl/Cmd+Z)"><i class="icon-undo"></i></a>
                            <a class="btn" data-edit="redo" title="Redo (Ctrl/Cmd+Y)"><i class="icon-repeat"></i></a>
                        </div>
                        <input type="text" data-edit="inserttext" id="voiceBtn" x-webkit-speech="">
                    </div>

                    <%--Editor--%>
                    <div id="editor">
                        ${articleInfo.content}
                    </div>
                    <div class="ui form">
                        <div class="inline fields">
                            <label>Tag</label>
                            <select required class="ui fluid normal dropdown" name="tag">
                                <option value="">tags</option>
                                <option value="Movie">Movie</option>
                                <option value="Food">Food</option>
                                <option value="Science">Science</option>
                                <option value="Technology">Technology</option>
                                <option value="Business">Business</option>
                                <option value="Health">Health</option>
                                <option value="Music">Music</option>
                                <option value="Education">Education</option>
                                <option value="Other" selected="selected">Other</option>
                            </select>
                        </div>
                    </div>
                    ${hiddenElement}
                    <div class="submitBtnSet"> ${deleteElement}${submitElement}</div>

                </div>
            </div>
            <input type="hidden" name="content" id="articleContentSubmit">
        </form>
        <c:if test="${information != null}">
            <p id="errorInfo">${information}</p>
        </c:if>
    </div>
</body>
</html>
