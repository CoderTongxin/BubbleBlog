package code_project.Info;

import java.sql.Blob;
import java.sql.SQLException;

public class LoginInfo {

    private String username;
    private byte[] password, salt;
    private int iterations;

    public LoginInfo(String username, Blob password, Blob salt, int iterations) throws SQLException {
        this.username = username;
        this.password = password.getBytes(1, (int) password.length());
        this.salt = salt.getBytes(1, (int) salt.length());
        this.iterations = iterations;
    }

    public int getIterations() {
        return iterations;
    }

    public String getUsername() {
        return username;
    }

    public byte[] getPassword() {
        return password;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }
}