package code_project.Servlet;

import code_project.DAO.*;
import code_project.Info.*;
import code_project.Security.LoginStatus;
import code_project.db.MySQL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

public class AlbumsServlet extends HttpServlet {
    MySQL DB = new MySQL();
    private String username;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        LoginStatus.verifyStatus(request, response);
        HttpSession session = request.getSession(true);
        username = (String) session.getAttribute("username");
        response.setContentType("text/html");
        String action = "";
        if (request.getParameter("action") != null) {
            action = request.getParameter("action");
            switch (action) {
                case "Image":
                    loadImage(request);
                    break;
                case "Video":
                    loadVideo(request);
                    break;
                case "Audio":
                    loadAudio(request);
                    break;
                case "Youtube":
                    loadYoutube(request);
                    break;
            }
            request.getRequestDispatcher("Pages/AlbumsPage/AlbumsInfo.jsp").forward(request, response);
        } else {
            request.getRequestDispatcher("Pages/AlbumsPage/Albums.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }

    private void loadImage(HttpServletRequest request) {

        List<AlbumsImageInfo> albumsImageInfoList = AlbumsImageDAO.getAlbumsImageList(DB, username);
        List<AlbumsImageInfo> imageSpotlightList = AlbumsImageDAO.getAllAlbumsImageList(DB);
        request.setAttribute("albumsImageInfoList", albumsImageInfoList);
        request.setAttribute("imageSpotlightList", imageSpotlightList);
    }

    private void loadVideo(HttpServletRequest request) {
        List<AlbumsVideoInfo> albumsVideoInfoList = AlbumsVideoDAO.getAlbumsVideoList(DB, username);
        List<AlbumsVideoInfo> videoSpotlightList = AlbumsVideoDAO.getAllAlbumsVideoList(DB);
        request.setAttribute("albumsVideoInfoList", albumsVideoInfoList);
        request.setAttribute("videoSpotlightList", videoSpotlightList);

    }

    private void loadAudio(HttpServletRequest request) {
        List<AlbumsAudioInfo> albumsAudioInfoList = AlbumsAudioDAO.getAlbumsAudioList(DB, username);
        List<AlbumsAudioInfo> audioSpotlightList = AlbumsAudioDAO.getAllAlbumsAudioList(DB);
        request.setAttribute("albumsAudioInfoList", albumsAudioInfoList);
        request.setAttribute("audioSpotlightList", audioSpotlightList);

    }

    private void loadYoutube(HttpServletRequest request) {
        List<AlbumsVideoInfo> albumsYoutubeList = AlbumsVideoDAO.getYoutubeList(DB, username);
        List<AlbumsVideoInfo> youtubeSpotlightList = AlbumsVideoDAO.getAllAlbumsYoutubeList(DB);
        request.setAttribute("albumsYoutubeList", albumsYoutubeList);
        request.setAttribute("youtubeSpotlightList", youtubeSpotlightList);


    }

}
