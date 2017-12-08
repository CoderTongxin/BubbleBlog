package code_project.Servlet;

import code_project.DAO.LoginInfoDAO;
import code_project.Info.LoginInfo;
import code_project.Security.Passwords;
import code_project.db.MySQL;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.sql.SQLException;
import java.util.Random;

public class LoginServlet extends HttpServlet {
    private MySQL mySQL = new MySQL();
    private HttpSession session;
    private String status;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");

        session = request.getSession(true);
        if ((status = (String) session.getAttribute("status")) == null) status = "";

        String action;
        if ((action = request.getParameter("action")) == null) action = "";

        switch (action) {
            case "login":
                loginProcess(request, response);
                break;
            case "logout":
                logoutProcess(request, response);
                break;
            case "register":
                registerProcess(request, response);
                break;
            case "verify":
                verifyProcess(request, response);
                break;
            case "check":
                checkProcess(request, response);
                break;
            default:
                request.getRequestDispatcher("Pages/LoginPage/Login.jsp").forward(request, response);
        }
    }

    private void checkProcess(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");
        response.getWriter().write(status);
    }

    private void verifyProcess(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");
        Writer out = response.getWriter();
        String username = request.getParameter("username");

        if (LoginInfoDAO.verifyUsernameExistence(mySQL, username)) {
            out.write("Username: " + username + " already existed, please pick another one");
        } else {
            out.write("Username: " + username + " is available");
        }
    }

    private void registerProcess(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        boolean ajaxRequest = request.getHeader("x-requested-with") != null;
        PrintWriter out = response.getWriter();
        switch (status) {
            case "login":
                if (ajaxRequest) {
                    out.write("login");
                } else {
                    response.sendRedirect("Blog");
                }
                break;
            default:
                String username = request.getParameter("username");
                if (username == null) {
                    request.getRequestDispatcher("Pages/RegisterPage/Register.jsp").forward(request, response);
                    return;
                }

                String password = request.getParameter("password");
                byte[] salt = Passwords.getNextSalt();
                int iterations = new Random().nextInt(1000) + 100_000;
                byte[] hashPassword = Passwords.hash(password.toCharArray(), salt, iterations);
                try {
                    LoginInfoDAO.createLoginInfo(mySQL, username, hashPassword, salt, iterations, "DefaultAvatar/elyse.png");
                    if (ajaxRequest) {
                        out.write("success");
                    } else {
                        request.setAttribute("message", "Success to create account");
                        request.getRequestDispatcher("Pages/LoginPage/Login.jsp").forward(request, response);
                    }
                } catch (SQLException e) {
                    if (ajaxRequest) {
                        out.write("Fail: " + e.getMessage());
                    } else {
                        response.sendError(500, e.getMessage());
                    }
                }
                break;
        }
    }

    private void logoutProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        switch (status) {
            case "logout":
                request.setAttribute("message", "You already logout!");
                break;
            case "login":
                session.setAttribute("status", "logout");
                session.removeAttribute("username");
                request.setAttribute("message", "You are success to logout!");
                break;
            default:
                session.setAttribute("status", "logout");
                request.setAttribute("message", "You didn't login yet!");
                break;
        }
        request.getRequestDispatcher("Pages/LoginPage/Login.jsp").forward(request, response);
    }

    private void loginProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean ajaxRequest = request.getHeader("x-requested-with") != null;
        response.setContentType("text/plain");
        Writer out = response.getWriter();
        switch (status) {
            case "login":
                if (ajaxRequest) {
                    out.write("login");
                } else {
                    response.sendRedirect("Blog?page=home");
                }
                break;
            default:
                String username = request.getParameter("username");

                if (username == null) {
                    request.getRequestDispatcher("Pages/LoginPage/Login.jsp").forward(request, response);
                    return;
                }

                String password = request.getParameter("password");

                LoginInfo loginInfo = LoginInfoDAO.getLoginInfo(mySQL, username);

                if (loginInfo == null) {
                    if (ajaxRequest) {
                        out.write("Fail to login: username not exist");
                    } else {
                        request.setAttribute("message", "Fail to login: username not exist");
                        request.getRequestDispatcher("Pages/LoginPage/Login.jsp").forward(request, response);
                    }
                    return;
                }

                if (Passwords.isExpectedPassword(password.toCharArray(), loginInfo.getSalt(), loginInfo.getIterations(), loginInfo.getPassword())) {
                    session.setAttribute("status", "login");
                    session.setAttribute("username", username);
                    if (ajaxRequest) {
                        out.write("login");
                    } else {
                        response.sendRedirect("Blog?page=home");
                    }
                } else {
                    if (ajaxRequest) {
                        out.write("Fail to login: wrong password");
                    } else {
                        request.setAttribute("message", "Fail to login: wrong password");
                        request.getRequestDispatcher("Pages/LoginPage/Login.jsp").forward(request, response);
                    }
                }
                break;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }

}
