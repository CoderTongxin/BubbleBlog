package code_project.DAO;

import code_project.Info.AlbumsVideoInfo;
import code_project.db.AbstractDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AlbumsVideoDAO {

    public static List<AlbumsVideoInfo> getAllAlbumsVideoList(AbstractDB db) {

        List<AlbumsVideoInfo> allAlbumsVideoList = new ArrayList<>();
        try (Connection c = db.connection()) {
            try (PreparedStatement p = c.prepareStatement("SELECT * FROM AlbumsVideo_beta_1 WHERE fileName!='No file' ORDER BY postTime ")) {
                try (ResultSet r = p.executeQuery()) {
                    while (r.next()) {
                        allAlbumsVideoList.add(AlbumsVideoInfoFromResultSet(r));
                    }
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return allAlbumsVideoList;
    }


    public static List<AlbumsVideoInfo> getAllAlbumsYoutubeList(AbstractDB db) {

        List<AlbumsVideoInfo> allAlbumsYoutubeList = new ArrayList<>();
        try (Connection c = db.connection()) {
            try (PreparedStatement p = c.prepareStatement("SELECT * FROM AlbumsVideo_beta_1 WHERE fileName='No file' ORDER BY postTime ")) {
                try (ResultSet r = p.executeQuery()) {
                    while (r.next()) {
                        allAlbumsYoutubeList.add(AlbumsVideoInfoFromResultSet(r));
                    }
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return allAlbumsYoutubeList;
    }


    public static List<AlbumsVideoInfo> getAlbumsVideoList(AbstractDB db, String username) {

        List<AlbumsVideoInfo> AlbumsVideoInfoList = new ArrayList<>();

        try (Connection c = db.connection()) {
            try (PreparedStatement p = c.prepareStatement("SELECT * FROM AlbumsVideo_beta_1 WHERE username=? AND fileName!='No file'")) {
                p.setString(1, username);
                try (ResultSet r = p.executeQuery()) {
                    while (r.next()) {
                        AlbumsVideoInfoList.add(AlbumsVideoInfoFromResultSet(r));
                    }
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return AlbumsVideoInfoList;
    }

    public static Boolean checkAlbumsYoutube(AbstractDB db, String address, String username) {
        try (Connection c = db.connection()) {
            try (PreparedStatement p = c.prepareStatement("SELECT * FROM AlbumsVideo_beta_1 WHERE username=? AND address=?")) {
                p.setString(1, username);
                p.setString(2, address);
                try (ResultSet r = p.executeQuery()) {
                    while (r.next()) {
                        return true;
                    }
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static void createAlbumsVideoInfo(AbstractDB db, String username, String address, String fileName, String avatar) throws SQLException {
        try (Connection c = db.connection()) {
            try (PreparedStatement p = c.prepareStatement("INSERT INTO AlbumsVideo_beta_1(username,address,postTime,fileName,userAvatar) VALUES (?,?,?,?,?)")) {
                p.setString(1, username);
                p.setString(4, fileName);
                p.setString(2, address);
                p.setString(3, getCurrentTimeStamp());
                p.setString(5, avatar);
                p.executeUpdate();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void deleteAlbumsVideoInfo(AbstractDB db, String username, int id) throws SQLException {
        try (Connection c = db.connection()) {
            try (PreparedStatement p = c.prepareStatement("DELETE FROM AlbumsVideo_beta_1 WHERE username = ? AND id=?")) {
                p.setString(1, username);
                p.setInt(2, id);
                p.executeUpdate();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static List<AlbumsVideoInfo> getYoutubeList(AbstractDB db, String username) {

        List<AlbumsVideoInfo> AlbumsYoutubeList = new ArrayList<>();

        try (Connection c = db.connection()) {
            try (PreparedStatement p = c.prepareStatement("SELECT * FROM AlbumsVideo_beta_1 WHERE username=? AND fileName='No file'")) {
                p.setString(1, username);
                try (ResultSet r = p.executeQuery()) {
                    while (r.next()) {
                        AlbumsYoutubeList.add(AlbumsVideoInfoFromResultSet(r));
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return AlbumsYoutubeList;

    }

    public static AlbumsVideoInfo getAlbumsVideoInfo(AbstractDB db, String username, String id) {

        AlbumsVideoInfo AlbumsVideoInfo = null;

        try (Connection c = db.connection()) {
            try (PreparedStatement p = c.prepareStatement("SELECT * FROM AlbumsVideo_beta_1 WHERE username = ?AND id=?")) {
                p.setString(1, username);
                p.setString(2, id);
                try (ResultSet r = p.executeQuery()) {
                    while (r.next()) {
                        AlbumsVideoInfo = AlbumsVideoInfoFromResultSet(r);
                    }
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return AlbumsVideoInfo;
    }


    private static AlbumsVideoInfo AlbumsVideoInfoFromResultSet(ResultSet r) throws SQLException {
        return new AlbumsVideoInfo(
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
