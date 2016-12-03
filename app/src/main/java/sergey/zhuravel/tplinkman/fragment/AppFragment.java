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

                    Log.e("INF", String.valueOf(information));
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

    public String setSettingsRouter(final ArrayList<String> data, final String type, final ArrayList<String> value) {
        final String ip = data.get(0);
        final String key = data.get(1);
        final String login = data.get(2);
        final String pass = data.get(3);

        class AsyncLink extends AsyncTask<String, Void, String> {

//            ProgressDialog pd;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

//                pd = ProgressDialog.show(getActivity(), "Setup type", type, false, false);
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
                        case TYPE_REBOOT:
                            stringUrl = "http://" + ip + "/" + key + REBOOT;
                            urlReferer = REBOOT_REFERER;
                            requestCode = "Reboot";
                            break;
                        case TYPE_WIFI_SETTINGS:
                           /* value:
                            o - region
                            1 - chanel
                            2 - ssid

                           */
                            String channel;
                            if (value.get(1).equals("auto")) {
                                channel = "15";
                            } else {
                                channel = value.get(1);
                            }

                            stringUrl = "http://" + ip + "/" + key + "/userRpm/WlanNetworkRpm.htm?broadcast=2&ap=1" +
                                    "&region=" + getRegionNumber(value.get(0)) + "&channel=" + channel + "&Save=Save&ssid1=" + value.get(2);
                            urlReferer = WLAN_SETTING_REFERER;
                            requestCode = WLAN_CODE;
                            break;

                        case TYPE_WIFI_SEC:
                            urlReferer = WLAN_SEC_REFERER;
                            requestCode = WLAN_CODE;
                            /* value:
                            o - secMode
                            1 - password
                            */
                            if (value.get(0).equals("0")) {
                                stringUrl = "http://" + ip + "/" + key + WLAN_SEC_OFF;
                            } else {
                                stringUrl = "http://" + ip + "/" + key + WLAN_SEC + value.get(1);
                            }

                            break;
                        case TYPE_WAN_DYN:
                            stringUrl = "http://" + ip + "/" + key + WAN_URL_DYN;
                            urlReferer = WAN_DYN;
                            requestCode = WAN_DYN_CODE;

                            break;
                        case TYPE_WAN_STAT:

                            Log.e("VALUE", String.valueOf(value));
                            stringUrl = "http://" + ip + "/" + key + "/userRpm/WanStaticIpCfgRpm.htm?wantype=1&mtu=1500"
                                    +"&ip="+value.get(0)+"&mask="+value.get(1)+ "&gateway="+value.get(2)+"&dnsserver="+value.get(3)
                                    +"&dnsserver2="+value.get(4)+"&Save=Save";
                            urlReferer = WAN_STAT;
                            requestCode = WAN_STAT_CODE;
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
                        text = "ok";
                    } else {
                        text = "error";

                    }


                } catch (IOException e) {
//                    pd.dismiss();
                    e.printStackTrace();
                }
                return text;
            }

            @Override
            protected void onPostExecute(String s) {
//                pd.dismiss();
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

    private String getRegionNumber(String name) {
        switch (name) {
            case "Russia":
                return "83";

            case "Ukraine":
                return "98";

            case "USA":
                return "101";

            default:
                return "98";

        }
    }

    public ArrayList<String> getInfo(final ArrayList<String> data, final String type) {
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
                String infoLink = null;
                StringBuffer response = new StringBuffer();

                switch (type) {
                    case INFO_WIFI:
                        infoLink = WLAN_SETTING_REFERER;
                        break;
                    case INFO_WIFI_SEC:
                        infoLink = WLAN_SEC_REFERER;
                        break;
                    case INFO_WAN_DYN:
                        infoLink=WAN_DYN;
                        break;
                    case INFO_WAN_STAT:
                        infoLink = WAN_STAT;
                        break;
                    case INFO_WAN_PPTP:

                        break;
                    case INFO_WAN_PPOE:

                        break;

                }


                String stringUrl = "http://" + ip + "/" + key + infoLink;

                try {

                    String authorization = cookieEncodeMD5(login, pass);
                    URL url = new URL(stringUrl);
                    HttpURLConnection uc = (HttpURLConnection) url.openConnection();
                    uc.setRequestProperty("Referer", "http://" + ip + "/" + key + infoLink);

                    uc.setRequestProperty("Cookie", "Authorization=" + authorization);


                    BufferedReader in1 = new BufferedReader(new InputStreamReader(uc.getInputStream()));
                    String inputLine1;

                    while ((inputLine1 = in1.readLine()) != null) {
                        response.append(inputLine1);
                    }

                    uc.disconnect();

                    text = String.valueOf(response);
                    Log.e("INFO", text);
                    String[] responseArray = text.split(",");

                    switch (type) {
                        case INFO_WIFI:
                            information.add(responseArray[3].replace("\"", "")); //ssid
                            information.add(responseArray[5]); //region
                            information.add(responseArray[10]); // chanel
                            break;
                        case INFO_WIFI_SEC:
                            information.add(responseArray[2].replace(" ", "")); //mode sec
                            information.add(responseArray[9].replace("\"", "")); // pass
                            break;
                        case INFO_WAN_DYN:
                            information.add(responseArray[17].replace("\"", "")); // ip
                            information.add(responseArray[18].replace("\"", "")); // mask
                            information.add(responseArray[19].replace("\"", "")); // gateway
                            information.add(responseArray[24].replace("\"", "")); // dns
                            break;
                        case INFO_WAN_STAT:
                            information.add(responseArray[16].replace("\"", "")); // ip
                            information.add(responseArray[17].replace("\"", "")); // mask
                            information.add(responseArray[18].replace("\"", "")); // gateway
                            information.add(responseArray[20].replace("\"", "")); // dns1
                            information.add(responseArray[21].replace("\"", "")); // dns2


                            break;
                        case INFO_WAN_PPTP:

                            break;
                        case INFO_WAN_PPOE:

                            break;

                    }


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


}
