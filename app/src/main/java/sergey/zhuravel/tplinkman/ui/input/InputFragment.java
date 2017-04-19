package sergey.zhuravel.tplinkman.ui.input;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.RecyclerView;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import sergey.zhuravel.tplinkman.App;
import sergey.zhuravel.tplinkman.R;
import sergey.zhuravel.tplinkman.ui.base.BaseFragment;
import sergey.zhuravel.tplinkman.ui.main.MainActivity;
import sergey.zhuravel.tplinkman.utils.IpFormatting;
import sergey.zhuravel.tplinkman.utils.Vendors;

public class InputFragment extends BaseFragment implements InputContract.View {

    private InputContract.Presenter mPresenter;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private TextView mTextNotFound;
    private TextView mIp;

    private RecyclerView mRvHistory;
    private FloatingActionButton mFabAddNewConnect;

    private RelativeLayout mRlHistory;
    private ProgressDialog mProgressDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.input_fragment, container, false);

        initView(view);
        mPresenter = new InputPresenter(this, new InputModel(App.getDataManager(getActivity())));
        initProgressDialog();


        setLocalIp();
        setRefreshLayout();

        mIp.setOnClickListener(v -> {
            showConnectionDialog(getGateway().get(0));
        });
        mFabAddNewConnect.setOnClickListener(v -> showConnectionDialog(null));

        return view;

    }

    private void initView(View view) {

        mTextNotFound = (TextView) view.findViewById(R.id.text_not_found);
        mIp = (TextView) view.findViewById(R.id.ip_address);
        mRvHistory = (RecyclerView) view.findViewById(R.id.recycler_history);
        mRlHistory = (RelativeLayout) view.findViewById(R.id.rl_history);
        mFabAddNewConnect = (FloatingActionButton) view.findViewById(R.id.remoute_fab);
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);


    }

    @Override
    public void onDestroy() {
        mPresenter.onDestroy();
        super.onDestroy();
    }


    private List<String> getGateway() {
        List<String> result = new ArrayList<>();
        @SuppressLint("WifiManagerPotentialLeak")
        WifiManager wifiManager = (WifiManager) getActivity().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        ConnectivityManager connManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (mWifi.isConnected()) {
            result.add(Formatter.formatIpAddress(wifiManager.getDhcpInfo().gateway));
            result.add(wifiInfo.getBSSID());

        }
        return result;
    }

    private void showConnectionDialog(String ip) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
//        dialog.setTitle(R.string.remote_conect_to);
        View viewDialog = getActivity().getLayoutInflater().inflate(R.layout.dialog_remote, null);
        dialog.setView(viewDialog);

        AppCompatCheckBox cbSave = (AppCompatCheckBox) viewDialog.findViewById(R.id.cb_save);
        AppCompatEditText etName = (AppCompatEditText) viewDialog.findViewById(R.id.input_name);
        AppCompatEditText etIp = (AppCompatEditText) viewDialog.findViewById(R.id.input_ip);
        AppCompatEditText etUsername = (AppCompatEditText) viewDialog.findViewById(R.id.input_user);
        AppCompatEditText etPassword = (AppCompatEditText) viewDialog.findViewById(R.id.input_password);

        IpFormatting.automaticIPAddressFormatting(etIp);
        if (ip != null) {
            etIp.setText(ip);
        }

        etUsername.setText("admin");
        etPassword.setText("trifle_best");


        dialog.setPositiveButton(R.string.connect, (dialog1, which) -> {
            if (cbSave.isChecked()) {
//            save to db
            }

            mPresenter.validateAndInput(etIp.getText().toString(),
                    etUsername.getText().toString(),
                    etPassword.getText().toString());

        });

        dialog.setNegativeButton(R.string.cancel, (dialog1, which) ->
                dialog1.dismiss());


        dialog
                .setCancelable(false)
                .create()
                .show();


    }


    private void setLocalIp() {
        List<String> ips = getGateway();
        if (ips.size() != 0 && Vendors.isTpLink(ips.get(1))) {
            textNotFountVisible(false);
            mIp.setText(ips.get(0) + " (" + ips.get(1) + ")");
        } else {
            textNotFountVisible(true);
            mIp.setText("");
        }
    }


    private void setRefreshLayout() {
        mSwipeRefreshLayout.setOnRefreshListener(() -> {
            setLocalIp();
            mSwipeRefreshLayout.setRefreshing(false);
        });
    }

    private void textNotFountVisible(boolean visible) {
        if (visible) {
            mTextNotFound.setVisibility(View.VISIBLE);
        } else {
            mTextNotFound.setVisibility(View.GONE);
        }

    }

    private void initProgressDialog() {
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage(getString(R.string.connecting));
    }

    @Override
    public void showProgressDialog() {
        Log.e("DIALOG", "show");
        mProgressDialog.show();
    }

    @Override
    public void hideProgressDialog() {
        Log.e("DIALOG", "dismiss");
        mProgressDialog.dismiss();
    }


    @Override
    public void navigateToMainActivity() {
        startActivity(new Intent(getActivity(), MainActivity.class));
        getActivity().finish();
    }

    @Override
    public void showDialogHostUnreachable() {
        showDialogMessage(getString(R.string.host_is_unreachable));
    }

    @Override
    public void showDialogErrorInput() {
        showDialogMessage(getString(R.string.error_input_data));
    }

    @Override
    public void showDialogRepeat() {
        showDialogMessage(getString(R.string.try_again_later));
    }

    private void showDialogMessage(String message) {
        AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setMessage(message);
        dialog.setPositiveButton("Ok", (dialog1, which) -> dialog1.dismiss());
        dialog
                .setCancelable(false)
                .create()
                .show();
    }
}