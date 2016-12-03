package sergey.zhuravel.tplinkman;


public interface Const {
    String REBOOT = "/userRpm/SysRebootRpm.htm?Reboot=Reboot";
    String REBOOT_REFERER = "/userRpm/SysRebootRpm.htm";

    String WLAN_SEC = "/userRpm/WlanSecurityRpm.htm?wepSecOpt=3&wpaSecOpt=2&wpaCipher=2&intervalWpa=0&secType=3&pskSecOpt=3&pskCipher=1&interval=0&Save=Save&pskSecret=";
    String WLAN_SEC_OFF = "/userRpm/WlanSecurityRpm.htm?wepSecOpt=3&wpaSecOpt=2&wpaCipher=2&intervalWpa=0&secType=0&pskSecOpt=3&pskCipher=1&interval=0&Save=Save";
    String WLAN_SEC_REFERER = "/userRpm/WlanSecurityRpm.htm";

    String IPADDRESS_PATTERN =
            "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
    String LOGOUT= "/userRpm/LogoutRpm.htm";
    /*
    region:
    98 - Ukraine
    101 - USA
    83 - Russia
    */
    String WLAN_SETTING = "/userRpm/WlanNetworkRpm.htm?broadcast=2&ap=1&region=98&Save=Save&ssid1=";
    String WLAN_SETTING_REFERER = "/userRpm/WlanNetworkRpm.htm";

    String WAN_DYN="/userRpm/WanDynamicIpCfgRpm.htm";
    String WAN_STAT="/userRpm/WanStaticIpCfgRpm.htm";

    String WAN_URL_DYN="/userRpm/WanDynamicIpCfgRpm.htm?wantype=0&mtu=1500&Save=Save";
    String WAN_URL_STAT="/userRpm/WanStaticIpCfgRpm.htm?wantype=1&mtu=1500&Save=Save";

    String DHCP = "/userRpm/LanDhcpServerRpm.htm?ip1=192.168.0.100&ip2=192.168.0.201&Lease=120&gateway=192.168.0.1&Save=Save&dhcpserver=";
    String DHCP_REFERER = "/userRpm/LanDhcpServerRpm.htm";

    String WLAN_CODE="wlanPara";
    String DHCP_CODE="DHCPPara";
    String WAN_DYN_CODE="dhcpInf";
    String WAN_STAT_CODE="staticIpInf";

    String INFO = "/userRpm/StatusRpm.htm";

    String INFO_WIFI = "info_wifi";
    String INFO_WIFI_SEC = "info_wifi_security";
    String INFO_WAN_DYN = "info_wan_dyn";
    String INFO_WAN_STAT = "info_wan_stat";
    String INFO_WAN_PPTP = "info_wan_pptp";
    String INFO_WAN_PPOE = "info_wan_ppoe";


     String TYPE_REBOOT = "reboot";
     String TYPE_WIFI_SETTINGS = "wifi_setting";
     String TYPE_WIFI_SEC = "wifi_sec";
     String TYPE_INFO = "info";
    String TYPE_WAN_DYN="wan_dyn";
    String TYPE_WAN_STAT="wan_stat";
    String TYPE_WAN_PPTP="wan_pptp";
    String TYPE_WAN_PPOE="wan_ppoe";
}
