package code_project.Servlet;

import code_project.DAO.CommentInfoDAO;
import code_project.DAO.UserInfoDAO;
import code_project.Info.UserInfo;
import code_project.db.MySQL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class CommentServlet extends HttpServlet {
    private MySQL mySQL = new MySQL();


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String action = request.getParameter("action");
        switch (action) {
            case "create":
                createCommentInfo(request, response);
                break;
            case "delete":
                deleteCommentInfo(request, response);
                break;
            case "deleteCommentReply":
                deleteCommentReplyInfo(request, response);
                break;
            case "update":
                updateCommentInfo(request, response);
                break;
            case "reply":
                replyCommentInfo(request, response);
                break;
            case "like":
                likeCommentInfo(request, response);
                break;
            case "dislike":
                dislikeCommentInfo(request, response);
                break;
        }

        String articleID = request.getParameter("articleID");
        response.sendRedirect("Article?action=retrieve&articleID=" + articleID);
    }


    private void createCommentInfo(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        String articleID = request.getParameter("articleID");
        String content = request.getParameter("comment");
        String postTime = CommentInfoDAO.getCurrentTimeStamp();
        try {
            UserInfo userInfo = UserInfoDAO.getUserInfo(mySQL, username);
            String userAvatar = userInfo.getAvatar();
            CommentInfoDAO.createCommentInfo(mySQL, content, postTime, username, articleID, userAvatar);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    private void deleteCommentInfo(HttpServletRequest request, HttpServletResponse response) {
        int commentID = Integer.parseInt(request.getParameter("commentID"));
        try {
            CommentInfoDAO.deleteCommentInfo(mySQL, commentID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteCommentReplyInfo(HttpServletRequest request, HttpServletResponse response) {
        int commentReplyID = Integer.parseInt(request.getParameter("commentReplyID"));
        try {
            CommentInfoDAO.deleteCommentReplyInfo(mySQL, commentReplyID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void updateCommentInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        int commentID = Integer.parseInt(request.getParameter("commentID"));
        String content = request.getParameter("content");
        String postTime = CommentInfoDAO.getCurrentTimeStamp();
        try {
            CommentInfoDAO.updateCommentInfo(mySQL, commentID, content, username, postTime);
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        response.getWriter().write("success");
    }

    private void replyCommentInfo(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        String content = request.getParameter("content");
        String postTime = CommentInfoDAO.getCurrentTimeStamp();
        String username = (String) session.getAttribute("username");
        int commentId = Integer.parseInt(request.getParameter("commentID"));
        int articleID = Integer.parseInt(request.getParameter("articleID"));
        try {
            UserInfo userInfo = UserInfoDAO.getUserInfo(mySQL, username);
            String userAvatar = userInfo.getAvatar();
            CommentInfoDAO.replyCommentInfo(mySQL, content, username, postTime, commentId, articleID, userAvatar);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void likeCommentInfo(HttpServletRequest request, HttpServletResponse response) {
    }

    private void dislikeCommentInfo(HttpServletRequest request, HttpServletResponse response) {

    }

}
