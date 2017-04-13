package sergey.zhuravel.tplinkman.ui.internet;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import sergey.zhuravel.tplinkman.App;
import sergey.zhuravel.tplinkman.R;
import sergey.zhuravel.tplinkman.constant.TypeConstant;
import sergey.zhuravel.tplinkman.ui.base.BaseFragment;


public class InternetFragment extends BaseFragment implements InternetContract.View {

    private InternetContract.Presenter mPresenter;
    private Toolbar mToolbar;

    private AppCompatEditText mEtIp;
    private AppCompatEditText mEtMask;
    private AppCompatEditText mEtGateway;
    private AppCompatEditText mEtDns1;
    private AppCompatEditText mEtDns2;
    private AppCompatEditText mEtUsername;
    private AppCompatEditText mEtPassword;
    private AppCompatEditText mEtVpn;

    private LinearLayout mLlInternetData;
    private LinearLayout mLlInternetIp;

    private TextInputLayout mInputDns1;
    private TextInputLayout mInputDns2;
    private TextInputLayout mInputVpn;

    private FloatingActionButton mFabTypeInternet;

    private ProgressDialog mProgressDialog;

    private String mTypeWan;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_internet, container, false);

        initView(view);

        mPresenter = new InternetPresenter(this, new InternetModel(App.getDataManager(getActivity())));

        initProgressDialog();

        mFabTypeInternet.setOnClickListener(v -> showChoseTypeInternetDialog());

        mPresenter.getWanType();


        return view;
    }

    @Override
    public void setWanType(String type) {
        switch (type) {
            case "WanDynamicIpCfgRpm.htm":
                setTypeDynamicIp(true);
                break;
            case "WanStaticIpCfgRpm.htm":
                setTypeStaticIp(true);
                break;
            case "PPTPCfgRpm.htm":
                setTypePptp(true);
                break;
            case "PPPoECfgRpm.htm":
                setTypePpoe(true);
                break;
            default:
                setTypeDynamicIp(true);
                break;
        }
    }

    private void initProgressDialog() {
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage(getString(R.string.dialog_applay));
    }

    private void initView(View view) {
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar);

        mEtIp = (AppCompatEditText) view.findViewById(R.id.etIP);
        mEtGateway = (AppCompatEditText) view.findViewById(R.id.etGateway);
        mEtMask = (AppCompatEditText) view.findViewById(R.id.etMask);
        mEtDns1 = (AppCompatEditText) view.findViewById(R.id.etDns1);
        mEtDns2 = (AppCompatEditText) view.findViewById(R.id.etDns2);
        mEtUsername = (AppCompatEditText) view.findViewById(R.id.etUsername);
        mEtPassword = (AppCompatEditText) view.findViewById(R.id.etPass);
        mEtVpn = (AppCompatEditText) view.findViewById(R.id.etVpnServer);

        mLlInternetData = (LinearLayout) view.findViewById(R.id.ll_internet_data);
        mLlInternetIp = (LinearLayout) view.findViewById(R.id.ll_internet_ip);

        mInputDns1 = (TextInputLayout) view.findViewById(R.id.input_layout_dns1);
        mInputDns2 = (TextInputLayout) view.findViewById(R.id.input_layout_dns2);
        mInputVpn = (TextInputLayout) view.findViewById(R.id.input_layout_vpn);
        mInputVpn = (TextInputLayout) view.findViewById(R.id.input_layout_vpn);

        mFabTypeInternet = (FloatingActionButton) view.findViewById(R.id.internet_fab);


        initToolbar(mToolbar, "Internet Settings", true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_internet_setting, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                break;
            case R.id.save_setting:
                String ip = mEtIp.getText().toString();
                String mask = mEtMask.getText().toString();
                String gateway = mEtGateway.getText().toString();
                String dns1 = mEtDns1.getText().toString();
                String dns2 = mEtDns2.getText().toString();
                String username = mEtUsername.getText().toString();
                String password = mEtPassword.getText().toString();
                String server = mEtVpn.getText().toString();

                switch (mTypeWan) {
                    case TypeConstant.WAN_DYNAMIC:
                        mPresenter.setWanDynamic();
                        break;
                    case TypeConstant.WAN_STATIC:
                        mPresenter.setWanStatic(ip, mask, gateway, dns1, dns2);
                        break;
                    case TypeConstant.WAN_PPTP:
                        mPresenter.setWanPptp(username, password, server);
                        break;
                    case TypeConstant.WAN_PPPOE:
                        mPresenter.setWanPppoe(username, password);
                        break;
                    default:
                        Log.e("MENU", "error");
                        break;
                }

                break;
        }


        return super.onOptionsItemSelected(item);
    }


    public void setTypeDynamicIp(boolean reset) {
        mTypeWan = TypeConstant.WAN_DYNAMIC;
        if (reset) {
            resetVariables();
        }
        mLlInternetIp.setVisibility(View.VISIBLE);
        mLlInternetData.setVisibility(View.GONE);
        isEnableIpSetting(false);


        mPresenter.getDynamicSetting();
    }

    public void setTypeStaticIp(boolean reset) {
        mTypeWan = TypeConstant.WAN_STATIC;
        if (reset) {
            resetVariables();
        }
        mLlInternetIp.setVisibility(View.VISIBLE);
        mLlInternetData.setVisibility(View.GONE);
        isEnableIpSetting(true);
        setAccessibilityDns2(true);

        mPresenter.getStaticSetting();
    }

    public void setTypePptp(boolean reset) {
        mTypeWan = TypeConstant.WAN_PPTP;
        if (reset) {
            resetVariables();
        }
        isEnableIpSetting(false);
        mLlInternetIp.setVisibility(View.VISIBLE);
        mLlInternetData.setVisibility(View.VISIBLE);
        setAccessibilityVpnServer(true);

        mPresenter.getPptpSetting();
    }

    public void setTypePpoe(boolean reset) {
        mTypeWan = TypeConstant.WAN_PPPOE;
        if (reset) {
            resetVariables();
        }
        mLlInternetIp.setVisibility(View.GONE);
        mLlInternetData.setVisibility(View.VISIBLE);
        setAccessibilityVpnServer(false);

        mPresenter.getPppoeSetting();
    }

    private void setAccessibilityDns1(boolean visible) {
        if (visible) {
            mInputDns1.setVisibility(View.VISIBLE);
        } else {
            mInputDns1.setVisibility(View.GONE);
        }
    }

    private void setAccessibilityDns2(boolean visible) {
        if (visible) {
            mInputDns2.setVisibility(View.VISIBLE);
        } else {
            mInputDns2.setVisibility(View.GONE);
        }
    }

    private void setAccessibilityVpnServer(boolean visible) {
        if (visible) {
            mInputVpn.setVisibility(View.VISIBLE);
        } else {
            mInputVpn.setVisibility(View.GONE);
        }
    }

    private void isEnableIpSetting(boolean enable) {
        mEtIp.setEnabled(enable);
        mEtMask.setEnabled(enable);
        mEtGateway.setEnabled(enable);
        mEtDns1.setEnabled(enable);
        mEtDns2.setEnabled(enable);
    }

    private void resetVariables() {
        mEtIp.setText("");
        mEtMask.setText("");
        mEtGateway.setText("");
        mEtDns1.setText("");
        mEtDns2.setText("");
        mEtUsername.setText("");
        mEtPassword.setText("");
        mEtVpn.setText("");
    }


    @Override
    public void setIpSettings(String ip, String mask, String gateway, String dns1, String dns2) {
        mEtIp.setText(ip);
        mEtMask.setText(mask);
        mEtGateway.setText(gateway);
        mEtDns1.setText(dns1);
        mEtDns2.setText(dns2);

        setAccessibilityDns1(true);
        if (dns2.equals("")) {
            setAccessibilityDns2(false);
        } else {
            setAccessibilityDns2(true);
        }

    }


    @Override
    public void setIpSettings(String ip, String mask, String gateway) {
        mEtIp.setText(ip);
        mEtMask.setText(mask);
        mEtGateway.setText(gateway);

        setAccessibilityDns1(false);
        setAccessibilityDns2(false);
    }


    @Override
    public void setDataSettings(String username, String password, String vpn) {
        mEtUsername.setText(username);
        mEtPassword.setText(password);
        mEtVpn.setText(vpn);

        setAccessibilityVpnServer(true);
    }

    @Override
    public void setDataSettings(String username, String password) {
        mEtUsername.setText(username);
        mEtPassword.setText(password);


        setAccessibilityVpnServer(false);
    }

    @Override
    public void showProgressDialog() {
        mProgressDialog.show();
    }

    @Override
    public void hideProgressDialog() {
        mProgressDialog.dismiss();
    }

    private void showChoseTypeInternetDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity(), R.style.CustomDialog);

        View viewDialog = getActivity().getLayoutInflater().inflate(R.layout.dialog_chose_internet_type, null);
        dialog.setView(viewDialog);

        RelativeLayout rlDyn = (RelativeLayout) viewDialog.findViewById(R.id.rl_dyn);
        RelativeLayout rlStatic = (RelativeLayout) viewDialog.findViewById(R.id.rl_static);
        RelativeLayout rlPptp = (RelativeLayout) viewDialog.findViewById(R.id.rl_pptp);
        RelativeLayout rlPpoe = (RelativeLayout) viewDialog.findViewById(R.id.rl_ppoe);
        ImageView imgCancel = (ImageView) viewDialog.findViewById(R.id.img_cancel);

        dialog.setCancelable(true);

        AlertDialog alertDialog = dialog.create();


        rlDyn.setOnClickListener(v -> {
            setTypeDynamicIp(true);
            alertDialog.dismiss();
        });

        rlStatic.setOnClickListener(v -> {
            setTypeStaticIp(true);
            alertDialog.dismiss();
        });

        rlPptp.setOnClickListener(v -> {
            setTypePptp(true);
            alertDialog.dismiss();
        });

        rlPpoe.setOnClickListener(v -> {
            setTypePpoe(true);
            alertDialog.dismiss();
        });

        imgCancel.setOnClickListener(v -> alertDialog.dismiss());

        alertDialog.show();
    }

    @Override
    public void navigateSettingMenu() {
        getActivity().onBackPressed();
    }

    @Override
    public void showSuccessToast() {
        Toast.makeText(getActivity(), R.string.success_apply_wan, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorToast() {
        Toast.makeText(getActivity(), R.string.error_apply_wan, Toast.LENGTH_SHORT).show();
    }

}
