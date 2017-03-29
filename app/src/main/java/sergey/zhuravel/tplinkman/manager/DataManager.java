package sergey.zhuravel.tplinkman.manager;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by serj on 29.03.2017.
 */

public class DataManager {
    public static final String APP_PREFERENCES = "preferences";
    public static final String IP = "ip";
    public static final String KEY = "key";
    public static final String USERNAME = "username";
    public static final String PASS = "pass";

    private SharedPreferences mSharedPreferences;

    private String ip;
    private String key;
    private String username;
    private String pass;


    public DataManager(Context context) {
        mSharedPreferences = context.getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        ip = mSharedPreferences.getString(IP, "");
        key = mSharedPreferences.getString(KEY, "");
        username = mSharedPreferences.getString(USERNAME, "");
        pass = mSharedPreferences.getString(PASS, "");

    }

    public String getIp() {
        return ip;
    }

    public String getKey() {
        return key;
    }

    public String getUsername() {
        return username;
    }

    public String getPass() {
        return pass;
    }

    public void saveData(String ip, String key, String username, String pass) {
        this.ip = ip;
        this.key = key;
        this.username = username;
        this.pass = pass;
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(IP,ip);
        editor.putString(KEY,key);
        editor.putString(USERNAME,username);
        editor.putString(PASS,pass);
        editor.apply();
    }

}
