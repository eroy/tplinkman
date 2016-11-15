package sergey.zhuravel.tplinkman;


public interface Const {
    String WLAN_PASS = "/userRpm/WlanSecurityRpm.htm?wepSecOpt=3&wpaSecOpt=2&wpaCipher=2&intervalWpa=0&secType=3&pskSecOpt=3&pskCipher=1&interval=0&Save=Save&pskSecret=";
    String WLAN_PASS_REFERER = "/userRpm/WlanSecurityRpm.htm";
    /*
    region:
    98 - Ukraine
    101 - USA
    */
    String WLAN_SSID = "/userRpm/WlanNetworkRpm.htm?broadcast=2&ap=1&region=98&Save=Save&ssid1=";
    String WLAN_SSID_REFERER = "/userRpm/WlanNetworkRpm.htm";

    String DHCP = "/userRpm/LanDhcpServerRpm.htm?ip1=192.168.0.100&ip2=192.168.0.201&Lease=120&gateway=192.168.0.1&Save=Save&dhcpserver=";
    String DHCP_REFERER = "/userRpm/LanDhcpServerRpm.htm";

    String WLAN_CODE="wlanPara";
    String DHCP_CODE="DHCPPara";

}
