package sergey.zhuravel.tplinkman.utils;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Response;
import rx.Observable;
import sergey.zhuravel.tplinkman.constant.TypeConstant;

public class Utils {

    public static Observable<List<String>> replaceResponseToObservable(Response<ResponseBody> response, String type) {
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
                case TypeConstant.INFO_WAN_PPOE:

                    break;

                case TypeConstant.INFO_WAN_TYPE:
                    String[] responseArray1 = text.split("/");
                    information.add(responseArray1[2]); //region
                    break;

                case TypeConstant.INFO_FIRMWARE:
                    String[] responseArray2 = text.split("\\(");
                    String[] responseArray3 = responseArray2[1].split(",");
                    information.add(responseArray3[0].replace("\"", "")); // build
                    String[] responseArray31 =responseArray3[1].replace("\"", "").split(" ");

                    information.add(responseArray31[0] + " " + responseArray31[1]); // verison

                    break;
                case TypeConstant.INFO_MAC_WAN:
                    String[] responseArray4 = text.split("\\(");
                    String[] responseArray5 = responseArray4[1].split(",");
                    information.add(responseArray5[0].replace("\"", "")); // mac address


                    break;
            }

            return information;
        });
    }

}
