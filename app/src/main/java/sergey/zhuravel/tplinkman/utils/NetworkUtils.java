package sergey.zhuravel.tplinkman.utils;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sergey.zhuravel.tplinkman.constant.NetworkConstant;


public class NetworkUtils {


    public static boolean validateIP(String host) {

        Pattern pattern = Pattern.compile(NetworkConstant.IPADDRESS_PATTERN);
        Matcher matcher = pattern.matcher(host);
        return matcher.matches();

    }

    public static boolean isReachable(String host) {
        if (validateIP(host)) {
            Runtime runtime = Runtime.getRuntime();
            try {
                Process ipProcess = runtime.exec("/system/bin/ping -c 2 " + host);
                int exitValue = ipProcess.waitFor();
                return (exitValue == 0);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return false;
        }

    }


}
