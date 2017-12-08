
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1">
    <title>Account Setting</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.2.10/semantic.css"/>
    <link rel="stylesheet" href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">
    <script
            src="https://code.jquery.com/jquery-3.2.1.min.js"
            integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4="
            crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/semantic-ui/2.2.10/semantic.js"></script>
    <script src="${pageContext.request.contextPath}/Pages/NavigationBar/NavigationBar.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Pages/NavigationBar/NavigationBar.css">
    <script src="${pageContext.request.contextPath}/Pages/UserProfilePage/EditProfile.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/Pages/UserProfilePage/EditProfile.css">
    <script>
        var username = '${userProfile.username}';
        var googleUser = '${googleUser}';
        var selection='${userProfile.gender}';
    </script>
</head>
<body>

<div class="pusher">

    <jsp:include page="../NavigationBar/NavigationBar.jsp">
        <jsp:param name="NavigationBar" value=""/>
    </jsp:include>

    <%--Edit Profile--%>
    <div class="ui text container">



        <div class="ui segment">

            <!--id for update profile form should be changed-->
            <div class="ui fitted horizontal divider">Change Details</div>
            <div id="avatarDiv">
                <img src="${userProfile.avatar}" class="rounded" title="click to change avatar" onclick="showWindow2()"/>
            </div>
            <%--<button class="ui button"  id="userIcon"  onclick="showWindow2()">change</button>--%>

            <form class="ui form" id="profileForm" action="Profile" method="post">
                <div class="two fields">
                    <div class="field">
                        <label>Username:</label><input placeholder="Please type your username(3-16 letters or numbers)" id="newUsername" type="text" pattern="[A-Za-z0-9-]{3,16}" name="username"
                                                       value="${userProfile.username}" /></div>
                    <div class="field">
                        <label>Firstname:</label><input type="text" placeholder="Please type your first name(2-16 letters)" pattern="[A-Za-z]{2,16}" name="firstName" value="${userProfile.firstName}"
                    />
                    </div>
                </div>
                <div class="two fields" >
                    <div class="field">
                        <div id="usernameMessageContainer" class="ui message hidden">
                            <p id="usernameMessage"></p>
                        </div>
                    </div>
                </div>
                <div class="two fields">
                    <div class="field">
                        <label>Lastname:</label> <input placeholder="Please type your last name(2-16 letters)" type="text" pattern="[A-Za-z]{2,16}" name="lastName" value="${userProfile.lastName}"
                    /></div>
                    <div class="field">
                        <label>Email: </label><input type="email" placeholder="Please type your email" name="email" value="${userProfile.email}"
                    /></div>
                </div>
                <div class="two fields">
                    <div class="field">
                        <label>Birthday:</label> <input required type="date" name="birthday" value="${userProfile.birthDate}"
                    /></div>
                    <div class="field">
                        <label>Gender:</label><select name="gender" id="gender">
                        <option value="Female">Female</option>
                        <option value="Male">Male</option>
                    </select>
                    </div>
                </div>
                <div class="ui error message"></div>
                <input  type="hidden" name="profileAction" value="Update" id="profileAction">
                <button class="ui green submit button" id="updateBtn">Update</button>
                <button class="ui red button" type="button" id="deleteBtn">Delete</button>
            </form>


            <div id="passwordDivider" class="ui fitted horizontal divider" style="display: none">Change Password</div>
            <form class="ui form" id="passwordForm" action="ChangePassword" method="post" style="display: none">
                <div class="field">
                    <label>Password: </label><input id="password" type="password" name="password"
                                                    placeholder="Please Enter Your Password" />
                </div>
                <div class="field">
                    <label>New Password:</label> <input id="newPassword" type="password" name="newPassword"
                                                        placeholder="Please Enter Your New Password"
                /></div>
                <div class="field">
                    <label>Re-enter New Password:</label> <input id="reNewPassword" type="password" name="reNewPassword"
                                                                 placeholder="Please Re-Enter Your New Password"
                />
                </div>
                <div id="passwordMessageContainer"
                     style="margin-top: 0 !important; max-width: 300px; padding: 10px 15px !important;"
                     class="ui message hidden">
                    <p id="passwordMessage"></p>
                </div>
                <button id="passwordBtn" class="ui submit button" type="button" value="Update">Update</button>
            </form>
        </div>
        <br>
    </div>
</div>

<div class="ui modal" id="iconWindow">
    <i class="close icon"></i>
    <div class="header">
        Avatar
    </div>
    <div class="image content">

        <div class="avatarInfo">
            <div>
                <div class="center">
                    <img class="avatar" id="icon" src="${userProfile.avatar}">
                </div>

                <br>
                <div class="center">
                    <div class="ui fitted horizontal divider">Options</div>
                    <c:forEach var="localIcon" items="${iconList}">
                        <div class="img">
                            <img src="${localIcon}" class="rounded" id="${localIcon}"/>
                        </div>
                    </c:forEach>
                    <br>
                    <p></p>
                    <button class="ui blue labeled icon small button" id="fileButton">
                        <i class="inverted upload icon"></i>
                        Upload
                    </button>
                    <form action="AvatarEdit" id="avatarForm" method="post" style="margin: 0" enctype="multipart/form-data">
                        <input type="hidden" name="result" id="result" value="${userProfile.avatar}">
                        <input style="display: none" type="file" id="imageFile" accept=".jpg, .gif,.png"
                               name="icon"/>
                    </form>

                </div>
            </div>
        </div>
    </div>
    <div class="actions">
        <button class="ui deny button">
            Cancel
        </button>
        <button id="submitAvatarChange" class="ui positive right icon button">
            <i id="saveIcon" class="list icon"></i>
            Save
        </button>
    </div>
</div>

<div class="ui small modal" id="deleteWindow">
    <div class="header">Delete your account</div>
    <div class="content">Are you sure you want to delete your account and all files?</div>
    <div class="actions">
        <div class="ui negative button">No</div>
        <button class="ui positive right labled icon button" id="deleteAccountBtn">Yes<i class="checkmark icon"></i> </button>
    </div>
</div>
</body>
</html>