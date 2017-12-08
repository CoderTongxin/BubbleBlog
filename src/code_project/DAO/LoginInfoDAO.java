package code_project.DAO;

import code_project.Info.LoginInfo;
import code_project.db.AbstractDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class LoginInfoDAO {

    public static List<String> getUsernameList(AbstractDB db) {
        List<String> usernameList = new ArrayList<>();
        try (Connection c = db.connection()) {
            try (PreparedStatement p = c.prepareStatement("SELECT username FROM UserInfo_beta_1")) {
                try (ResultSet r = p.executeQuery()) {
                    while (r.next()) {
                        usernameList.add(r.getString("username"));
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return usernameList;
    }

    public static Boolean verifyUsernameExistence(AbstractDB db, String username) {
        try (Connection c = db.connection()) {
            try (PreparedStatement p = c.prepareStatement("SELECT * FROM UserInfo_beta_1 WHERE username = ?")) {
                p.setString(1, username);
                try (ResultSet r = p.executeQuery()) {
                    if (r.next()) {
                        return true;
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static LoginInfo getLoginInfo(AbstractDB db, String username) {
        LoginInfo loginInfo = null;
        try (Connection c = db.connection()) {
            try (PreparedStatement p = c.prepareStatement("SELECT * FROM UserInfo_beta_1 WHERE username = ?")) {
                p.setString(1, username);
                try (ResultSet r = p.executeQuery()) {
                    while (r.next()) {
                        loginInfo = loginInfoFromResultSet(r);
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return loginInfo;
    }

    public static String getUsernameByGoogleID(AbstractDB db, String googleID) {
        String username = null;
        try (Connection c = db.connection()) {
            try (PreparedStatement p = c.prepareStatement("SELECT username FROM UserInfo_beta_1 WHERE googleID = ?")) {
                p.setString(1, googleID);
                try (ResultSet r = p.executeQuery()) {
                    while (r.next()) {
                        username = r.getString("username");
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return username;
    }

    public static void createLoginInfo(AbstractDB db, String username, byte[] password, byte[] salt, int iterations, String avatar) throws SQLException {
        try (Connection c = db.connection()) {
            try (PreparedStatement p = c.prepareStatement("INSERT INTO UserInfo_beta_1 (username, password, salt,iterations, avatar) VALUE (?,?,?,?,?);")) {
                p.setString(1, username);
                p.setBytes(2, password);
                p.setBytes(3, salt);
                p.setInt(4, iterations);
                p.setString(5, avatar);

                p.executeUpdate();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void createLoginInfo(AbstractDB db, String username, String avatar, String firstName, String lastName, String email, String googleID) throws SQLException {
        try (Connection c = db.connection()) {
            try (PreparedStatement p = c.prepareStatement("INSERT INTO UserInfo_beta_1 (username, firstName, lastName, email,avatar,googleID) VALUE (?,?,?,?,?,?);")) {
                p.setString(1, username);
                p.setString(2, firstName);
                p.setString(3, lastName);
                p.setString(4, email);
                p.setString(5, avatar);
                p.setString(6, googleID);

                p.executeUpdate();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void updateLoginInfo(AbstractDB db, String username, byte[] password, byte[] salt, int iterations) throws SQLException {
        try (Connection c = db.connection()) {
            try (PreparedStatement p = c.prepareStatement("UPDATE UserInfo_beta_1 SET password = ?, salt = ?, iterations=? WHERE username = ?;")) {
                p.setBytes(1, password);
                p.setBytes(2, salt);
                p.setInt(3, iterations);
                p.setString(4, username);
                p.executeUpdate();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static LoginInfo loginInfoFromResultSet(ResultSet r) throws SQLException {
        return new LoginInfo(
                r.getString("username"),
                r.getBlob("password"),
                r.getBlob("salt"),
                r.getInt("iterations"));
    }

}
