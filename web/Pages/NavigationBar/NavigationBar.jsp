
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<body>


<div class="ui vertical center aligned segment" id="topMenu">

    <div class="ui stackable three column centered grid container">
        <div class="row">
            <div class="column invisibleforPhone">
            </div>
            <div class="column invisibleforPhone">
                <div class="ui center aligned three column grid container">
                    <div class="row">
                        <div class="column"><a href="Blog?page=home" class="item iconClass"><i
                                class="ion-ios-home-outline iconClass"></i></a></div>
                        <div class="column">
                            <a href="Albums" class="item"><i class="ion-ios-albums-outline iconClass"></i></a></div>
                        <div class="column">
                            <a href="Login?action=logout" class="item"><i
                                    class="ion-ios-upload-outline icon rotated iconClass"></i></a>
                        </div>
                    </div>
                </div>
            </div>
            <div class="column">
                <div class="ui center aligned three column grid container ">
                    <div class="row">
                        <div class="column">

                        </div>
                        <div class="column">
                            <img class="ui centered image" src="${pageContext.request.contextPath}/Pages/NavigationBar/smallLogo.png">
                        </div>
                        <div class="column">
                            <div class="ui accordion">
                                <div class="title">
                                    <a class="toc item">
                                        <i class="ion-ios-more-outline sidebarIcon"></i>
                                    </a>
                                </div>
                                <div class="content">
                                    <p><a href="Blog?page=home" class="accoritem">Home</a></p>
                                    <p><a href="Albums" class="accoritem">Ablum</a>
                                    </p>
                                    <p><a href="Login?action=logout" class="accoritem">Logout</a></p>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>
</div>
</body>
