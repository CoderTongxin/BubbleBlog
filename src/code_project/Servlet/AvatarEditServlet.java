package code_project.Servlet;

import code_project.DAO.UserInfoDAO;
import code_project.Security.LoginStatus;
import code_project.db.MySQL;
import org.apache.commons.fileupload.FileItem;
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AvatarEditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private String filePath;
    private int maxFileSize = 5120 * 5120;
    private int maxMemSize = 5120 * 5120;
    private File file;
    private String username;
    private String avatarFilePath;
    static String fileName;
    MySQL DB = new MySQL();
    ServletContext servletContext;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        LoginStatus.verifyStatus(request, response);
        HttpSession session = request.getSession(true);
        response.setContentType("text/html");
        username = (String) session.getAttribute("username");
        LoginStatus.verifyStatus(request, response);
        //get User-Info path
        servletContext = getServletContext();
        String fullFilePath = servletContext.getRealPath("User-Info");
        response.setContentType("text/html");

        //create User-Info folder
        File folder = new File(fullFilePath);

        if (!folder.exists()) {
            folder.mkdir();
        }

        //create the user's own folder under User-Info folder
        File Userfolder = new File(fullFilePath + "/" + username);

        if (!Userfolder.exists()) {
            Userfolder.mkdir();
        }

        File avatarFile = new File(fullFilePath + "/" + username + "/avatar");
        if (!avatarFile.exists()) {
            avatarFile.mkdir();
        }
        //get the user's folder path
        filePath = fullFilePath + "/" + username + "/avatar/";

        //create directory
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // maximum size that will be stored in memory
        factory.setSizeThreshold(maxMemSize);
        // Location to save data that is larger than maxMemSize.
        factory.setRepository(new File("C:\\temp"));
        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);
        // maximum file size to be uploaded.
        upload.setSizeMax(maxFileSize);

        deleteLocalAvatar(filePath);

        createUserIcon(upload, request, filePath);

        try {
            UserInfoDAO.updateUserIcon(DB, avatarFilePath, username);
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect("Profile");
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        response.setContentType("text/html");
        LoginStatus.verifyStatus(request, response);
        doPost(request, response);
    }

    public static List<String> iconList() {
        List<String> iconList = new ArrayList<>();
        iconList.add("DefaultAvatar/elyse.png");
        iconList.add("DefaultAvatar/helen.jpg");
        iconList.add("DefaultAvatar/jenny.jpg");
        iconList.add("DefaultAvatar/matthew.png");
        iconList.add("DefaultAvatar/molly.png");
        return iconList;
    }

    private void deleteLocalAvatar(String filePath) {
        String[] fileNameList = {"avatar.jpg", "avatar.gif", "avatar.png"};
        for (String fileName : fileNameList) {
            File file = new File(filePath + fileName);
            if (file.exists()) {
                file.delete();
            }
        }
    }

    private void createUserIcon(ServletFileUpload upload, HttpServletRequest request, String filePath) {

        List<String> iconList = iconList();
        try {
            // Parse the request to get file items.
            List fileItems = upload.parseRequest(request);
            // Process the uploaded file items
            Iterator i = fileItems.iterator();
            //add file
            while (i.hasNext()) {
                FileItem fi = (FileItem) i.next();
                if (fi.getFieldName().equals("result")) {
                    //use local image as user icon
                    if (iconList.contains(fi.getString())) {
                        //get local icon's  path
                        String localIconPath = fi.getString();
                        BufferedImage icon = null;
                        String defaultPath = servletContext.getRealPath(localIconPath);
                        try {
                            //read the local image
                            icon = ImageIO.read(new File(defaultPath));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        String extension = fi.getString().substring(fi.getString().indexOf(".") + 1, fi.getString().length());
                        File outputfile = new File(filePath + "avatar." + extension);
                        ImageIO.write(icon, extension, outputfile);
                        avatarFilePath = "User-Info/" + username + "/avatar/avatar." + extension;
                        scaleImage(outputfile, extension);
                        //write the image to the user's icon
                        break;
                    }
                    //if the file is from the user
                } else if (!fi.isFormField()) {
                    fileName = fi.getName();
                    String fileExtension = fileName.substring(fileName.indexOf(".") + 1, fileName.length());
                    file = new File(filePath + "avatar." + fileExtension);
                    fi.write(file);
                    if (!fileExtension.toLowerCase().equals("gif")) {
                        try {
                            avatarFilePath = "User-Info/" + username + "/avatar/avatar." + fileExtension;
                            scaleImage(file, fileExtension);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        avatarFilePath = "User-Info/" + username + "/avatar/avatar." + fileExtension;
                    }
                }
            }

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    private void scaleImage(File outputFile, String fileExtension) throws IOException {
        try {
            avatarFilePath = "User-Info/" + username + "/avatar/avatar." + fileExtension;
            BufferedImage image = new BufferedImage(200, 200, BufferedImage.TYPE_INT_RGB);
            image.createGraphics().drawImage(ImageIO.read(outputFile).getScaledInstance(200, 200, Image.SCALE_SMOOTH), 0, 0, null);
            //write the thumbnail
            ImageIO.write(image, fileExtension, outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
