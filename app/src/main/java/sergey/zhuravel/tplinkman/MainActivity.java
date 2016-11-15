package sergey.zhuravel.tplinkman;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements Const {

    public static final String TAG = "Sergey";

    private TextView answerText;
    private Button btn1,btn2;
    private EditText etValue;



    private String ip = "213.110.122.91";
    private String username = "admin";
    private String password = "trifle_best";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        answerText = (TextView) findViewById(R.id.answer_text);
        etValue = (EditText) findViewById(R.id.etValue);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String t = asyncWifi("0", getKey(), DHCP, DHCP_REFERER);
                if (t.equals("error")) {
                    answerText.setText("Error code");
                }
                else {
                    answerText.setText("Successes change wifi ssid to " + t);
                }

            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String t = asyncWifi(etValue.getText().toString(), getKey(), WLAN_PASS, WLAN_PASS_REFERER);
                if (t.equals("error")) {
                    answerText.setText("Error code");
                }
                else {
                    answerText.setText("Successes change wifi password to " + t);
                }

            }
        });

    }

    private String asyncWifi(final String value, final String key, final String url, final String urlReferer) {


        class AsyncLink extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... strings) {
                String text = null;
                StringBuffer response = new StringBuffer();
                try {
                    Log.e(TAG, "key: " + key);
                    String authorization = cockieEncodeMD5();
                    String stringUrl1 = "http://" + ip + "/" + key + url + value;
                    URL url1 = new URL(stringUrl1);
                    HttpURLConnection uc1 = (HttpURLConnection) url1.openConnection();

                    uc1.setRequestProperty("Referer", "http://" + ip + "/" + key + urlReferer);

                    uc1.setRequestProperty("Cookie", "Authorization=" + authorization);


                    BufferedReader in1 = new BufferedReader(new InputStreamReader(uc1.getInputStream()));
                    String inputLine1;

                    while ((inputLine1 = in1.readLine()) != null) {
                        response.append(inputLine1);
                    }

                    uc1.disconnect();

                    text = String.valueOf(response);

                    Log.e(TAG, "TEXT: " + text);

                    if (text.contains("wlanPara")) {
                        text = value;
                    } else {
                        text = "error";

                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }
                return text;
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
        Log.e(TAG, "out: " + out);
        return out;
    }


    private String getKey() {


        class AsyncLink extends AsyncTask<String, Void, String> {


            @Override
            protected String doInBackground(String... strings) {
                String key = "";
                StringBuffer response = new StringBuffer();
                try {
                    String authorization = cockieEncodeMD5();
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
                    Log.e(TAG, "getKey: " + key);


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

    public String cockieEncodeMD5() {
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
