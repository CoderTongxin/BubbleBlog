package code_project.Servlet;

import code_project.DAO.UserInfoDAO;
import code_project.DAO.FollowInfoDAO;
import code_project.Info.FollowInfo;
import code_project.Info.UserInfo;
import code_project.Security.LoginStatus;
import code_project.db.MySQL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class FollowServlet extends HttpServlet {

    private MySQL mySQL = new MySQL();
    String username;
    String action;
    String followUsername;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        LoginStatus.verifyStatus(request, response);
        HttpSession session = request.getSession();
        username = (String) session.getAttribute("username");
        action = request.getParameter("action");
        followUsername = request.getParameter("followUsername");
        switch (action) {
            case "follow":
                follow(response);
                break;
            case "unfollow":
                unfollow(response);
                break;
            case "getFollowInfo":
                getFollowInfo(request, response);

                break;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }


    private void follow(HttpServletResponse response) throws IOException {

        try {
            FollowInfoDAO.follow(mySQL, username, followUsername);

        } catch (SQLException e) {
            response.sendError(500, "Fail to talk to the database");
        }
    }


    private void unfollow(HttpServletResponse response) throws IOException {

        try {
            FollowInfoDAO.unfollow(mySQL, username, followUsername);
        } catch (SQLException e) {
            response.sendError(500, "Fail to talk to the database");
        }
    }

    private void getFollowInfo(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        FollowInfo followInfo = FollowInfoDAO.getFollowInfo(mySQL, username);
        List<String> followsNameList = followInfo.getFollows();
        List<String> followersNameList = followInfo.getFollowers();
        try {
            List<UserInfo> followsList = UserInfoDAO.getUsersList(mySQL, followsNameList);
            List<UserInfo> followersList = UserInfoDAO.getUsersList(mySQL, followersNameList);

            for (UserInfo follow : followsList) {
                follow.setFollowStatus(" <button class=\"ui button unfollow\" title=\"unfollow\"><i class=\"remove user icon\"></i></button>");
                for (UserInfo follower : followersList) {
                    if (follow.getUsername().equals(follower.getUsername())) {
                        follow.setFollowStatus("<button class=\"ui button unfollow\" title=\"unfollow\"><i class=\"heart icon\"></i></button>");
                        break;
                    }
                }
            }
            for (UserInfo follower : followersList) {
                follower.setFollowStatus("<button class=\"ui button follow\" title=\"follow\"><i class=\"add user icon\"></i></button>");
                for (UserInfo follow : followsList) {
                    if (follower.getUsername().equals(follow.getUsername())) {
                        follower.setFollowStatus("<button class=\"ui button unfollow\" title=\"unfollow\"><i class=\"heart icon\"></i></button>");
                        break;
                    }
                }
            }

            request.setAttribute("followsList", followsList);
            request.setAttribute("followsNumber", followsList.size());
            request.setAttribute("followersList", followersList);
            request.setAttribute("followersNumber", followersList.size());
            request.getRequestDispatcher("Pages/FollowAndFollowerPage/FollowAndFollower.jsp").forward(request, response);
        } catch (SQLException e) {
            response.sendError(500, "Fail to talk to the database");
        }
    }

}
