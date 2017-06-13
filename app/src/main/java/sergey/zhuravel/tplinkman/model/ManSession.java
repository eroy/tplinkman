package sergey.zhuravel.tplinkman.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class ManSession extends RealmObject {

    @PrimaryKey
    private String ip;
    private String username;
    private String password;
    private String nameConnection;


    public ManSession(String ip, String username, String password, String nameConnection) {
        this.ip = ip;
        this.username = username;
        this.password = password;
        this.nameConnection = nameConnection;
    }

    public ManSession() {
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNameConnection() {
        return nameConnection;
    }

    public void setNameConnection(String nameConnection) {
        this.nameConnection = nameConnection;
    }
}
