package code_project.Servlet;

import code_project.DAO.AlbumsAudioDAO;
import code_project.DAO.AlbumsImageDAO;
import code_project.DAO.AlbumsVideoDAO;
import code_project.DAO.UserInfoDAO;
import code_project.Info.UserInfo;
import code_project.Security.LoginStatus;
import code_project.db.MySQL;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

public class AlbumsChangeServlet extends HttpServlet {
    MySQL mySQL = new MySQL();
    private static final long serialVersionUID = 1L;
    private String filePath;
    private int maxFileSize = 5120 * 5120;
    private int maxMemSize = 5120 * 5120;
    private File file;
    static String fileName;
    String avatarFileName;


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        LoginStatus.verifyStatus(request, response);
        HttpSession session = request.getSession(true);
        response.setContentType("text/html");
        String username = (String) session.getAttribute("username");
        UserInfo userInfo = UserInfoDAO.getUserInfo(mySQL, username);
        avatarFileName = userInfo.getAvatar();
        if (request.getParameter("action") != null) {
            switch (request.getParameter("action")) {
                case "deleteAudio":
                    deleteAlbumsAudio(request, response, username);
                    break;
                case "deleteVideo":
                    deleteAlbumsVideo(request, response, username);
                    break;
                case "deleteImage":
                    deleteAlbumsImage(request, response, username);
                    break;
                case "deleteYoutube":
                    deleteYoutube(request, response, username);
                    break;
                case "createYoutube":
                    createYoutube(request, response, username);
            }
        } else {
            addNewThingsToAlbums(request, response, username);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }

