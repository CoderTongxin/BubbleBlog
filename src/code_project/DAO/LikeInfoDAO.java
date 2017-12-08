package code_project.DAO;

import code_project.db.AbstractDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LikeInfoDAO {

    public static void like(AbstractDB db, String articleID, String likedBy) throws SQLException {

        try (Connection c = db.connection()) {
            try (PreparedStatement p = c.prepareStatement("INSERT IGNORE INTO LikeInfo_beta_1 (articleID, likedBy) VALUE (?,?);")) {
                p.setString(1, articleID);
                p.setString(2, likedBy);
                p.executeUpdate();
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void cancelLike(AbstractDB db, String articleID, String likedBy) throws SQLException {

        try (Connection c = db.connection()) {
            try (PreparedStatement p = c.prepareStatement("DELETE FROM LikeInfo_beta_1 WHERE articleID=? AND likedBy=?")) {
                p.setString(1, articleID);
                p.setString(2, likedBy);
                p.executeUpdate();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Boolean checkLikeStatus(AbstractDB db, String articleID, String likedBy) {

        try (Connection c = db.connection()) {
            try (PreparedStatement p = c.prepareStatement("SELECT * FROM LikeInfo_beta_1 WHERE articleID=? AND likedBy=?")) {
                p.setString(1, articleID);
                p.setString(2, likedBy);
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

    public static int getLikeNum(AbstractDB db, String articleID) throws SQLException {

        try (Connection c = db.connection()) {
            try (PreparedStatement p = c.prepareStatement("SELECT count(likedBy) FROM LikeInfo_beta_1 WHERE articleID=?")) {
                p.setString(1, articleID);
                try (ResultSet r = p.executeQuery()) {
                    if (r.next()) {
                        return r.getInt("count(likedBy)");
                    }
                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return 0;

    }
}
