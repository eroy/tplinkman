package sergey.zhuravel.tplinkman.utils;

import android.util.Log;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import rx.Observable;
import sergey.zhuravel.tplinkman.constant.NetworkConstant;


public class NetworkUtils {


    public static boolean validateIP(String host) {

        Pattern pattern = Pattern.compile(NetworkConstant.IPADDRESS_PATTERN);
        Matcher matcher = pattern.matcher(host);
        return matcher.matches();

    }

    public static boolean isPing(String ip) {
        if (validateIP(ip)) {
            Runtime runtime = Runtime.getRuntime();
            try {
                Process ipProcess = runtime.exec("/system/bin/ping -c 2 " + ip);
                int exitValue = ipProcess.waitFor();
                return (exitValue == 0);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }


    public static Observable<Boolean> isHostReachable(String ip) {
        return Observable.fromCallable(() -> {
            if (validateIP(ip)) {
                Runtime runtime = Runtime.getRuntime();
                try {
                    Process ipProcess = runtime.exec("/system/bin/ping -c 2 " + ip);
                    int exitValue = ipProcess.waitFor();
                    return (exitValue == 0);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                    return false;
                }
            }
            return false;
        });


    }


}