    private void deleteYoutube(HttpServletRequest request, HttpServletResponse response, String username) {
        try {
            int youtubeID = Integer.valueOf(request.getParameter("videoID"));
            AlbumsVideoDAO.deleteAlbumsVideoInfo(mySQL, username, youtubeID);
            response.sendRedirect("Albums");
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }


    private void deleteAlbumsAudio(HttpServletRequest request, HttpServletResponse response, String username) {
        try {
            String audioFileName = request.getParameter("audioFileName");
            int id = Integer.valueOf(request.getParameter("audioID"));
            AlbumsAudioDAO.deleteAlbumsAudioInfo(mySQL, username, id);
            deleteFile(username, audioFileName, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteAlbumsImage(HttpServletRequest request, HttpServletResponse response, String username) {
        try {
            String imageFileName = request.getParameter("imageFileName");
            int id = Integer.valueOf(request.getParameter("imageID"));
            AlbumsImageDAO.deleteAlbumsImageInfo(mySQL, username, id);
            deleteFile(username, imageFileName, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteAlbumsVideo(HttpServletRequest request, HttpServletResponse response, String username) {
        try {
            String videoFileName = request.getParameter("videoFileName");
            int id = Integer.valueOf(request.getParameter("videoID"));
            AlbumsVideoDAO.deleteAlbumsVideoInfo(mySQL, username, id);
            deleteFile(username, videoFileName, response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteFile(String username, String fileName, HttpServletResponse response) {
        ServletContext servletContext = getServletContext();
        String imagePath = servletContext.getRealPath("User-Info/" + username + "/");
        File file = new File(imagePath + fileName);
        file.delete();
        try {
            response.sendRedirect("Albums");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addNewThingsToAlbums(HttpServletRequest request, HttpServletResponse response, String username) throws IOException, ServletException {

        ServletContext servletContext = getServletContext();

        String fullFilePath = servletContext.getRealPath("User-Info");
        File folder = new File(fullFilePath);
        if (!folder.exists()) {
            folder.mkdir();
        }
        //create the user's own folder under User-Info folder
        File Userfolder = new File(fullFilePath + "/" + username);
        if (!Userfolder.exists()) {
            Userfolder.mkdir();
        }

        //get the user's folder path
        filePath = servletContext.getRealPath("User-Info/" + username + "/");

        //create directory
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // maximum size that will be stored in memory
        factory.setSizeThreshold(maxMemSize);
        // Location to save data that is larger than maxMemSize.
//        factory.setRepository(new File("C:\\temp"));
        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);
        // maximum file size to be uploaded.
//        upload.setSizeMax(maxFileSize);
        try {
            createNewFile(upload, request, response, filePath, username);
            //request.getRequestDispatcher("Pages/ArticleEditPage/ArticleEdit.jsp").forward(request, response);
        } catch (Exception e) {
            response.setContentType("text/plain");
            response.getWriter().write("Fail to upload the file, please try again");
        }

    }

    private void createNewFile(ServletFileUpload upload, HttpServletRequest request, HttpServletResponse response, String filePath, String username) throws IOException, ServletException, FileUploadException, SQLException {

        String fileAddress = "";

        // Parse the request to get file items.
        List fileItems = upload.parseRequest(request);
        // Process the uploaded file items
        Iterator i = fileItems.iterator();
        //add file
        while (i.hasNext()) {
            FileItem fi = (FileItem) i.next();

            if (!fi.isFormField()) {
                fileName = fi.getName();
                if (fileName.contains(".jpg") || fileName.contains(".gif") || fileName.contains(".png")) {
                    fileAddress = createImage(fi, filePath, username, fileName);
                } else if (fileName.contains(".mp3") || fileName.contains(".wav")) {
                    fileAddress = createAudio(fi, fileName, filePath, username);
                } else if (fileName.contains(".mp4") || fileName.contains(".ogg")) {
                    fileAddress = createVideo(fi, fileName, filePath, username);
                }
            }
        }

        response.setContentType("text/plain");
        response.getWriter().write(fileAddress);
    }

    private String createImage(FileItem fileItem, String filePath, String username, String fileName) {

        String fileAddress = "<img src='User-Info/" + username + "/" + fileName + "'>";

        String extension = fileName.substring(fileName.indexOf(".") + 1, fileName.length());

        if (!checkFile(filePath, fileName)) {
            file = new File(filePath + fileName);
            try {
                fileItem.write(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
            BufferedImage AlbumImage = null;
            try {
                AlbumsImageDAO.createAlbumsImageInfo(mySQL, username, "User-Info/" + username + "/" + fileName, fileName, avatarFileName);
                if (!fileName.toLowerCase().contains(".gif")) {
                    AlbumImage = ImageIO.read(file);

                    //check the size of the image
                    if ((AlbumImage.getWidth() > 1000)) {

                        Double width = (double) AlbumImage.getWidth();
                        Double height = (double) AlbumImage.getHeight();
                        double r = (height / width) * 1000;
                        BufferedImage image = new BufferedImage(1000, (int) r, BufferedImage.TYPE_INT_RGB);
                        image.createGraphics().drawImage(ImageIO.read(new File(filePath + fileName)).getScaledInstance(1000, (int) r, Image.SCALE_SMOOTH), 0, 0, null);
                        //write the thumbnail
                        ImageIO.write(image, extension, file);
                    }
                }
            } catch (Exception e) {
                file.delete();
                e.printStackTrace();
                return "Fail to upload the image!";
            }
        }
        return fileAddress;
    }

    private String createVideo(FileItem fileItem, String fileName, String filePath, String username) {

        String address = "<video width='400px' height='200px' controls >\n" +
                "  <source src=\"User-Info/" + username + "/" + fileName + "\" type=\"video/mp4\">\n" +
                "  <source src=\"User-Info/" + username + "/" + fileName + "\" type=\"video/ogg\">\n" +
                "  Your browser does not support the video tag.\n" +
                "</video>";

        if (!checkFile(filePath, fileName)) {
            try {
                File file = new File(filePath + fileName);
                fileItem.write(file);
                AlbumsVideoDAO.createAlbumsVideoInfo(mySQL, username, "User-Info/" + username + "/" + fileName, fileName, avatarFileName);
                return address;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return address;
    }


    private void createYoutube(HttpServletRequest request, HttpServletResponse response, String username) throws IOException {

        String address = request.getParameter("youtubeAddress");

        if (!AlbumsVideoDAO.checkAlbumsYoutube(mySQL, address, username)) {
            try {
                AlbumsVideoDAO.createAlbumsVideoInfo(mySQL, username, address, "No file", avatarFileName);

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        response.setContentType("text/plain");
        response.getWriter().write(address);
    }


    private String createAudio(FileItem fileItem, String fileName, String filePath, String username) {

        String address = "<div><audio controls width='200px'>\n" +
                "  <source src=\"User-Info/" + username + "/" + fileName + "\" type=\"audio/ogg\">\n" +
                "  <source src=\"User-Info/" + username + "/" + fileName + "\" type=\"audio/mpeg\">\n" +
                "  Your browser does not support the audio tag.\n" +
                "</audio><div>";
        if (!checkFile(filePath, fileName)) {
            try {
                File file = new File(filePath + fileName);
                fileItem.write(file);
                AlbumsAudioDAO.createAlbumsAudioInfo(mySQL, username, "User-Info/" + username + "/" + fileName, fileName, avatarFileName);
                return address;
            } catch (Exception e) {
                return "Fail to upload the audio!";
            }
        }
        return address;
    }

    private boolean checkFile(String filePath, String fileName) {
        File folder = new File(filePath);
        if (folder.listFiles() != null) {
            File[] listOfFiles = folder.listFiles();
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    if (file.getName().equals(fileName)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
