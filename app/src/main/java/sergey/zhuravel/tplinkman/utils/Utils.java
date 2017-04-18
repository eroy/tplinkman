package sergey.zhuravel.tplinkman.utils;


import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Response;
import rx.Observable;
import sergey.zhuravel.tplinkman.constant.TypeConstant;

public class Utils {


    public static Observable<String> replaceResponseToText(Response<ResponseBody> response) {
        return Observable.fromCallable(() -> {
            StringBuffer message = new StringBuffer();
            try {
                BufferedReader in1 = new BufferedReader(new InputStreamReader(response.body().byteStream()));
                String inputLine1;

                while ((inputLine1 = in1.readLine()) != null) {
                    message.append(inputLine1);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

            return String.valueOf(message);
        });
    }

    public static Observable<String> replaceResponseToCode(Response<ResponseBody> response) {
        return Observable.fromCallable(() -> {

            String code = String.valueOf(response.code());

            return code;


        });

    }


    public static Observable<String> replaceResponseKeyToText(Response<ResponseBody> response) {
        return Observable.fromCallable(() -> {
            StringBuffer message = new StringBuffer();
            String text = "";
            if (response.code() == 200) {
                try {
                    BufferedReader in1 = new BufferedReader(new InputStreamReader(response.body().byteStream()));
                    String inputLine1;

                    while ((inputLine1 = in1.readLine()) != null) {
                        message.append(inputLine1);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                String[] responseKeyArray = String.valueOf(message).split("/");
                text = responseKeyArray[3];
            } else {
                text = "old";
            }


            return text;
        });
    }

    public static Observable<List<String>> replaceResponseToObservableList(Response<ResponseBody> response, String type) {
        return Observable.fromCallable(() -> {
            List<String> information = new ArrayList<>();


            StringBuffer message = new StringBuffer();
            try {
                BufferedReader in1 = new BufferedReader(new InputStreamReader(response.body().byteStream()));
                String inputLine1;

                while ((inputLine1 = in1.readLine()) != null) {
                    message.append(inputLine1);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }


            String text = String.valueOf(message);
            String[] responseArray = text.split(",");

            switch (type) {
                case TypeConstant.INFO_WIFI:

                    information.add(responseArray[3].replace("\"", "")); //ssid
                    information.add(responseArray[5]); //region
                    information.add(responseArray[10]); // chanel
                    information.add(responseArray[8]); // isEnable wifi
                    Log.e("WIFI", responseArray[8]);
                    break;
                case TypeConstant.INFO_WIFI_SEC:
                    information.add(responseArray[2].replace(" ", "")); //mode sec
                    information.add(responseArray[9].replace("\"", "")); // pass
                    break;
                case TypeConstant.INFO_WAN_DYN:
                    information.add(responseArray[17].replace("\"", "")); // ip
                    information.add(responseArray[18].replace("\"", "")); // mask
                    information.add(responseArray[19].replace("\"", "")); // gateway
                    information.add(responseArray[24].replace("\"", "")); // dns
                    break;
                case TypeConstant.INFO_WAN_STAT:
                    information.add(responseArray[16].replace("\"", "")); // ip
                    information.add(responseArray[17].replace("\"", "")); // mask
                    information.add(responseArray[18].replace("\"", "")); // gateway
                    information.add(responseArray[20].replace("\"", "")); // dns1
                    information.add(responseArray[21].replace("\"", "")); // dns2


                    break;
                case TypeConstant.INFO_WAN_PPTP:
                    information.add(responseArray[4].replace("\"", "")); // vpn server
                    information.add(responseArray[5].replace("\"", "")); // username
                    information.add(responseArray[6].replace("\"", "")); // password
                    information.add(responseArray[9].replace("\"", "")); // ip
                    information.add(responseArray[10].replace("\"", "")); // mask
                    information.add(responseArray[11].replace("\"", "")); // gateway

                    break;
                case TypeConstant.INFO_WAN_PPPOE:
                    String[] respPppoe = text.split("\\(");
                    String[] respPppoe1 = respPppoe[2].split("\",");
                    String[] respPppoe3 = respPppoe1[0].split("\"");
                    String[] respPppoe4 = respPppoe1[1].split("\"");

                    information.add(respPppoe3[1]);
                    information.add(respPppoe4[1]);

                    break;

                case TypeConstant.INFO_WAN_TYPE:
                    String[] responseArray1 = text.split("/");
                    String[] responseArray12 = responseArray1[2].split("\"");

                    information.add(responseArray12[0]); // type wan
                    break;

                case TypeConstant.INFO_FIRMWARE:

                    String[] responseArray2 = text.split("\\(");
                    String[] responseArray3 = responseArray2[1].split(",");
                    information.add(responseArray3[0].replace("\"", "")); // build
                    String[] responseArray31 = responseArray3[1].replace("\"", "").split(" ");

                    information.add(responseArray31[0] + " " + responseArray31[1]); // verison

                    break;
                case TypeConstant.INFO_MAC_WAN:
                    String[] responseArray4 = text.split("\\(");
                    String[] responseArray5 = responseArray4[1].split(",");
                    information.add(responseArray5[0].replace("\"", "")); // mac address

                    break;
                case TypeConstant.INFO_STATUS:

                    String[] responseStatus = text.split("\\(");
                    String[] responseStatus1 = responseStatus[4].split("\",");

                    if (responseStatus1.length > 1) {
                        information.add(convertBytes(responseStatus1[0].replace("\"", "").replace(",", "")));
                        information.add(convertBytes(responseStatus1[1].replace("\"", "").replace(",", "")));
                    } else {
                        information.add("0");
                        information.add("0");
                    }

                    break;
                case TypeConstant.INFO_WIFI_STATION:

                    String[] responseWifi = text.split("\\(");
                    String[] responseWifi1 = responseWifi[2].split("\\)");
                    information.add(responseWifi1[0]);

                    String[] respPage = text.split("wlanHostPara");
                    String[] respPage1 = respPage[1].split("\\)");
                    String[] respPage2 = respPage1[0].split(",");

                    information.add(respPage2[1].replace(" ", ""));

                    break;
                case TypeConstant.INFO_WIFI_STATION_NAME:

                    String[] responseWifiName = text.split("\\(");
                    String[] responseWifiName1 = responseWifiName[1].split("\\)");
                    information.add(responseWifiName1[0]);
                    break;

                case TypeConstant.INFO_WIFI_FILTER:

                    String[] responseWifiFilter = text.split("wlanFilterList");
                    String[] responseWifiFilter1 = responseWifiFilter[1].split("\\)");
                    information.add(responseWifiFilter1[0]);

                    String[] responsePage = text.split("wlanFilterPara");
                    String[] responsePage1 = responsePage[1].split("\\)");
                    String[] responsePage2 = responsePage1[0].split(",");

                    information.add(responsePage2[3]);


                    break;

            }

            return information;
        });
    }


    public static String convertBytes(String bytes) {
        String hrSize = "0";
        if (bytes != null) {
            if (bytes.length() < 50) {
                double size = Double.parseDouble(bytes);
                double k = size / 1024.0;
                double m = ((size / 1024.0) / 1024.0);
                double g = (((size / 1024.0) / 1024.0) / 1024.0);
                double t = ((((size / 1024.0) / 1024.0) / 1024.0) / 1024.0);

                DecimalFormat dec = new DecimalFormat("0.00");

                if (t > 1) {
                    hrSize = dec.format(t).concat(" TB");
                } else if (g > 1) {
                    hrSize = dec.format(g).concat(" GB");
                } else if (m > 1) {
                    hrSize = dec.format(m).concat(" MB");
                } else if (k > 1) {
                    hrSize = dec.format(k).concat(" KB");
                } else {
                    hrSize = dec.format(size).concat(" Bytes");
                }
            }
        }
        return hrSize;

    }

    public static List<String> getMacByString(String str) {
        List<String> list = new ArrayList<>();
        Pattern pattern = Pattern.compile("([0-9A-Fa-f]{2}[-]){5}([0-9A-Fa-f]{2})");
        Matcher matcherName = pattern.matcher(str);
        while (matcherName.find()) {
            list.add(matcherName.group(0));
        }

        return list;
    }

    public static String getRegionName(String number) {
        switch (number) {
            case "83":
                return "Russia";

            case "98":
                return "Ukraine";

            case "101":
                return "USA";

            default:
                return "Ukraine";

        }
    }
}
