package code_project.DAO;

import code_project.Info.AlbumsImageInfo;
import code_project.db.AbstractDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AlbumsImageDAO {

    public static List<AlbumsImageInfo> getAllAlbumsImageList(AbstractDB db) {

        List<AlbumsImageInfo> allAlbumsImageList = new ArrayList<>();
        try (Connection c = db.connection()) {
            try (PreparedStatement p = c.prepareStatement("SELECT * FROM AlbumsImage_beta_1 ORDER BY postTime")) {
                try (ResultSet r = p.executeQuery()) {
                    while (r.next()) {
                        allAlbumsImageList.add(AlbumsImageInfoFromResultSet(r));
                    }
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return allAlbumsImageList;
    }


    public static List<AlbumsImageInfo> getAlbumsImageList(AbstractDB db, String username) {

        List<AlbumsImageInfo> AlbumsImageInfoList = new ArrayList<>();

        try (Connection c = db.connection()) {
            try (PreparedStatement p = c.prepareStatement("SELECT * FROM AlbumsImage_beta_1 WHERE username=?")) {
                p.setString(1, username);
                try (ResultSet r = p.executeQuery()) {
                    while (r.next()) {
                        AlbumsImageInfoList.add(AlbumsImageInfoFromResultSet(r));
                    }
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return AlbumsImageInfoList;
    }

    public static void createAlbumsImageInfo(AbstractDB db, String username, String address, String fileName, String avatar) throws SQLException {
        try (Connection c = db.connection()) {
            try (PreparedStatement p = c.prepareStatement("INSERT INTO AlbumsImage_beta_1(username,address,postTime,fileName,userAvatar) VALUES (?,?,?,?,?)")) {
                p.setString(1, username);
                p.setString(2, address);
                p.setString(4, fileName);
                p.setString(3, getCurrentTimeStamp());
                p.setString(5, avatar);
                p.executeUpdate();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void deleteAlbumsImageInfo(AbstractDB db, String username, int id) throws SQLException {
        try (Connection c = db.connection()) {
            try (PreparedStatement p = c.prepareStatement("DELETE FROM AlbumsImage_beta_1 WHERE username = ? AND id=?")) {
                p.setString(1, username);
                p.setInt(2, id);
                p.executeUpdate();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static AlbumsImageInfo getAlbumsImageInfo(AbstractDB db, String username, String id) {

        AlbumsImageInfo albumsImageInfo = null;

        try (Connection c = db.connection()) {
            try (PreparedStatement p = c.prepareStatement("SELECT * FROM AlbumsImage_beta_1 WHERE username = ?AND id=?")) {
                p.setString(1, username);
                p.setString(2, id);
                try (ResultSet r = p.executeQuery()) {
                    while (r.next()) {
                        albumsImageInfo = AlbumsImageInfoFromResultSet(r);
                    }
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return albumsImageInfo;
    }


    private static AlbumsImageInfo AlbumsImageInfoFromResultSet(ResultSet r) throws SQLException {
        return new AlbumsImageInfo(
                r.getInt("id"),
                r.getString("fileName"),
                r.getString("username"),
                r.getString("address"),
                r.getDate("postTime").toString() + " " + r.getTime("postTime").toString(),
                r.getString("userAvatar")
        );
    }

    private static String getCurrentTimeStamp() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }
}
