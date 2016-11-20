package sergey.zhuravel.tplinkman.model;



public class WifiInfo {
    private String ssid;
    private String region;
    private String chanel;
    private String secMode;
    private String password;


    public String getSsid() {
        return ssid;
    }

    public void setSsid(String ssid) {
        this.ssid = ssid;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getChanel() {
        return chanel;
    }

    public void setChanel(String chanel) {
        this.chanel = chanel;
    }

    public String getSecMode() {
        return secMode;
    }

    public void setSecMode(String secMode) {
        this.secMode = secMode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
