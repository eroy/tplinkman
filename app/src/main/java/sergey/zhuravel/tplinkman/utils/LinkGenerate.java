package sergey.zhuravel.tplinkman.utils;

import android.util.Base64;

import java.io.UnsupportedEncodingException;

import sergey.zhuravel.tplinkman.constant.ApiConstant;


public class LinkGenerate {

    public static String baseLink(String ip, String key) {
        return ApiConstant.SERVER + ip + "/" + key + "/";
    }

    public static String baseLink(String ip, String username, String password) {
        return ApiConstant.SERVER + username + ":" + password + "@" + ip + "/";
    }

    public static String baseLink(String ip) {
        return ApiConstant.SERVER + ip + "/";
    }

    public static String referer(String ip, String key, String link) {
        return baseLink(ip, key) + link;
    }

    public static String refererNew(String ip, String key) {
        return baseLink(ip, key);
    }

    public static String referer(String ip) {
        return baseLink(ip);
    }

    public static String referer(String ip, String link) {
        return baseLink(ip) + link;
    }


    public static String authorization(String username, String password) {
        String credentials = username + ":" + password;
        return "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);
    }

    public static String cookie(String username, String password) {
        return "Authorization=" + cookieEncodeMD5(username, password);
    }


    private static String cookieEncodeMD5(String username, String password) {
        String md5 = username + ":" + MD5(password);

        byte[] data = new byte[0];
        try {
            data = md5.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String base64 = Base64.encodeToString(data, Base64.DEFAULT);

        String value = "Basic " + base64;
        value = value.replaceAll(" ", "%20");
        value = value.replaceAll("=", "%3d");
        value = value.replaceAll(System.getProperty("line.separator"), "");
        return value;
    }

    private static String MD5(String md5) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
            byte[] array = md.digest(md5.getBytes());
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            return sb.toString();
        } catch (java.security.NoSuchAlgorithmException e) {
        }
        return null;
    }
}
