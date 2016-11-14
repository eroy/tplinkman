package sergey.zhuravel.tplinkman;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "Sergey";
    TextView answerText;
    String key;

    String text;

    String ora="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        answerText = (TextView) findViewById(R.id.answer_text);

        final StringBuffer response = new StringBuffer();
        final StringBuffer response1 = new StringBuffer();



        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    String authorization = cockieEncodeMD5("trifle_best");
                    String stringUrl = "http://213.110.122.91/userRpm/LoginRpm.htm?Save=Save";
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
                    Log.e(TAG, key);






                    String stringUrl1 = "http://213.110.122.91/" + key + "/userRpm/WlanSecurityRpm.htm?wepSecOpt=3&wpaSecOpt=2&wpaCipher=2&intervalWpa=0&secType=3&pskSecOpt=3&pskCipher=1&interval=0&Save=Save&pskSecret=DDDDDDDDDD";
                    URL url1 = new URL(stringUrl1);
                    URLConnection uc1 = url1.openConnection();

                    uc1.setRequestProperty("Referer", "http://213.110.122.91/" + key + "/userRpm/WlanSecurityRpm.htm");

                    uc1.setRequestProperty("Cookie", "Authorization=" + authorization);


                    BufferedReader in1 = new BufferedReader(new InputStreamReader(uc1.getInputStream()));
                    String inputLine1;

                    while ((inputLine1 = in1.readLine()) != null) {
                        response1.append(inputLine1);
                    }


                    text = String.valueOf(response1);


                    Log.e(TAG,text);

                    if (text.contains("wlanPara")) {
                        ora="ok";
                    } else {
                        ora="error";

                    }
                    Log.e(TAG,"Ora1: "+ora);

            } catch (IOException e) {
                e.printStackTrace();
            }
                }
            });
            thread.start();

        Log.e(TAG,ora);
        answerText.setText(ora);


    }

    public String cockieEncodeMD5(String password) {
        String md5 = "admin:" + MD5(password);

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


    public String MD5(String md5) {
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
