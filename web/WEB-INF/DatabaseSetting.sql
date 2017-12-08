DROP TABLE IF EXISTS LikeInfo_beta_1;
DROP TABLE IF EXISTS UserRelationship_beta_1;
DROP TABLE IF EXISTS AlbumsAudio_beta_1;
DROP TABLE IF EXISTS AlbumsVideo_beta_1;
DROP TABLE IF EXISTS AlbumsImage_beta_1;
DROP TABLE IF EXISTS CommentReply_beta_1;
DROP TABLE IF EXISTS Comment_beta_1;
DROP TABLE IF EXISTS Article_beta_1;
DROP TABLE IF EXISTS UserInfo_beta_1;

CREATE TABLE UserInfo_beta_1 (
  userID     BIGINT AUTO_INCREMENT NOT NULL UNIQUE,
  username   VARCHAR(99) UNIQUE,
  password   BLOB,
  salt       BLOB,
  iterations BIGINT,
  firstName  VARCHAR(99),
  lastName   VARCHAR(99),
  email      VARCHAR(99),
  birthDate  DATE,
  gender     VARCHAR(99),
  avatar     VARCHAR(256),
  googleID   VARCHAR(99) UNIQUE,
  PRIMARY KEY (username, avatar)
);

DROP TABLE IF EXISTS Article_beta_1;

CREATE TABLE Article_beta_1 (
  articleID  BIGINT PRIMARY KEY AUTO_INCREMENT,
  title      VARCHAR(99),
  content    LONGTEXT,
  postTime   DATETIME,
  tags       VARCHAR(99)  NOT NULL,
  likeNum    BIGINT,
  username   VARCHAR(99)  NOT NULL,
  userAvatar VARCHAR(256) NOT NULL,
  FOREIGN KEY (username) REFERENCES UserInfo_beta_1 (username)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  FOREIGN KEY (username, userAvatar) REFERENCES UserInfo_beta_1 (username, avatar)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);

DROP TABLE IF EXISTS Comment_beta_1;

CREATE TABLE Comment_beta_1 (
  commentID  BIGINT PRIMARY KEY AUTO_INCREMENT,
  content    TINYTEXT,
  postTime   DATETIME,
  username   VARCHAR(99)  NOT NULL,
  userAvatar VARCHAR(256) NOT NULL,
  articleID  BIGINT,
  FOREIGN KEY (username) REFERENCES UserInfo_beta_1 (username)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  FOREIGN KEY (username, userAvatar) REFERENCES UserInfo_beta_1 (username, avatar)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  FOREIGN KEY (articleID) REFERENCES Article_beta_1 (articleID)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);


DROP TABLE IF EXISTS CommentReply_beta_1;

CREATE TABLE CommentReply_beta_1 (
  commentReplyID BIGINT PRIMARY KEY AUTO_INCREMENT,
  content        VARCHAR(500),
  postTime       DATETIME,
  username       VARCHAR(99)  NOT NULL,
  userAvatar     VARCHAR(256) NOT NULL,
  articleID      BIGINT,
  commentID      BIGINT,
  FOREIGN KEY (username) REFERENCES UserInfo_beta_1 (username)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  FOREIGN KEY (username, userAvatar) REFERENCES UserInfo_beta_1 (username, avatar)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  FOREIGN KEY (articleID) REFERENCES Article_beta_1 (articleID)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  FOREIGN KEY (commentID) REFERENCES Comment_beta_1 (commentID)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);


DROP TABLE IF EXISTS AlbumsImage_beta_1;

CREATE TABLE IF NOT EXISTS AlbumsImage_beta_1 (
  id         INT AUTO_INCREMENT,
  fileName   VARCHAR(50),
  username   VARCHAR(99)  NOT NULL,
  userAvatar VARCHAR(256) NOT NULL,
  address    VARCHAR(1000) NOT NULL,
  postTime   DATETIME,
  PRIMARY KEY (id),
  FOREIGN KEY (username) REFERENCES UserInfo_beta_1 (username)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  FOREIGN KEY (username, userAvatar) REFERENCES UserInfo_beta_1 (username, avatar)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);

DROP TABLE IF EXISTS AlbumsVideo_beta_1;

CREATE TABLE IF NOT EXISTS AlbumsVideo_beta_1 (
  id         INT AUTO_INCREMENT,
  fileName   VARCHAR(50),
  username   VARCHAR(99)  NOT NULL,
  userAvatar VARCHAR(256) NOT NULL,
  address    VARCHAR(1000) NOT NULL,
  postTime   DATETIME,
  PRIMARY KEY (id),
  FOREIGN KEY (username) REFERENCES UserInfo_beta_1 (username)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  FOREIGN KEY (username, userAvatar) REFERENCES UserInfo_beta_1 (username, avatar)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);

DROP TABLE IF EXISTS AlbumsAudio_beta_1;

CREATE TABLE IF NOT EXISTS AlbumsAudio_beta_1 (
  id         INT AUTO_INCREMENT,
  fileName   VARCHAR(50),
  username   VARCHAR(99)  NOT NULL,
  userAvatar VARCHAR(256) NOT NULL,
  address    VARCHAR(1000) NOT NULL,
  postTime   DATETIME,
  PRIMARY KEY (id),
  FOREIGN KEY (username) REFERENCES UserInfo_beta_1 (username)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  FOREIGN KEY (username, userAvatar) REFERENCES UserInfo_beta_1 (username, avatar)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);

DROP TABLE IF EXISTS UserRelationship_beta_1;

CREATE TABLE IF NOT EXISTS UserRelationship_beta_1 (
  follower VARCHAR(99) NOT NULL,
  follow   VARCHAR(99) NOT NULL,
  PRIMARY KEY (follow, follower),
  FOREIGN KEY (follow) REFERENCES UserInfo_beta_1 (username)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  FOREIGN KEY (follower) REFERENCES UserInfo_beta_1 (username)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);


DROP TABLE IF EXISTS LikeInfo_beta_1;

CREATE TABLE IF NOT EXISTS LikeInfo_beta_1 (
  articleID BIGINT      NOT NULL,
  likedBy   VARCHAR(99) NOT NULL,
  PRIMARY KEY (articleID, likedBy),
  FOREIGN KEY (articleID) REFERENCES Article_beta_1 (articleID)
    ON UPDATE CASCADE
    ON DELETE CASCADE,
  FOREIGN KEY (likedBy) REFERENCES UserInfo_beta_1 (username)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);