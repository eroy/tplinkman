package sergey.zhuravel.tplinkman.fragment;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import sergey.zhuravel.tplinkman.Const;


public class AppFragment extends Fragment implements Const {

    public static final String TYPE_REBBOT = "reboot";
    public static final String TYPE_CHANGE_WIFI = "reboot";
    public static final String TYPE_CHANGE_SSID = "reboot";
    public static final String TYPE_INFO = "info";

    public static String getKey(final String ip, final String username, final String password) {

        class AsyncLink extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... strings) {
                String key = "";
                StringBuffer response = new StringBuffer();
                try {
                    String authorization = cookieEncodeMD5(username, password);
                    String stringUrl = "http://" + ip + "/userRpm/LoginRpm.htm?Save=Save";
                    URL url = new URL(stringUrl);
                    URLConnection uc = url.openConnection();
                    uc.setRequestProperty("Cookie", "Authorization=" + authorization);

                    BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }


                    String responseKey = String.valueOf(response);

                    String[] responseKeyArray = responseKey.split("/");
                    key = responseKeyArray[3];
                    Log.e("Sergey", "getKey: " + key);


                } catch (IOException e) {
                    e.printStackTrace();
                }
                return key;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            }
        }

        String out = null;
        try {
            out = new AsyncLink().execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return out;

    }

    public static String cookieEncodeMD5(String username, String password) {
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
//        Log.e(TAG,value);
        return value;
    }


    public static String MD5(String md5) {
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

    public ArrayList<String> asyncInfo(final ArrayList<String> data) {
        final String ip = data.get(0);
        final String key = data.get(1);
        final String login = data.get(2);
        final String pass = data.get(3);

        class AsyncLink extends AsyncTask<Void, Void, ArrayList<String>> {
            ProgressDialog pd;

            @Override
            protected void onPreExecute() {
                pd = ProgressDialog.show(getActivity(), "Getting info", "is the collection of information", false, false);

            }

            @Override
            protected ArrayList<String> doInBackground(Void... voids) {
                String text = null;
                ArrayList<String> information = new ArrayList<>();
                StringBuffer response = new StringBuffer();
                String stringUrl = "http://" + ip + "/" + key + INFO;
                try {

                    String authorization = cookieEncodeMD5(login, pass);
                    URL url = new URL(stringUrl);
                    HttpURLConnection uc = (HttpURLConnection) url.openConnection();
                    uc.setRequestProperty("Referer", "http://" + ip + "/" + key + INFO);

                    uc.setRequestProperty("Cookie", "Authorization=" + authorization);


                    BufferedReader in1 = new BufferedReader(new InputStreamReader(uc.getInputStream()));
                    String inputLine1;

                    while ((inputLine1 = in1.readLine()) != null) {
                        response.append(inputLine1);
                    }

                    uc.disconnect();

                    text = String.valueOf(response);
                    String[] responseArray = text.split(",");

                    information.add(responseArray[5]); //build
                    information.add(responseArray[6]); // version
                    information.add(responseArray[16]); //ssid
                    information.add(responseArray[41]); // mac address
                    information.add(responseArray[42]); // ip
                    information.add(responseArray[43]); // type wan
                    information.add(responseArray[44]); // mask
                    information.add(responseArray[47]); // gateway
                    information.add(responseArray[51]); // dns1
                    information.add(responseArray[52]); // dns2

                } catch (IOException e) {
                    pd.dismiss();
                    e.printStackTrace();
                }
                return information;
            }

            @Override
            protected void onPostExecute(ArrayList<String> strings) {
                pd.dismiss();
                super.onPostExecute(strings);
            }
        }
        ArrayList<String> inf = new ArrayList<>();
        try {
            inf = new AsyncLink().execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return inf;

    }

    public String asyncWifi(final ArrayList<String> data, final String type, final String value) {
        final String ip = data.get(0);
        final String key = data.get(1);
        final String login = data.get(2);
        final String pass = data.get(3);

        class AsyncLink extends AsyncTask<String, Void, String> {

            ProgressDialog pd;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                pd = ProgressDialog.show(getActivity(), "Setup type", type, false, false);
            }

            @Override
            protected String doInBackground(String... strings) {
                String text = null;
                StringBuffer response = new StringBuffer();
                String requestCode = null;
                String stringUrl = null;
                String urlReferer = null;
                try {
                    String authorization = cookieEncodeMD5(login, pass);

                    switch (type) {
                        case TYPE_REBBOT:
                            stringUrl = "http://" + ip + "/" + key + REBOOT;
                            urlReferer = REBOOT_REFERER;
                            requestCode = "Reboot";
                            break;


                    }

                    URL url = new URL(stringUrl);
                    HttpURLConnection uc = (HttpURLConnection) url.openConnection();
                    uc.setRequestProperty("Referer", "http://" + ip + "/" + key + urlReferer);

                    uc.setRequestProperty("Cookie", "Authorization=" + authorization);


                    BufferedReader in1 = new BufferedReader(new InputStreamReader(uc.getInputStream()));
                    String inputLine1;

                    while ((inputLine1 = in1.readLine()) != null) {
                        response.append(inputLine1);
                    }

                    uc.disconnect();

                    text = String.valueOf(response);
                    Log.e("Sergey", text);


                    if (text.contains(requestCode)) {
                        text = value;
                    } else {
                        text = "error";

                    }


                } catch (IOException e) {
                    pd.dismiss();
                    e.printStackTrace();
                }
                return text;
            }

            @Override
            protected void onPostExecute(String s) {
                pd.dismiss();
                super.onPostExecute(s);
            }
        }
        String out = null;

        try {
            out = new AsyncLink().execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return out;
    }

}
