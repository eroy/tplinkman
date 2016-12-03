package sergey.zhuravel.tplinkman.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;

import sergey.zhuravel.tplinkman.MainActivity;
import sergey.zhuravel.tplinkman.R;
import sergey.zhuravel.tplinkman.model.WifiInfo;


public class FragmentWifi extends AppFragment {

    private ArrayList<String> data = new ArrayList<>();
    private TextView ssid, textRegional, textChanel, textPassword;
    private ImageView textStatusSec;
    private LinearLayout llInfoWifi, llInfoWifiSec;
    private RelativeLayout rlPassword;


    private ArrayList<String> listWifiInfo = new ArrayList<>();
    private ArrayList<String> listWifiSecInfo = new ArrayList<>();
    private WifiInfo wifiInfo = new WifiInfo();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wifi, container, false);
        initView(view);
        //        get value data
        data = getArguments().getStringArrayList("data");

        listWifiInfo = getInfo(data, INFO_WIFI);
        listWifiSecInfo = getInfo(data, INFO_WIFI_SEC);

//        getInfo
        wifiInfo.setSsid(listWifiInfo.get(0));
        wifiInfo.setRegion(listWifiInfo.get(1));
        wifiInfo.setChanel(listWifiInfo.get(2));
        wifiInfo.setSecMode(listWifiSecInfo.get(0));
        wifiInfo.setPassword(listWifiSecInfo.get(1).substring(1));

//set text
        ssid.setText(wifiInfo.getSsid());
        if (wifiInfo.getChanel().equals("15")) {
            textChanel.setText("auto");
        } else {
            textChanel.setText(wifiInfo.getChanel());
        }

        textRegional.setText(getRegionName(wifiInfo.getRegion()));
        if (wifiInfo.getSecMode().equals("0")) {
            textStatusSec.setImageDrawable(getActivity().getDrawable(R.drawable.ic_lock_open_black_24dp));
            rlPassword.setVisibility(View.GONE);
        } else {
            textStatusSec.setImageDrawable(getActivity().getDrawable(R.drawable.ic_lock_outline_black_24dp));
            rlPassword.setVisibility(View.VISIBLE);
            textPassword.setText(wifiInfo.getPassword());
        }


        llInfoWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editWifiSettingsDialog();
            }
        });
        llInfoWifiSec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editWifiSecDialog();
            }
        });


        return view;
    }

    private void editWifiSecDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.wifi_sec_set_dialog, null);
        final Switch secMode = (Switch) view.findViewById(R.id.securityStatus);
        final EditText etPassword = (EditText) view.findViewById(R.id.etPass);
        final TextInputLayout tilPass = (TextInputLayout) view.findViewById(R.id.tilPass);
        if (wifiInfo.getSecMode().equals("0")) {
            secMode.setChecked(false);
            etPassword.setVisibility(View.GONE);
        } else {
            secMode.setChecked(true);
            etPassword.setVisibility(View.VISIBLE);
            etPassword.setText(wifiInfo.getPassword());
        }

        secMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b) {
                    etPassword.setVisibility(View.VISIBLE);
                    etPassword.setText(wifiInfo.getPassword());
                } else {
                    etPassword.setVisibility(View.GONE);
                }
            }
        });

        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (etPassword.getText().toString().length() < 8) {
                    tilPass.setError("Error password");

                } else {
                    tilPass.setErrorEnabled(false);
                }
            }
        });


        builder.setView(view);

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ArrayList<String> value = new ArrayList<String>();
                String sec;
                if (secMode.isChecked()) {
                    sec = "1";

                } else {
                    sec = "0";
                }
                value.add(sec);
                value.add(etPassword.getText().toString());
                String request = setSettingsRouter(data, TYPE_WIFI_SEC, value);
                if (!secMode.isChecked()) {
                    dialogExit(wifiInfo.getSsid(), "");
                    wifiInfo.setPassword("");
                } else {
                    dialogExit(wifiInfo.getSsid(), etPassword.getText().toString());
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }


    private void dialogExit(final String ssid, final String pass) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String message = "SSID: " + ssid + "\n";
        String message1 = !pass.equals("") ? pass : "No password";
        builder.setMessage(message + "Password: " + message1);
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.putExtra("wifiSsid", ssid);
                intent.putExtra("wifiPass", pass);
                startActivity(intent);
                getActivity().finish();
            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();


    }


    private void editWifiSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.wifi_set_dialog, null);
        final Spinner regional = (Spinner) view.findViewById(R.id.regional);
        final Spinner chanel = (Spinner) view.findViewById(R.id.chanel);
        final EditText etSSID = (EditText) view.findViewById(R.id.etSSID);

        etSSID.setText(wifiInfo.getSsid());
        getRegion(wifiInfo.getRegion(), regional);
        int numberChanel = Integer.parseInt(wifiInfo.getChanel());
//        if chanel auto
        if (numberChanel == 15) {
            chanel.setSelection(13);
        } else {
            chanel.setSelection(numberChanel - 1);
        }

        builder.setView(view);

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                ArrayList<String> value = new ArrayList<String>();
                value.add(regional.getSelectedItem().toString());
                value.add(chanel.getSelectedItem().toString());
                value.add(etSSID.getText().toString());

                String request = setSettingsRouter(data, TYPE_WIFI_SETTINGS, value);
                Snackbar.make(getView(), request, Snackbar.LENGTH_SHORT).show();
                dialogExit(etSSID.getText().toString(), wifiInfo.getPassword());
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


            }
        });
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();


    }

    private String getRegionName(String number) {
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

    private void getRegion(String number, Spinner regional) {
        switch (number) {
            case "83":
                regional.setSelection(2);
                break;
            case "98":
                regional.setSelection(0);
                break;
            case "101":
                regional.setSelection(1);
                break;
            default:
                regional.setSelection(0);
                break;
        }
    }

    private void initView(View view) {
        ssid = (TextView) view.findViewById(R.id.ssid);
        textChanel = (TextView) view.findViewById(R.id.textChanel);
        textRegional = (TextView) view.findViewById(R.id.textRegional);
        textStatusSec = (ImageView) view.findViewById(R.id.textStatusSec);
        textPassword = (TextView) view.findViewById(R.id.textPassword);
        llInfoWifi = (LinearLayout) view.findViewById(R.id.llInfoWifi);
        llInfoWifiSec = (LinearLayout) view.findViewById(R.id.llInfoWifiSec);
        rlPassword = (RelativeLayout) view.findViewById(R.id.rlPassword);


    }

}
