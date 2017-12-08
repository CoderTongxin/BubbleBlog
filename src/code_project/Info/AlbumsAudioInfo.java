package code_project.Info;

public class AlbumsAudioInfo {
    private int id;
    private String fileName;
    private String username;
    private String address;
    private String postTime;
    private String userAvatar;


    public AlbumsAudioInfo(int id, String fileName, String username, String address, String postTime, String userAvatar) {
        this.fileName = fileName;
        this.username = username;
        this.address = address;
        this.postTime = postTime;
        this.id = id;
        this.userAvatar = userAvatar;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

}
