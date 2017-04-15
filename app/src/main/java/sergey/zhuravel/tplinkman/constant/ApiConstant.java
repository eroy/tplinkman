package sergey.zhuravel.tplinkman.constant;


public class ApiConstant {
    public static final String SCHEME = "http://";
    public static final String HOSTNAME = "";
    public static final String SERVER = SCHEME;


    public static final String IP = "ip";
    public static final String KEY = "key";

    public static final String IP_KEY = "{ip}/{key}";

    /*input url*/
    public static final String INPUT_KEY = "userRpm/LoginRpm.htm?Save=Save";
    public static final String INPUT_VALIDATE = "userRpm/StatusRpm.htm";
    public static final String INPUT_VALIDATE_OLD = "userRpm/StatusRpm.htm";


    /*info url*/
    public static final String INFO_FIRMWARE = "userRpm/SoftwareUpgradeRpm.htm";
    public static final String INFO_MAC = "userRpm/MacCloneCfgRpm.htm";
    public static final String INFO_WIFI_NAME = "userRpm/WlanNetworkRpm.htm";
    public static final String INFO_WIFI_PASS = "userRpm/WlanSecurityRpm.htm";
    public static final String INFO_STATUS = "userRpm/StatusRpm.htm";
    public static final String INFO_WIFI_STATION = "userRpm/WlanStationRpm.htm";
    public static final String INFO_WIFI_STATION_NAME = "userRpm/AssignedIpAddrListRpm.htm";
    public static final String INFO_WAN_DYNAMIC = "userRpm/WanDynamicIpCfgRpm.htm";
    public static final String INFO_WAN_STATIC = "userRpm/WanStaticIpCfgRpm.htm";
    public static final String INFO_WAN_PPTP = "userRpm/PPTPCfgRpm.htm";
    public static final String INFO_WAN_PPPOE = "userRpm/PPPoECfgRpm.htm";
    public static final String INFO_WAN_TYPE = "userRpm/WanCfgRpm.htm";
    public static final String INFO_WIFI_FILTER = "userRpm/WlanMacFilterRpm.htm";

    /*settings url*/
    public static final String LOGOUT = "userRpm/LogoutRpm.htm";
    public static final String REBOOT = "userRpm/SysRebootRpm.htm?Reboot=Reboot";
    public static final String WIFI_MODE_SETTING = "userRpm/WlanNetworkRpm.htm?broadcast=2&Save=Save";
    public static final String WAN_DYNAMIC = "userRpm/WanDynamicIpCfgRpm.htm?wantype=0&mtu=1500&Save=Save";
    public static final String WAN_STATIC = "userRpm/WanStaticIpCfgRpm.htm?wantype=1&mtu=1500&Save=Save";
    public static final String WAN_PPTP = "userRpm/PPTPCfgRpm.htm?wantype=7&Save=Save";
    public static final String WAN_PPPOE = "userRpm/PPPoECfgRpm.htm?wantype=2&Save=Save";
    public static final String WIFI_FILTER = "userRpm/WlanMacFilterRpm.htm?Save=Save";

    /*settings referer url*/
    public static final String REBOOT_REFERER = "userRpm/SysRebootRpm.htm";
    public static final String WIFI_SETTING_REFERER = "userRpm/WlanNetworkRpm.htm";

}
