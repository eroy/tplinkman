package sergey.zhuravel.tplinkman.model;


public class Client {

    private String name;
    private String mac;
    private String ip;
    private String download;
    private String upload;

    public Client() {
    }

    public Client(String name, String mac, String ip, String download, String upload) {
        this.name = name;
        this.mac = mac;
        this.ip = ip;
        this.download = download;
        this.upload = upload;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDownload() {
        return download;
    }

    public void setDownload(String download) {
        this.download = download;
    }

    public String getUpload() {
        return upload;
    }

    public void setUpload(String upload) {
        this.upload = upload;
    }


    @Override
    public String toString() {
        return "Client{" +
                "name='" + name + '\'' +
                ", mac='" + mac + '\'' +
                ", ip='" + ip + '\'' +
                ", download='" + download + '\'' +
                ", upload='" + upload + '\'' +
                '}';
    }
}
