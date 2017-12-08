package code_project.DAO;

import code_project.Info.UserInfo;
import code_project.db.AbstractDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserInfoDAO {

    public static List<UserInfo> getUserInfoList(AbstractDB db) {

        List<UserInfo> userInfoList = new ArrayList<>();

        try (Connection c = db.connection()) {
            try (PreparedStatement p = c.prepareStatement("SELECT * FROM UserInfo_beta_1")) {
                try (ResultSet r = p.executeQuery()) {
                    while (r.next()) {
                        userInfoList.add(userInfoFromResultSet(r));
                    }
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return userInfoList;

    }

    /**
     * Gets the {@link UserInfo} with the given id from the given {@link AbstractDB}.
     *
     * @param db
     * @return
     */

    public static UserInfo getUserInfo(AbstractDB db, String username) {
        UserInfo userInfo = null;
        try (Connection c = db.connection()) {
            try (PreparedStatement p = c.prepareStatement("SELECT * FROM UserInfo_beta_1 WHERE username = ?")) {
                p.setString(1, username);
                try (ResultSet r = p.executeQuery()) {
                    while (r.next()) {
                        userInfo = userInfoFromResultSet(r);
                    }
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return userInfo;
    }

    public static boolean googleUser(AbstractDB db, String username) {
        UserInfo userInfo = null;
        try (Connection c = db.connection()) {
            try (PreparedStatement p = c.prepareStatement("SELECT googleID FROM UserInfo_beta_1 WHERE username = ?")) {
                p.setString(1, username);
                try (ResultSet r = p.executeQuery()) {
                    if (r.next()) {
                        if (r.getString("googleID") != null) {
                            return true;
                        }
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void updateUserInfo(AbstractDB db, String username, String newUsername, String newFirstName, String newLastName, String newEmail, String newBirthDate, String newGender) throws SQLException {
        try (Connection c = db.connection()) {
            try (PreparedStatement p = c.prepareStatement("UPDATE UserInfo_beta_1 SET username=?, firstName = ?, lastName=?, email=?, birthDate=?, gender = ? WHERE username = ?;")) {
                p.setString(1, newUsername);
                p.setString(2, newFirstName);
                p.setString(3, newLastName);
                p.setString(4, newEmail);
                p.setString(5, newBirthDate);
                p.setString(6, newGender);
                p.setString(7, username);
                p.executeUpdate();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void updateUserIcon(AbstractDB db, String avatar, String username) throws SQLException {
        try (Connection c = db.connection()) {
            try (PreparedStatement p = c.prepareStatement("UPDATE UserInfo_beta_1 SET avatar=? WHERE username = ?;")) {
                p.setString(1, avatar);
                p.setString(2, username);
                p.executeUpdate();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void deleteUserInfo(AbstractDB db, String username) throws SQLException {
        try (Connection c = db.connection()) {
            try (PreparedStatement a = c.prepareStatement("DELETE FROM UserInfo_beta_1 WHERE username = ?")) {
                a.setString(1, username);
                a.executeUpdate();
            }


        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static List<UserInfo> getUsersList(AbstractDB db, List<String> usernameList) throws SQLException {
        List<UserInfo> userList = new ArrayList<>();
        try (Connection c = db.connection()) {
            for (String name : usernameList) {
                try (PreparedStatement p = c.prepareStatement("SELECT * FROM UserInfo_beta_1 WHERE username = ?")) {
                    p.setString(1, name);
                    try (ResultSet r = p.executeQuery()) {
                        while (r.next()) {
                            UserInfo userInfo = userInfoFromResultSet(r);
                            userList.add(userInfo);
                        }
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return userList;
    }

    public static UserInfo userInfoFromResultSet(ResultSet r) throws SQLException {
        return new UserInfo(
                r.getString("username"),
                r.getString("firstName"),
                r.getString("lastName"),
                r.getString("email"),
                r.getString("birthDate"),
                r.getString("gender"),
                r.getString("avatar"));
    }

}
