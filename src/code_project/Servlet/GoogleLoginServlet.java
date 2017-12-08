package code_project.Servlet;

import code_project.DAO.LoginInfoDAO;
import code_project.db.MySQL;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.Writer;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.util.Collections;

public class GoogleLoginServlet extends HttpServlet {
    private MySQL mySQL = new MySQL();
    private String email, name, avatar, lastName, firstName, username, googleID;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        Writer out = response.getWriter();
        HttpSession session = request.getSession();
        String idTokenString = request.getParameter("idToken");
        if (idTokenString != null) {
            try {
                if (verifyIdToken(idTokenString)) {
                    String UsernameOfGoogleID = LoginInfoDAO.getUsernameByGoogleID(mySQL, googleID);
                    if (UsernameOfGoogleID != null) {
                        session.setAttribute("status", "login");
                        session.setAttribute("username", UsernameOfGoogleID);
                        out.write("success");
                    } else {
                        LoginInfoDAO.createLoginInfo(mySQL, username, avatar, firstName, lastName, email, googleID);
                        session.setAttribute("status", "login");
                        session.setAttribute("username", this.username);
                        out.write("success");
                    }
                } else {
                    out.write("Fail: invalid ID Token");
                }
            } catch (GeneralSecurityException e) {
                out.write("Fail: security error");
            } catch (SQLException e) {
                out.write("Fail: invalid user information");
            }
        } else {
            out.write("Fail: no ID Token");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        doPost(request, response);
    }

    private boolean verifyIdToken(String idTokenString) throws GeneralSecurityException, IOException {

        NetHttpTransport transport = new NetHttpTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList("782826346139-b034vt93v6m8483o8m4jf2d94hdsbhq6.apps.googleusercontent.com"))
                // Or, if multiple clients access the backend:
                //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                .build();

// (Receive idTokenString by HTTPS POST)

        GoogleIdToken idToken = verifier.verify(idTokenString);
        if (idToken != null) {
            GoogleIdToken.Payload payload = idToken.getPayload();

            // Print user identifier
            googleID = payload.getSubject();

            // Get profile information from payload
            email = payload.getEmail();
            name = (String) payload.get("name");
            avatar = (String) payload.get("picture");
            lastName = (String) payload.get("family_name");
            firstName = (String) payload.get("given_name");

            // Use or store profile information
            username = name + "_" + googleID;
            return true;
        } else {
            return false;
        }
    }

}
