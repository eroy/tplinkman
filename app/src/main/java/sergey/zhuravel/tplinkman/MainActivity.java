package sergey.zhuravel.tplinkman;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etIP = (EditText) findViewById(R.id.etIP);
        etLogin = (EditText) findViewById(R.id.etLogin);
        etPass = (EditText) findViewById(R.id.etPass);
        tilIP = (TextInputLayout) findViewById(R.id.tilIP);
        btnIn = (Button) findViewById(R.id.btnIn);
//        ipStatus = (TextView) findViewById(R.id.ipStatus);


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

                }
                else {
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

    private void validateData(String ip, String login, String pass) {

        if (isPing(ip)) {
            String key = getKey(ip, login, pass);

            int keyLength = key.length();
            if (keyLength > 10) {
                Log.e("Sergey", "Validate " + validatePassword(ip, key, login, pass));
                if (validatePassword(ip, key, login, pass)){
                    ArrayList<String> data = new ArrayList<>();
                    Intent intent = new Intent(this,ManagementActivity.class);

                    data.add(ip);
                    data.add(key);
                    data.add(login);
                    data.add(pass);
                    intent.putStringArrayListExtra("arrayData", (ArrayList<String>) data);
                    startActivity(intent);
                    finish();

                }
                else {
//                    Snackbar.make(getView(),"Error password",Snackbar.LENGTH_SHORT).show();
                }


            } else {
                Log.e("Sergey", "OLD");
            }
        }
        else {
//            Snackbar.make(getView(),"Host unreachable",Snackbar.LENGTH_SHORT).show();
            Log.e("Sergey", "Host unreachable");
        }

    }

    private boolean validatePassword(final String ip, final String key, final String login, final String pass) {

        class AsyncLink extends AsyncTask<String, Void, String> {
            ProgressDialog pd;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                pd = new ProgressDialog(
                        MainActivity.this);
                pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                pd.setMessage("Validations data...");

            }

            @Override
            protected String doInBackground(String... strings) {
                String code = "";
                StringBuffer response = new StringBuffer();
                try {
                    String authorization = cookieEncodeMD5(login,pass);
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
                    pd.dismiss();
                    e.printStackTrace();
                }
                return code;
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

}
