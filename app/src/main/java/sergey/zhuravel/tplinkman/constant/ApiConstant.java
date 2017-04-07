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


    /*info url*/
    public static final String INFO_FIRMWARE = "userRpm/SoftwareUpgradeRpm.htm";
    public static final String INFO_MAC = "userRpm/MacCloneCfgRpm.htm";
    public static final String INFO_WIFI_NAME = "userRpm/WlanNetworkRpm.htm";
    public static final String INFO_WIFI_PASS = "userRpm/WlanSecurityRpm.htm";
    public static final String INFO_STATUS = "userRpm/StatusRpm.htm";
    public static final String INFO_WIFI_STATION = "userRpm/WlanStationRpm.htm";

    /*settings url*/
    public static final String LOGOUT = "userRpm/LogoutRpm.htm";
    public static final String REBOOT = "userRpm/SysRebootRpm.htm?Reboot=Reboot";
    public static final String WIFI_MODE_SETTING = "userRpm/WlanNetworkRpm.htm?broadcast=2&Save=Save";

    /*settings referer url*/
    public static final String REBOOT_REFERER = "userRpm/SysRebootRpm.htm";
    public static final String WIFI_SETTING_REFERER = "userRpm/WlanNetworkRpm.htm";

}
