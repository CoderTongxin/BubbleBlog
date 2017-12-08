package code_project.DAO;

import code_project.Info.FollowInfo;
import code_project.db.AbstractDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FollowInfoDAO {


    public static void follow(AbstractDB db, String followerUsername, String followUsername) throws SQLException {

        try (Connection c = db.connection()) {
            try (PreparedStatement p = c.prepareStatement("INSERT IGNORE INTO UserRelationship_beta_1 (follower, follow) VALUE (?,?);")) {
                p.setString(1, followerUsername);
                p.setString(2, followUsername);
                p.executeUpdate();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static void unfollow(AbstractDB db, String followerUsername, String followUsername) throws SQLException {
        try (Connection c = db.connection()) {
            try (PreparedStatement p = c.prepareStatement("DELETE FROM UserRelationship_beta_1 WHERE follower=? AND follow=?")) {
                p.setString(1, followerUsername);
                p.setString(2, followUsername);
                p.executeUpdate();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


    public static FollowInfo getFollowInfo(AbstractDB db, String username) {
        List<String> followsList = new ArrayList<>();
        List<String> followersList = new ArrayList<>();
        try (Connection c = db.connection()) {
            try (PreparedStatement p = c.prepareStatement("SELECT * FROM UserRelationship_beta_1 WHERE follower=?")) {
                p.setString(1, username);
                try (ResultSet r = p.executeQuery()) {
                    while (r.next()) {
                        followsList.add(r.getString("follow"));
                    }
                }
            }
            try (PreparedStatement p = c.prepareStatement("SELECT * FROM UserRelationship_beta_1 WHERE follow=?")) {
                p.setString(1, username);
                try (ResultSet r = p.executeQuery()) {
                    while (r.next()) {
                        followersList.add(r.getString("follower"));
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        FollowInfo followInfo = new FollowInfo(followsList, followersList);
        return followInfo;
    }


    public static Boolean checkFollowStatus(Connection c, String username, String followUsername) {
        try (PreparedStatement p = c.prepareStatement("SELECT * FROM UserRelationship_beta_1 WHERE follower=? AND follow=?")) {
            p.setString(1, username);
            p.setString(2, followUsername);
            try (ResultSet r = p.executeQuery()) {
                if (r.next()) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
