package code_project.DAO;

import code_project.Info.AlbumsAudioInfo;
import code_project.db.AbstractDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AlbumsAudioDAO {


    public static List<AlbumsAudioInfo> getAllAlbumsAudioList(AbstractDB db) {

        List<AlbumsAudioInfo> allAlbumsAudioList = new ArrayList<>();
        try (Connection c = db.connection()) {
            try (PreparedStatement p = c.prepareStatement("SELECT * FROM AlbumsAudio_beta_1 ORDER BY postTime")) {

                try (ResultSet r = p.executeQuery()) {
                    while (r.next()) {
                        allAlbumsAudioList.add(AlbumsAudioInfoFromResultSet(r));
                    }
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return allAlbumsAudioList;
    }

    public static List<AlbumsAudioInfo> getAlbumsAudioList(AbstractDB db, String username) {

        List<AlbumsAudioInfo> AlbumsAudioInfoList = new ArrayList<>();

        try (Connection c = db.connection()) {
            try (PreparedStatement p = c.prepareStatement("SELECT * FROM AlbumsAudio_beta_1 WHERE username=?")) {
                p.setString(1, username);
                try (ResultSet r = p.executeQuery()) {
                    while (r.next()) {
                        AlbumsAudioInfoList.add(AlbumsAudioInfoFromResultSet(r));
                    }
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return AlbumsAudioInfoList;
    }

    public static void createAlbumsAudioInfo(AbstractDB db, String username, String address, String fileName, String avatar) throws SQLException {
        try (Connection c = db.connection()) {
            try (PreparedStatement p = c.prepareStatement("INSERT INTO AlbumsAudio_beta_1(username,address,postTime,fileName,userAvatar) VALUES (?,?,?,?,?)")) {
                p.setString(1, username);
                p.setString(2, address);
                p.setString(3, getCurrentTimeStamp());
                p.setString(4, fileName);
                p.setString(5, avatar);
                p.executeUpdate();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void deleteAlbumsAudioInfo(AbstractDB db, String username, int id) throws SQLException {
        try (Connection c = db.connection()) {
            try (PreparedStatement p = c.prepareStatement("DELETE FROM AlbumsAudio_beta_1 WHERE username = ? AND id=?")) {
                p.setString(1, username);
                p.setInt(2, id);
                p.executeUpdate();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static AlbumsAudioInfo getAlbumsAudioInfo(AbstractDB db, String username, String id) {

        AlbumsAudioInfo AlbumsAudioInfo = null;

        try (Connection c = db.connection()) {
            try (PreparedStatement p = c.prepareStatement("SELECT * FROM AlbumsAudio_beta_1 WHERE username = ?AND id=?")) {
                p.setString(1, username);
                p.setString(2, id);
                try (ResultSet r = p.executeQuery()) {
                    while (r.next()) {
                        AlbumsAudioInfo = AlbumsAudioInfoFromResultSet(r);
                    }
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return AlbumsAudioInfo;
    }


    private static AlbumsAudioInfo AlbumsAudioInfoFromResultSet(ResultSet r) throws SQLException {
        return new AlbumsAudioInfo(
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
