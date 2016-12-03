package sergey.zhuravel.tplinkman;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static sergey.zhuravel.tplinkman.fragment.AppFragment.cookieEncodeMD5;
import static sergey.zhuravel.tplinkman.fragment.AppFragment.getKey;

public class MainActivity extends AppCompatActivity implements Const {

    private static final String IPADDRESS_PATTERN =
            "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
    private SharedPreferences sharedPreferences;
    private EditText etIP, etLogin, etPass;
    private Button btnIn;
    private TextInputLayout tilIP;
    private TextView ipStatus;
    private String wifiSsid = "";
    private String wifiPass = "";
    private ProgressDialog PD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initPD();
        if (getIntent().getStringExtra("wifiSsid") != null) {
            wifiSsid = getIntent().getStringExtra("wifiSsid");
            wifiPass = getIntent().getStringExtra("wifiPass");
            if (!wifiSsid.equals("")) {


                connectToAP(wifiSsid, wifiPass);

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                String ip = etIP.getText().toString();
                String login = etLogin.getText().toString();
                String pass = etPass.getText().toString();

                validateData(ip, login, pass);

            } else {
                Log.e("SERGEY", "null");
            }
        }




        etIP.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!validateIP(etIP.getText().toString())) {
                    tilIP.setError("Error ip");

                } else {
                    tilIP.setErrorEnabled(false);
                }
            }
        });


        btnIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String ip = etIP.getText().toString();
                String login = etLogin.getText().toString();
                String pass = etPass.getText().toString();

                validateData(ip, login, pass);

            }
        });

        loadPref();


    }

    private void initView() {
        etIP = (EditText) findViewById(R.id.etIP);
        etLogin = (EditText) findViewById(R.id.etLogin);
        etPass = (EditText) findViewById(R.id.etPass);
        tilIP = (TextInputLayout) findViewById(R.id.tilIP);
        btnIn = (Button) findViewById(R.id.btnIn);
    }

    private void validateData(String ip, String login, String pass) {

        for (int i=0;i<5;i++) {

            if (isPing(ip)) {
                String key = getKey(ip, login, pass);

                int keyLength = key.length();
                if (keyLength > 10) {
                    Log.e("Sergey", "Validate " + validatePassword(ip, key, login, pass));
                    if (validatePassword(ip, key, login, pass)) {
                        ArrayList<String> data = new ArrayList<>();
                        Intent intent = new Intent(this, ManagementActivity.class);

                        data.add(ip);
                        data.add(key);
                        data.add(login);
                        data.add(pass);
                        intent.putStringArrayListExtra("arrayData", (ArrayList<String>) data);
                        startActivity(intent);
                        finish();

                    } else {
//                    Snackbar.make(getView(),"Error password",Snackbar.LENGTH_SHORT).show();
                    }

                } else {
                    Log.e("Sergey", "OLD");
                }

                break;
            } else {
//            Snackbar.make(getView(),"Host unreachable",Snackbar.LENGTH_SHORT).show();
                Log.e("Sergey", "Host unreachable");
            }
        }
    }

    private boolean validatePassword(final String ip, final String key, final String login, final String pass) {

        class AsyncLink extends AsyncTask<String, Void, String> {


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                PD.show();
            }

            @Override
            protected String doInBackground(String... strings) {

                String code = "";
                StringBuffer response = new StringBuffer();
                try {
                    String authorization = cookieEncodeMD5(login, pass);
                    String stringUrl = "http://" + ip + "/" + key + "/userRpm/StatusRpm.htm";
                    URL url = new URL(stringUrl);
                    URLConnection uc = url.openConnection();
                    uc.setRequestProperty("Referer", "http://" + ip + "/" + key + "/userRpm/StatusRpm.htm");
                    uc.setRequestProperty("Cookie", "Authorization=" + authorization);
                    BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
                    String inputLine;
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }

                    String responseKey = String.valueOf(response);
                    if (responseKey.contains("statusPara")) {
                        code = "ok";
                    } else {
                        code = "error";
                    }


                } catch (IOException e) {
                    PD.dismiss();
                    e.printStackTrace();
                }
                return code;
            }

            @Override
            protected void onPostExecute(String s) {

                PD.dismiss();
                super.onPostExecute(s);
            }

        }

        String out = null;
        try {
            out = new AsyncLink().execute().get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        return out.equals("ok");
    }


    private void savePref() {
        sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("ip", etIP.getText().toString());
        editor.putString("login", etLogin.getText().toString());
        editor.putString("pass", etPass.getText().toString());
        editor.apply();
    }

    private void loadPref() {
        sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        etIP.setText(sharedPreferences.getString("ip", ""));
        etLogin.setText(sharedPreferences.getString("login", ""));
        etPass.setText(sharedPreferences.getString("pass", ""));

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        savePref();
    }


    private boolean isPing(String ip) {
        if (validateIP(ip)) {
            Runtime runtime = Runtime.getRuntime();
            try {
                Process ipProcess = runtime.exec("/system/bin/ping -c 2 " + ip);
                int exitValue = ipProcess.waitFor();
                return (exitValue == 0);
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean validateIP(String ip) {
        Pattern pattern = Pattern.compile(IPADDRESS_PATTERN);
        Matcher matcher = pattern.matcher(ip);
        return matcher.matches();
    }

    private void initPD() {
        PD = new ProgressDialog(this);
        PD.setMessage("Loading.....");
        PD.setCancelable(true);

    }

    public void connectToAP(final String ssid, final String passkey) {

        PD.show();
        Log.e("SERGEY", "* connectToAP");

        for (int i=0;i<3;i++) {
            WifiConfiguration wifiConfiguration = new WifiConfiguration();
            WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
            String networkSSID = ssid;
            String networkPass = passkey;

            Log.e("SERGEY", "# password " + networkPass);

            for (ScanResult result : wifiManager.getScanResults()) {
                if (result.SSID.equals(networkSSID)) {

                    String securityMode = getScanResultSecurity(result);

                    if (securityMode.equalsIgnoreCase("OPEN")) {

                        wifiConfiguration.SSID = "\"" + networkSSID + "\"";
                        wifiConfiguration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
                        int res = wifiManager.addNetwork(wifiConfiguration);
                        Log.e("SERGEY", "# add Network returned " + res);

                        boolean b = wifiManager.enableNetwork(res, true);
                        Log.e("SERGEY", "# enableNetwork returned " + b);
                        wifiManager.setWifiEnabled(true);

                        PD.dismiss();

                    } else if (securityMode.equalsIgnoreCase("WEP")) {

                        wifiConfiguration.SSID = "\"" + networkSSID + "\"";
                        wifiConfiguration.wepKeys[0] = "\"" + networkPass + "\"";
                        wifiConfiguration.wepTxKeyIndex = 0;
                        wifiConfiguration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
                        wifiConfiguration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
                        int res = wifiManager.addNetwork(wifiConfiguration);


                        boolean b = wifiManager.enableNetwork(res, true);


                        wifiManager.setWifiEnabled(true);
                        PD.dismiss();
                    }
                    else if(securityMode.equalsIgnoreCase("PSK")){

                        wifiConfiguration.SSID = "\"" + networkSSID + "\"";
                        wifiConfiguration.preSharedKey = "\"" + networkPass + "\"";

                        wifiConfiguration.hiddenSSID = true;
                        wifiConfiguration.status = WifiConfiguration.Status.ENABLED;
                        wifiConfiguration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
                        wifiConfiguration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
                        wifiConfiguration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
                        wifiConfiguration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
                        wifiConfiguration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
                        wifiConfiguration.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
                        wifiConfiguration.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
                        //Adds to the list of network and returns the network id which can be used to enable it later.

                        int res = wifiManager.addNetwork(wifiConfiguration);
                        wifiManager.disconnect();
                        wifiManager.setWifiEnabled(true);
                        wifiManager.enableNetwork(res, true);
                        wifiManager.reconnect();




                    }
                    PD.dismiss();
                }
            }

        }
    }

    public String getScanResultSecurity(ScanResult scanResult) {


        final String cap = scanResult.capabilities;
        final String[] securityModes = {"WEP", "PSK", "EAP"};

        for (int i = securityModes.length - 1; i >= 0; i--) {
            if (cap.contains(securityModes[i])) {
                return securityModes[i];
            }
        }

        return "OPEN";
    }

}
