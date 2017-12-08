package code_project.DAO;

import code_project.Info.ArticleInfo;
import code_project.db.AbstractDB;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ArticleInfoDAO {

    public static List<ArticleInfo> getArticleInfoList(AbstractDB db, String username) {

        List<ArticleInfo> articleInfoList = new ArrayList<>();

        try (Connection c = db.connection()) {
            try (PreparedStatement p = c.prepareStatement("SELECT * FROM Article_beta_1 WHERE username=?")) {
                p.setString(1, username);
                try (ResultSet r = p.executeQuery()) {
                    while (r.next()) {
                        articleInfoList.add(ArticleInfoFromResultSet(r));
                    }
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return articleInfoList;
    }


    public static List<ArticleInfo> getArticleInfoList(AbstractDB db, String username, int number) {

        List<ArticleInfo> articleInfoList = new ArrayList<>();

        try (Connection c = db.connection()) {
            try (PreparedStatement p = c.prepareStatement("SELECT * FROM Article_beta_1 WHERE username=? ORDER BY postTime DESC LIMIT ?")) {
                p.setString(1, username);
                p.setInt(2, number);
                try (ResultSet r = p.executeQuery()) {
                    while (r.next()) {
                        articleInfoList.add(ArticleInfoFromResultSet(r));
                    }
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return articleInfoList;
    }

    public static int getTotalArticleNumber(AbstractDB db) {
        int totalArticleNumber = 0;
        try (Connection c = db.connection()) {
            try (Statement stmt = c.createStatement()) {
                try (ResultSet r = stmt.executeQuery("SELECT COUNT(*) FROM Article_beta_1")) {
                    while (r.next()) {
                        totalArticleNumber = r.getInt(1);
                    }
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return totalArticleNumber;
    }

    public static int getTotalArticleNumber(AbstractDB db, String username) {
        int totalArticleNumber = 0;
        try (Connection c = db.connection()) {
            try (PreparedStatement p = c.prepareStatement("SELECT COUNT(*) FROM Article_beta_1 WHERE username=?")) {
                p.setString(1, username);
                try (ResultSet r = p.executeQuery()) {
                    while (r.next()) {
                        totalArticleNumber = r.getInt(1);
                    }
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return totalArticleNumber;
    }

    public static List<ArticleInfo> getSpotlightArticleInfoList(AbstractDB db, int pageNumber, String username) {

        List<ArticleInfo> articleInfoList = new ArrayList<>();

        try (Connection c = db.connection()) {
            try (PreparedStatement p = c.prepareStatement("SELECT * FROM Article_beta_1 ORDER BY likeNum DESC LIMIT ? OFFSET 0")) {
                p.setInt(1, pageNumber);
                try (ResultSet r = p.executeQuery()) {
                    while (r.next()) {
                        ArticleInfo articleInfo = ArticleInfoFromResultSet(r);
                        String otherUsername = articleInfo.getUsername();
                        if (otherUsername.equals(username)) {
                            articleInfo.setFollowButton("");
                        } else {
                            if (FollowInfoDAO.checkFollowStatus(c, username, otherUsername)) {
                                articleInfo.setFollowButton("<button class=\"ui red button\">Unfollow</button>");
                            } else {
                                articleInfo.setFollowButton("<button class=\"ui blue button\">Follow</button>");
                            }
                        }
                        articleInfoList.add(articleInfo);
                    }
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return articleInfoList;
    }

    public static List<ArticleInfo> getUserArticleInfoList(AbstractDB db, int pageNumber, String username, String targetUser) {

        List<ArticleInfo> articleInfoList = new ArrayList<>();

        try (Connection c = db.connection()) {
            try (PreparedStatement p = c.prepareStatement("SELECT * FROM Article_beta_1 WHERE username=? ORDER BY postTime DESC LIMIT ?")) {
                p.setString(1, targetUser);
                p.setInt(2, pageNumber);

                try (ResultSet r = p.executeQuery()) {
                    while (r.next()) {
                        ArticleInfo articleInfo = ArticleInfoFromResultSet(r);
                        String otherUsername = articleInfo.getUsername();
                        if (otherUsername.equals(username)) {
                            articleInfo.setFollowButton("");
                        } else {
                            if (FollowInfoDAO.checkFollowStatus(c, username, otherUsername)) {
                                articleInfo.setFollowButton("<button class=\"ui red button\">Unfollow</button>");
                            } else {
                                articleInfo.setFollowButton("<button class=\"ui blue button\">Follow</button>");
                            }
                        }
                        articleInfoList.add(articleInfo);
                    }
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return articleInfoList;
    }

    public static List<ArticleInfo> getTagsArticleInfoList(AbstractDB db, int pageNumber, String username, String tags) {

        List<ArticleInfo> articleInfoList = new ArrayList<>();

        try (Connection c = db.connection()) {
            try (PreparedStatement p = c.prepareStatement("SELECT * FROM Article_beta_1 WHERE tags=? ORDER BY likeNum DESC LIMIT ? OFFSET 0")) {
                p.setString(1, tags);
                p.setInt(2, pageNumber);

                try (ResultSet r = p.executeQuery()) {
                    while (r.next()) {
                        ArticleInfo articleInfo = ArticleInfoFromResultSet(r);
                        String otherUsername = articleInfo.getUsername();
                        if (otherUsername.equals(username)) {
                            articleInfo.setFollowButton("");
                        } else {
                            if (FollowInfoDAO.checkFollowStatus(c, username, otherUsername)) {
                                articleInfo.setFollowButton("<button class=\"ui red button\">Unfollow</button>");
                            } else {
                                articleInfo.setFollowButton("<button class=\"ui blue button\">Follow</button>");
                            }
                        }
                        articleInfoList.add(articleInfo);
                    }
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return articleInfoList;
    }

    public static void createArticleInfo(AbstractDB db, String title, String content, String postTime, String tags, String username, String userAvatar) throws SQLException {
        try (Connection c = db.connection()) {
            try (PreparedStatement p = c.prepareStatement("INSERT INTO Article_beta_1(title,content,postTime,tags,username,userAvatar,likeNum) VALUES (?,?,?,?,?,?,?);")) {
                p.setString(1, title);
                p.setString(2, content);
                p.setString(3, postTime);
                p.setString(4, tags);
                p.setString(6, userAvatar);
                p.setString(5, username);
                p.setInt(7, 0);
                p.executeUpdate();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static ArticleInfo getArticleInfo(AbstractDB db, String articleID) {

        ArticleInfo articleInfo = null;

        try (Connection c = db.connection()) {
            try (PreparedStatement p = c.prepareStatement("SELECT * FROM Article_beta_1 WHERE articleID=?")) {
                p.setString(1, articleID);
                try (ResultSet r = p.executeQuery()) {
                    while (r.next()) {
                        articleInfo = ArticleInfoFromResultSet(r);
                    }
                }
            }

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return articleInfo;
    }

    public static void updateArticleInfo(AbstractDB db, String articleID, String content, String title, String postTime, String tags, String username) throws SQLException {
        try (Connection c = db.connection()) {
            try (PreparedStatement p = c.prepareStatement("UPDATE Article_beta_1 SET content =?, title=?,postTime=?, tags=? WHERE username = ? AND articleID=?;")) {
                p.setString(1, content);
                p.setString(2, title);
                p.setString(3, postTime);
                p.setString(5, username);
                p.setString(4, tags);
                p.setString(6, articleID);
                p.executeUpdate();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void updateLikeInfo(AbstractDB db, String articleID, String likeNum) throws SQLException {
        try (Connection c = db.connection()) {
            try (PreparedStatement p = c.prepareStatement("UPDATE Article_beta_1 SET likeNum=? WHERE articleID=?")) {
                p.setString(1, likeNum);
                p.setString(2, articleID);
                p.executeUpdate();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void deleteArticleInfo(AbstractDB db, String username, String articleID) throws SQLException {
        try (Connection c = db.connection()) {
            try (PreparedStatement p = c.prepareStatement("DELETE FROM Article_beta_1 WHERE username = ? AND articleID=?;")) {
                p.setString(1, username);
                p.setString(2, articleID);
                p.executeUpdate();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static ArticleInfo ArticleInfoFromResultSet(ResultSet r) throws SQLException {
        return new ArticleInfo(
                r.getInt("articleID"),
                r.getString("title"),
                r.getString("content"),
                r.getDate("postTime").toString() + " " + r.getTime("postTime").toString(),
                r.getString("tags"),
                r.getString("username"),
                r.getString("userAvatar"),
                r.getInt("likeNum")
        );
    }

    public static String getCurrentTimeStamp() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    }
}