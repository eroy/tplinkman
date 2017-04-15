package sergey.zhuravel.tplinkman.model;

public class Blocked {

    private String mac;
    private String description;

    public Blocked(String mac, String description) {
        this.mac = mac;
        this.description = description;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Blocked{" +
                "mac='" + mac + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
