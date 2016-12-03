package sergey.zhuravel.tplinkman.model;

/**
 * Created by serj on 03.12.16.
 */

public class WanInfo {
    private String ip;
    private String mask;
    private String gateway;
    private String dns1;
    private String dns2;

    private String username;
    private String password;

    private String vpnServer;
    private String typeDynOrStat;
    private String stIp;
    private String stMask;
    private String stGateway;
    private String stDns;

    public WanInfo(String vpnServer, String username, String password, String ip, String mask, String gateway) {
        this.vpnServer = vpnServer;
        this.username = username;
        this.password = password;
        this.ip = ip;
        this.mask = mask;
        this.gateway = gateway;
    }

    public WanInfo(String username, String password, String vpnServer, String typeDynOrStat, String stIp, String stMask, String stGateway, String stDns) {
        this.username = username;
        this.password = password;
        this.vpnServer = vpnServer;
        this.typeDynOrStat = typeDynOrStat;
        this.stIp = stIp;
        this.stMask = stMask;
        this.stGateway = stGateway;
        this.stDns = stDns;
    }

    public WanInfo(String username, String password) {

        this.username = username;
        this.password = password;
    }

    public WanInfo(String ip, String mask, String gateway, String dns1, String dns2) {

        this.ip = ip;
        this.mask = mask;
        this.gateway = gateway;
        this.dns1 = dns1;
        this.dns2 = dns2;
    }

    public WanInfo(String ip, String mask, String gateway, String dns1) {

        this.ip = ip;
        this.mask = mask;
        this.gateway = gateway;
        this.dns1 = dns1;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMask() {
        return mask;
    }

    public void setMask(String mask) {
        this.mask = mask;
    }

    public String getGateway() {
        return gateway;
    }

    public void setGateway(String gateway) {
        this.gateway = gateway;
    }

    public String getDns1() {
        return dns1;
    }

    public void setDns1(String dns1) {
        this.dns1 = dns1;
    }

    public String getDns2() {
        return dns2;
    }

    public void setDns2(String dns2) {
        this.dns2 = dns2;
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

    public String getVpnServer() {
        return vpnServer;
    }

    public void setVpnServer(String vpnServer) {
        this.vpnServer = vpnServer;
    }

    public String getTypeDynOrStat() {
        return typeDynOrStat;
    }

    public void setTypeDynOrStat(String typeDynOrStat) {
        this.typeDynOrStat = typeDynOrStat;
    }

    public String getStIp() {
        return stIp;
    }

    public void setStIp(String stIp) {
        this.stIp = stIp;
    }

    public String getStMask() {
        return stMask;
    }

    public void setStMask(String stMask) {
        this.stMask = stMask;
    }

    public String getStGateway() {
        return stGateway;
    }

    public void setStGateway(String stGateway) {
        this.stGateway = stGateway;
    }

    public String getStDns() {
        return stDns;
    }

    public void setStDns(String stDns) {
        this.stDns = stDns;
    }
}
